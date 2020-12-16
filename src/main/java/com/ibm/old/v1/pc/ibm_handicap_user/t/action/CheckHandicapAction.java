package com.ibm.old.v1.pc.ibm_handicap_user.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.controller.mq.CheckLoginController;
import com.ibm.old.v1.cloud.core.controller.mq.LoginClientController;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service.IbmCloudReceiptTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.entity.IbmHandicapItemT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.entity.IbmHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.thread.InitHandicapMemberThread;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_handicap_item.t.service.IbmPcHandicapItemTService;
import com.ibm.old.v1.pc.ibm_handicap_member.t.controller.HandicapInfoController;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import com.ibm.old.v1.pc.ibm_handicap_user.t.service.IbmPcHandicapUserTService;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Description: 校验登陆后是否识别过盘口
 * @Author: wck
 * @Date: 2019-03-18 10:45
 * @Email: 810160078@qq.com
 * @Version: v1.0
 */
public class CheckHandicapAction extends BaseAppAction {

    @Override
    public String run() throws Exception {
        JsonResultBeanPlus jrb = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (appUserT == null) {
            jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
            jrb.putSysEnum(IbmCodeEnum.CODE_401);
            return this.returnJson(jrb);
        }
        //盘口地址，盘口验证码
        String handicapUrl = BeanThreadLocal.find(dataMap.get("HANDICAP_URL"), "");
        String handicapCaptcha = BeanThreadLocal.find(dataMap.get("HANDICAP_CAPTCHA"), "");
        //盘口会员id
        String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
        //盘口code
        String handicapCode = BeanThreadLocal.find(dataMap.get("HANDICAP_CODE_"), "");
        if (StringTool.isEmpty(handicapUrl, handicapCaptcha, handicapCode, handicapMemberId)) {
            jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
            jrb.putSysEnum(IbmCodeEnum.CODE_401);
            return this.returnJson(jrb);
        }
        try {
            //查询盘口并判断是否存在
            IbmHandicapTService handicapTService = new IbmHandicapTService();
            Map<String, Object> handicapInfo = handicapTService.findHandicapInfoByCode(handicapCode);
            if (ContainerTool.isEmpty(handicapInfo)) {
                jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
                jrb.putSysEnum(IbmCodeEnum.CODE_404);
                return this.returnJson(jrb);
            }
            String handicapItemId;
            //查询盘口相关信息
            IbmPcHandicapItemTService handicapItemTService = new IbmPcHandicapItemTService();
            Map<String, Object> handicapMap = handicapItemTService.findIdByUrlCaptcha(handicapUrl, handicapCaptcha);
            Date nowTime = new Date();
            if (ContainerTool.isEmpty(handicapMap)) {
                //不存在新增
                IbmHandicapItemT handicapItemT = new IbmHandicapItemT();
                handicapItemT.setHandicapId(handicapInfo.get("IBM_HANDICAP_ID_").toString());
                handicapItemT.setHandicapName(handicapInfo.get("HANDICAP_NAME_").toString());
                handicapItemT.setHandicapUrl(handicapUrl);
                handicapItemT.setHandicapCaptcha(handicapCaptcha);
                handicapItemT.setCreateTime(nowTime);
                handicapItemT.setCreateTimeLong(nowTime.getTime());
                handicapItemT.setUpdateTime(nowTime);
                handicapItemT.setUpdateTimeLong(nowTime.getTime());
                handicapItemT.setState(IbmStateEnum.OPEN.name());
                handicapItemId = handicapItemTService.save(handicapItemT);
            } else {
                handicapItemId = handicapMap.get("IBM_HANDICAP_ITEM_ID_").toString();
                if (!handicapMap.get("HANDICAP_CODE_").toString().equals(handicapCode)) {
                    jrb.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
                    jrb.putSysEnum(IbmCodeEnum.CODE_403);
                    jrb.setMessageSys("该地址不属于本盘口");
                    return this.returnJson(jrb);
                }
            }

            //盘口用户是否存在
            IbmPcHandicapUserTService handicapUserTService = new IbmPcHandicapUserTService();
            if (!handicapUserTService.isExist(appUserId, handicapInfo.get("IBM_HANDICAP_ID_").toString())) {
                jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_USER);
                jrb.putSysEnum(IbmCodeEnum.CODE_404);
                return this.returnJson(jrb);
            }

            IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
            //保存盘口详情id到盘口会员
            IbmHandicapMemberT handicapMemberT = handicapMemberTService.find(handicapMemberId);
            handicapMemberT.setHandicapItemId(handicapItemId);
            handicapMemberT.setUpdateTime(nowTime);
            handicapMemberT.setUpdateTimeLong(nowTime.getTime());
            handicapMemberTService.update(handicapMemberT);

            //检验登录
            jrb=new CheckLoginController().execute(handicapMemberId);
            if(!jrb.isSuccess()){
                return returnJson(jrb);
            }
            handicapMemberTService.updateState(handicapMemberId);
            jrb.setSuccess(false);
            //发送mq消息
            jrb = new LoginClientController().execute(handicapMemberId);

            //登陆发送成功，且处理完成
            Map<String, Object> data = new HashMap<>(3);
            if (jrb.isSuccess()) {
                Object receiptId = jrb.getData();
                //FINISH，已经登陆完成
                if (IbmCodeEnum.CODE_200.getCode().equals(jrb.getCodeSys())) {
                    if (StringTool.notEmpty(receiptId)) {
                        //没有保存到盘口会员表中
                        IbmCloudReceiptTService receiptTService = new IbmCloudReceiptTService();
                        String processResult = receiptTService.findProcessResult(receiptId.toString());
                        if (StringTool.isEmpty(processResult)) {
                            jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
                            jrb.putSysEnum(IbmCodeEnum.CODE_404);
                            return returnJson(jrb);
                        }
                        JSONObject jsonRe = JSONObject.fromObject(processResult);
                        if (jsonRe.getBoolean("success")) {
							//同步盘口会员方案状态
							IbmPlanHmTService planHmTService = new IbmPlanHmTService();
							boolean exist=planHmTService.findByHmId(handicapMemberId);
							if(exist){
								ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil
										.findInstance().findLocal();
								ExecutorService executorService = threadExecutorService.findExecutorService();
								executorService.execute(new InitHandicapMemberThread(handicapMemberId));
							}
                            JSONObject hmInfo = new HandicapInfoController().execute(handicapMemberId).getJSONObject(0);
                            data.put("hmInfo", hmInfo);
                        } else {
                            data.put("message", jsonRe.get("data"));
                        }
                    } else {
                        log.error("获取消息回执id失败");
                    }
                    //返回登录结果
                } else if (IbmCodeEnum.CODE_202.getCode().equals(jrb.getCodeSys())) {
                    data.put("receiptId", receiptId);
                    data.put("handicapMemberId", handicapMemberId);
                }
                jrb.setData(data);
                jrb.success();
            }
            return returnJson(jrb);
        } catch (Exception e) {
            log.error(IbmConfig.LOG_SIGN + "识别盘口错误", e);
            throw e;
        }
    }
}
