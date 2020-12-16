package com.ibm.follow.servlet.client.core.controller.main;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.common.utils.http.utils.agent.BaseAgentUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
/**
 * @Description: 关闭客户端控制器
 * @Author: zjj
 * @Date: 2019-08-30 18:03
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CloseClientController implements ClientExecutor {
	protected Logger log = LogManager.getLogger(this.getClass());

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private IbmTypeEnum customerType;
	private String existId;
	private HandicapUtil.Code handicapCode;
	private IbmcExistHmService existHmService = new IbmcExistHmService();
	private IbmcExistHaService existHaService = new IbmcExistHaService();
	public CloseClientController(IbmTypeEnum customerType) {
		this.customerType = customerType;
	}

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		//获取已存在信息
		if (!getExistInfo(msgObj)) {
			return bean;
		}
        //清除内存
        CustomerCache.clearUp(existId);
        //移除定时器，新加游戏需处理
        removeJob();
		//清除客户盘口存在信息，新加盘口需处理
		removeHandicapInfo();
		//清除客户客户机上的信息
		removeExistInfo();
		//定时任务处理
		existHmService.removeQuertzInfo(existId);

		return bean.success();
	}
	/**
	 * 清除客户客户机上的信息
	 */
	private void removeExistInfo() throws SQLException {
		switch (customerType) {
			case MEMBER:
				existHmService.removeExistHmInfo(existId);
				break;
			case AGENT:
				existHaService.removeExistHaInfo(existId);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				break;
		}
	}
	/**
	 * 移除定时器
	 */
	private void removeJob() {
		switch (customerType) {
			case MEMBER:
				QuartzTool.removeCheckJob(existId, handicapCode);
				break;
			case AGENT:
				QuartzTool.removeHaJob(existId, handicapCode);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				break;

		}
	}
	/**
	 * 移除客户盘口信息
	 */
	private void removeHandicapInfo() {
		switch (customerType) {
			case MEMBER:
				handicapCode.getMemberFactory().removeCrawler(existId);
				break;
			case AGENT:
				BaseAgentUtil agentUtil = CrawlerFactory.getFactory().getAgentCrawler(handicapCode);
				agentUtil.removeHaInfo(existId);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				break;
		}
	}

	/**
	 * 获取已存在信息
	 *
	 * @param msgObj 消息内容
	 * @return 已存在信息
	 */
	private boolean getExistInfo(JSONObject msgObj) throws Exception {
        String code;
		switch (customerType) {
			case MEMBER:
				existId = msgObj.getString("EXIST_HM_ID_");
                code=existHmService.findHandicapCode(existId);
				break;
			case AGENT:
				existId = msgObj.getString("EXIST_HA_ID_");
                code=existHaService.findHandicapCode(existId);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return false;
		}
        if(StringTool.isEmpty(code)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return false;
        }
        handicapCode = HandicapUtil.Code.valueOf(code);
		return true;
	}
}
