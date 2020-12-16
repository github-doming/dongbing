package com.ibm.old.v1.pc.ibm_handicap_item.t.service;

import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPcHandicapItemTService extends IbmHandicapItemTService {
    /**
     * 根据验证码和地址获取盘口Id
     *
     * @param handicapUrl     验证码
     * @param handicapCaptcha 地址
     * @return 盘口Id
     */
    public Map<String, Object> findHandicapIdByUrlCaptcha(String handicapUrl, String handicapCaptcha) throws SQLException {
        String sql = "SELECT ihi.HANDICAP_ID_,ihi.IBM_HANDICAP_ITEM_ID_,ih.HANDICAP_CODE_ FROM ibm_handicap_item ihi LEFT JOIN"
                + " ibm_handicap ih ON ihi.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ WHERE ihi.HANDICAP_URL_ = ? AND ihi.HANDICAP_CAPTCHA_ = ? AND ihi.STATE_ != ? ";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapUrl);
        parameterList.add(handicapCaptcha);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 根据验证码和地址获取Id
     *
     * @param handicapUrl     验证码
     * @param handicapCaptcha 地址
     * @return 盘口Id
     */
    public Map<String, Object> findIdByUrlCaptcha(String handicapUrl, String handicapCaptcha) throws SQLException {
        String sql = "SELECT ihi.IBM_HANDICAP_ITEM_ID_,ihi.HANDICAP_ID_,ih.HANDICAP_CODE_ FROM ibm_handicap_item ihi LEFT JOIN"
                + " ibm_handicap ih ON ihi.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ WHERE ihi.HANDICAP_URL_ = ? AND ihi.HANDICAP_CAPTCHA_ = ?"
                + " AND ihi.STATE_ != ? AND ih.STATE_ != ?  ";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(handicapUrl);
        parameterList.add(handicapCaptcha);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMap(sql, parameterList);
    }
}