package com.ibm.follow.servlet.cloud.ibm_cms_announce.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_announce.entity.IbmCmsAnnounce;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IBM消息公告 IBM_CMS_ANNOUNCE 服务类
 *
 * @author Robot
 */
public class IbmCmsAnnounceService extends BaseServicePlus {

    /**
     * 保存IBM消息公告 IBM_CMS_ANNOUNCE 对象数据
     *
     * @param entity IbmCmsAnnounce对象数据
     */
    public String save(IbmCmsAnnounce entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_cms_announce 的 IBMS_CMS_ANNOUNCE_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_cms_announce set state_='DEL' where IBMS_CMS_ANNOUNCE_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBMS_CMS_ANNOUNCE_ID_主键id数组的数据
     *
     * @param idArray 要删除 ibm_cms_announce 的 IBMS_CMS_ANNOUNCE_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_cms_announce set state_='DEL' where IBMS_CMS_ANNOUNCE_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除 ibm_cms_announce  的 IBMS_CMS_ANNOUNCE_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_cms_announce where IBMS_CMS_ANNOUNCE_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBMS_CMS_ANNOUNCE_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_cms_announce 的 IBMS_CMS_ANNOUNCE_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_cms_announce where IBMS_CMS_ANNOUNCE_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmCmsAnnounce实体信息
     *
     * @param entity IBM消息公告 IBM_CMS_ANNOUNCE 实体
     */
    public void update(IbmCmsAnnounce entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_cms_announce表主键查找 IBM消息公告 IBM_CMS_ANNOUNCE 实体
     *
     * @param id ibm_cms_announce 主键
     * @return IBM消息公告 IBM_CMS_ANNOUNCE 实体
     */
    public IbmCmsAnnounce find(String id) throws Exception {
        return (IbmCmsAnnounce) this.dao.find(IbmCmsAnnounce.class, id);

    }

    /**
     * 更新消息
     * @param cmsNotifyId 消息主键
     * @param channel 平台
     * @param cancelTimeLong 过期时间
     * @param state 状态
     * @param date 时间
     */
    public int updateAnnounce(String cmsNotifyId,String channel,long cancelTimeLong, String state, Date date) throws SQLException {
        String sql = "UPDATE `ibm_cms_announce` SET ANNOUNCE_CHANNEL_=?,CANCEL_TIME_LONG_=?,`IS_CANCEL_`=? ,`UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?,STATE_=?  WHERE `CMS_NOTIFY_ID_`=? AND STATE_!=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(channel);
        parameters.add(cancelTimeLong);
        parameters.add(IbmStateEnum.CANCEL.name().equals(state));
        parameters.add(date);
        parameters.add(System.currentTimeMillis());
        parameters.add(state);
        parameters.add(cmsNotifyId);
        parameters.add(IbmStateEnum.DEL.name());
        return super.dao.execute(sql, parameters);
    }
}
