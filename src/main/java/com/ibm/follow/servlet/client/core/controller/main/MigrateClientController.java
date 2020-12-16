package com.ibm.follow.servlet.client.core.controller.main;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.http.tools.CrawlerFactory;
import com.ibm.follow.servlet.client.core.controller.ClientDefineController;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.entity.IbmcExistHa;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.entity.IbmcExistHm;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.Date;

/**
 * 客户端迁移
 *
 * @Author: Dongming
 * @Date: 2020-01-10 16:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MigrateClientController implements ClientExecutor {
    protected Logger log = LogManager.getLogger(this.getClass());

    private JsonResultBeanPlus bean = new JsonResultBeanPlus();
    private IbmTypeEnum customerType;
    private String existId;
    private HandicapUtil.Code handicapCode;

    public MigrateClientController(IbmTypeEnum customerType) {
        this.customerType = customerType;
    }

    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        //获取已存在信息
        boolean flag = getExistInfo(msgObj);
        if (!flag) {
            return bean;
        }
        //存储登录信息
        JSONObject loginInfo = msgObj.getJSONObject("loginInfo");
        JSONObject handicapInfo = msgObj.getJSONObject("handicapInfo");
        JSONArray gameInfos = msgObj.getJSONArray("gameInfos");
        String customerAccount, customerPassword, handicapUrl, handicapCaptcha;
        Date nowTime = new Date();
        ClientDefineController controller = new ClientDefineController(existId, nowTime);
        switch (customerType) {
            case MEMBER:
                controller.saveHandicapMember(loginInfo);
                controller.saveMemberHandicapInfo(handicapInfo);
                controller.saveMemberGameInfo(gameInfos);

                QuartzTool.saveCheckHmJob(existId, handicapCode);

                //放入账户信息 - 启动定时器
                customerAccount = loginInfo.get("MEMBER_ACCOUNT_").toString();
                customerPassword = EncryptTool.decode(EncryptTool.Type.ASE, loginInfo.get("MEMBER_PASSWORD_").toString());
                handicapUrl = loginInfo.get("HANDICAP_URL_").toString();
                handicapCaptcha = loginInfo.get("HANDICAP_CAPTCHA_").toString();
				handicapCode.getMemberFactory()
						.userInfo(existId, handicapUrl, handicapCaptcha, customerAccount, customerPassword);
                //添加member_info
                controller.saveMemberInfo(customerAccount);
                break;
            case AGENT:
                controller.saveHandicapAgent(loginInfo);
                controller.saveAgentHandicapInfo(handicapInfo);
                controller.saveAgentGameInfo(gameInfos, handicapCode);
                //添加agent_info
                controller.saveAgentInfo();
                QuartzTool.saveCheckHaJob(existId, handicapCode);

                //添加绑定信息
                if(ContainerTool.notEmpty(msgObj.getJSONArray("bindInfos"))){
                    controller.saveBindInfo(msgObj.getJSONArray("bindInfos"));
                }

                customerAccount = loginInfo.get("AGENT_ACCOUNT_").toString();
                customerPassword = EncryptTool.decode(EncryptTool.Type.ASE, loginInfo.get("AGENT_PASSWORD_").toString());
                handicapUrl = loginInfo.get("HANDICAP_URL_").toString();
                handicapCaptcha = loginInfo.get("HANDICAP_CAPTCHA_").toString();
                CrawlerFactory.getFactory().getAgentCrawler(handicapCode)
                        .accountInfo(existId, customerAccount, customerPassword, handicapUrl, handicapCaptcha);

                break;
            default:
                log.error("异常的客户类型捕捉：".concat(customerType.name()));
                bean.error("异常的客户类型捕捉");
                return bean;
        }


        return bean;

    }

    private boolean getExistInfo(JSONObject msgObj) throws Exception {
        switch (customerType) {
            case MEMBER:
                existId = msgObj.getString("EXIST_HM_ID_");
                IbmcExistHmService existHmService = new IbmcExistHmService();
                IbmcExistHm existHm = existHmService.find(existId);
                if (existHm == null) {
                    bean.putEnum(CodeEnum.IBS_404_DATA);
                    bean.putEnum(CodeEnum.CODE_404);
                    return false;
                }
                handicapCode = HandicapUtil.Code.valueOf(existHm.getHandicapCode());
                break;
            case AGENT:
                existId = msgObj.getString("EXIST_HA_ID_");
                IbmcExistHaService existHaService = new IbmcExistHaService();
                IbmcExistHa existHa = existHaService.find(existId);
                if (existHa == null) {
                    bean.putEnum(CodeEnum.IBS_404_DATA);
                    bean.putEnum(CodeEnum.CODE_404);
                    return false;
                }
                handicapCode = HandicapUtil.Code.valueOf(existHa.getHandicapCode());
                break;
            default:
                log.error("异常的客户类型捕捉：".concat(customerType.name()));
                bean.error("异常的客户类型捕捉");
                return false;
        }
        return true;
    }
}
