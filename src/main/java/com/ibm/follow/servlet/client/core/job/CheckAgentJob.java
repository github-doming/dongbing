package com.ibm.follow.servlet.client.core.job;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.common.utils.http.utils.agent.BaseAgentUtil;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_ha_info.service.IbmcHaInfoService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.container.LruMap;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @Description: 代理定时检验
 * @Author: null
 * @Date: 2020-04-10 18:18
 * @Version: v1.0
 */
public class CheckAgentJob extends BaseCommJob {
    private static final LruMap<String, CheckArgs> CHECK_INFO = new LruMap<>(30);
    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        JobDataMap map = context.getMergedJobDataMap();
        String existHaId = map.getString("existHaId");
        HandicapUtil.Code handicapCode;
        if(map.get("handicapCode") instanceof Enum){
            handicapCode = (HandicapUtil.Code) map.get("handicapCode");
        }else{
            handicapCode=HandicapUtil.Code.valueOf(map.get("handicapCode").toString());
        }
        log.trace(logTitle(),existHaId, handicapCode ,"开始");
        if(StringTool.isEmpty(new IbmcExistHaService().findState(existHaId))){
            log.error(logTitle(),existHaId, handicapCode, "异常，清除定时检验");
            QuartzTool.removeCheckJob(existHaId, handicapCode);
            return ;
        }
		CheckArgs checkArgs;
		if (CHECK_INFO.containsKey(existHaId)) {
			checkArgs = CHECK_INFO.get(existHaId);
		} else {
			checkArgs = new CheckArgs(existHaId);
			CHECK_INFO.put(existHaId, checkArgs);
		}
		if(checkArgs.getLastCheck()){
			log.error(logTitle(),existHaId, handicapCode, "异常，上一次检验尚未完成");
			return;
		}
		checkArgs.setLastCheck(true);
        JsonResultBeanPlus bean = checkResult(existHaId, handicapCode);
        //校验对象为空
        if (bean == null) {
            log.error(logTitle(),existHaId, handicapCode ,"错误：校验结果对象为空");
            checkArgs.checkError();
			checkArgs.setLastCheck(false);
            return;
        }
        //判断失败累加
        if (bean.equalSysCode(HcCodeEnum.CODE_403)) {
            //如果累加次数超过某个数据则发信息到主服务器
            if (checkArgs.checkFatal()) {
                // 发送错误信息到主服务器
                JSONObject content = new JSONObject();
                content.put("EXIST_HA_ID_", existHaId);
                content.put("method", IbmMethodEnum.LOGOUT.name());
                content.put("message",bean.getMessage());
                content.put("code",bean.getCode());
                RabbitMqTool.sendInfoReceipt(content.toString(), "agent");
            }
			checkArgs.setLastCheck(false);
            return;
        }
        //判断其他错误
        if (!bean.isSuccess() || ContainerTool.isEmpty(bean.getData())) {
            if(checkArgs.checkError()){
                log.error(logTitle(),existHaId, handicapCode ,"错误：校验结果长时间出错："+bean.toJsonString());
                // TODO: 2020/3/30 0030 登录 - 其他地方不允许登录 - 根据结果判定是否获取个人信息
                BaseAgentUtil agentUtil= CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
                agentUtil.agentCrawlers.get(existHaId).setProjectHost(null);
            }
			checkArgs.setLastCheck(false);
            return;
        }
        Object memberList =  bean.getData();
        checkArgs.checkSuccess();

        IbmcHaInfoService haInfoService=new IbmcHaInfoService();
        haInfoService.updateMemberList(existHaId,memberList);

        JSONObject content = new JSONObject();
        content.put("EXIST_HA_ID_", existHaId);
        content.put("memberList", memberList);
        content.put("method", IbmMethodEnum.CUSTOMER_INFO.name());
        content.put("requestType", IbmStateEnum.SUCCESS.name());
        RabbitMqTool.sendInfoReceipt(content.toString(), "agent");

		checkArgs.setLastCheck(false);
        log.debug(logTitle(),existHaId, handicapCode,"校验完成");
    }
    private JsonResultBeanPlus checkResult(String existHaId, HandicapUtil.Code handicapCode) {
        BaseAgentUtil agentUtil= CrawlerFactory.getFactory().getAgentCrawler(handicapCode);

        return agentUtil.checkInfo(existHaId);
    }

    private String logTitle() {
        return "盘口代理【{}】定时校验【{}】盘口,{}";
    }
}
