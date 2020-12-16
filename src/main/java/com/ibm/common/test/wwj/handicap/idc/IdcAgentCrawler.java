package com.ibm.common.test.wwj.handicap.idc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.core.configs.IdcConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.test.wwj.handicap.AbsAgentCrawler;
import com.ibm.common.test.wwj.handicap.DataCustomer;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.agent.IdcAgentApiTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.List;
import java.util.Map;

;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/4 16:23
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class IdcAgentCrawler extends AbsAgentCrawler<IdcAgentGrab> {
    /**
     * 获取会员账号信息
     *
     * @return 会员账号信息
     */
    @Override
    protected JsonResultBeanPlus memberListInfo() {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        try {
            DataCustomer customer = agent.customer();
            List<Map<String,String>> memberList = grab.getMemberList(customer.httpConfig(),customer.crawler().get("projectHost"), customer.crawler().get("ticket"));
            agent.memberList(memberList);
            bean.success(memberList);
        } catch (Exception e) {
            log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 登陆
     *
     * @param httpConfig      请求配置类
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @param account         盘口账户
     * @param password        盘口密码
     * @return 登录结果
     */
    @Override
    protected JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String account, String password) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //如果盘口地址不包含http://则添加
        String http = "http://";
        if (!handicapUrl.startsWith(http) && !handicapUrl.startsWith("https://")) {
            handicapUrl = http.concat(handicapUrl);
        }
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try{
            bean = grab.baseLogin(bean,httpConfig,handicapUrl,handicapCaptcha,account,password);
        }catch (Exception e) {
            log.error(getMsgTitle()+"代理账号【" + account + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 投注未结算摘要
     *
     * @param gameCode 游戏编码
     * @return 未结算摘要
     */
    @Override
    public JsonResultBeanPlus betSummary(GameUtil.Code gameCode) {

        return null;
    }

    /**
     * 投注未结算详情
     *
     * @param gameCode  游戏编码
     * @param period    期数
     * @param member    会员
     * @param oddNumber 注单号
     * @return 未结算详情
     */
    @Override
    public JsonResultBeanPlus betDetail(GameUtil.Code gameCode, String period, Object member, String oddNumber) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if(agent.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
            return bean;
        }
        try {
            DataCustomer customer = agent.customer();
            if (customer.crawler() == null) {
                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
                if (!bean.isSuccess()) {
                    return bean;
                }
                bean.setSuccess(false);
            }

            String game = IdcConfig.BET_CODE.get(gameCode.name());
            JSONObject unSettle = IdcAgentApiTool
                    .getUnsettledDetailed(customer.httpConfig(), customer.crawler().get("projectHost"), customer.crawler().get("ticket"),
                            customer.account(),(String)member, game, oddNumber);
            if (ContainerTool.isEmpty(unSettle)) {
                log.info(getMsgTitle()+"代理获取未结算明细失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
                return bean;
            }
            if ("1".equals(unSettle.get("code"))) {
                log.info(getMsgTitle()+"代理获取未结算明细错误=" + unSettle);
                bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
                bean.setMsg(unSettle.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("2".equals(unSettle.get("code"))) {
                log.info(getMsgTitle()+"代理获取未结算明细失败=" + unSettle);
//                bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
                return bean;
            }
            //更换旧票证
            if (StringTool.notEmpty(unSettle.get("ticket"))) {
                customer.crawler().put("ticket", unSettle.getString("ticket"));
            }
			JSONArray unsettledDetailed=new JSONArray();
             IdcAgentApiTool
                    .getUnsettledDetailedInfo(gameCode, unSettle.getJSONArray("data"), customer.account(),unsettledDetailed);
            bean.success(unsettledDetailed);
        } catch (Exception e) {
            log.error(getMsgTitle()+"获取投注未结算摘要失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
