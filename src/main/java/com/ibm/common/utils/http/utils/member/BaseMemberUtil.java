package com.ibm.common.utils.http.utils.member;

import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-04-22 14:35
 * @Version: v1.0
 */
public abstract class BaseMemberUtil implements IMemberCrawler{
    protected Logger log = LogManager.getLogger(this.getClass());

    public String message="盘口【{}】会员【{}】结果信息:{}";

    /**
     * 会员爬虫信息
     */
    public Map<String, MemberCrawler> memberCrawlers;

    public void init() {
        memberCrawlers=new HashMap<>(10);
    }

    /**
     * 清除盘口会员用户数据
     *
     * @param existHmId 已存在盘口会员用户id
     */
    public void removeHmInfo(String existHmId) {
        if (StringTool.isEmpty(existHmId)) {
            return;
        }
        memberCrawlers.remove(existHmId);
    }

    @Override
    public void accountInfo(String existHmId, String memberAccount, String memberPassword, String handicapUrl,
                            String handicapCaptcha) {
        MemberCrawler member;
        if(memberCrawlers.containsKey(existHmId)){
            member=memberCrawlers.get(existHmId);
        }else{
            member=new MemberCrawler();
            memberCrawlers.put(existHmId,member);
        }
        AccountInfo accountInfo=member.getAccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);
    }
    /**
     * 登陆
     *
     * @param existHmId 盘口会员存在id
     */
    @Override
    public JsonResultBeanPlus login(String existHmId) {
        synchronized (existHmId) {
            JsonResultBeanPlus bean = new JsonResultBeanPlus();
            if(!memberCrawlers.containsKey(existHmId)){
                bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            MemberCrawler member= memberCrawlers.get(existHmId);

            if (StringTool.isEmpty(member.getProjectHost())) {
                AccountInfo accountInfo = member.getAccountInfo();
                return login(existHmId, accountInfo.getAccount(), accountInfo.getPassword(),
                        accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            }
        }
        return new JsonResultBeanPlus().success();
    }
}
