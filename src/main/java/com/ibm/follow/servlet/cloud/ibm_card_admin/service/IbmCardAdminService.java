package com.ibm.follow.servlet.cloud.ibm_card_admin.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_card_admin.entity.IbmCardAdmin;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBM_充值卡管理用户 服务类
 *
 * @author Robot
 */
public class IbmCardAdminService extends BaseServicePlus {

    /**
     * 保存IBM_充值卡管理用户 对象数据
     *
     * @param entity IbmCardAdmin对象数据
     */
    public String save(IbmCardAdmin entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_card_admin 的 IBM_CARD_ADMIN_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_card_admin set state_='DEL' where IBM_CARD_ADMIN_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_CARD_ADMIN_ID_主键id数组的数据
     *
     * @param idArray 要删除 ibm_card_admin 的 IBM_CARD_ADMIN_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_card_admin set state_='DEL' where IBM_CARD_ADMIN_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除 ibm_card_admin  的 IBM_CARD_ADMIN_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_card_admin where IBM_CARD_ADMIN_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_CARD_ADMIN_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_card_admin 的 IBM_CARD_ADMIN_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_card_admin where IBM_CARD_ADMIN_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmCardAdmin实体信息
     *
     * @param entity IBM_充值卡管理用户 实体
     */
    public void update(IbmCardAdmin entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_card_admin表主键查找 IBM_充值卡管理用户 实体
     *
     * @param id ibm_card_admin 主键
     * @return IBM_充值卡管理用户 实体
     */
    public IbmCardAdmin find(String id) throws Exception {
        return (IbmCardAdmin) this.dao.find(IbmCardAdmin.class, id);

    }

    /**
     * 下属代理信息
     *
     * @param userType     用户类型
     * @param parentUserId 上级Id
     * @return 下属代理信息
     */
    public List<Map<String, Object>> listCardAdminInfo(String userType, String parentUserId) throws SQLException {
        String sql = "SELECT APP_USER_ID_,USER_NAME_ FROM `ibm_card_admin` WHERE USER_TYPE_ = ? AND STATE_ !=?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(userType);
        if (StringTool.notEmpty(parentUserId)) {
            sql += " AND PARENT_USER_ID_ = ? ";
            parameters.add(parentUserId);
        }
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql, parameters);
    }

    /**
     * 获取下属代理信息
     *
     * @return 下属代理信息
     */
    public List<Map<String, Object>> listAgentInfo(String userId) throws SQLException {
        String sql = "SELECT APP_USER_ID_,USER_NAME_,PARENT_USER_ID_ FROM `ibm_card_admin` where PARENT_USER_ID_ = ? and STATE_ != ? GROUP BY USER_NAME_ ";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql, parameters);
    }

    /**
     * 获取所有的下级代理
     *
     * @return 所有的下级代理
     */
    public List<Map<String, Object>> listAllAgentInfo() throws SQLException {
        String sql = "SELECT APP_USER_ID_,USER_NAME_,PARENT_USER_ID_ FROM `ibm_card_admin` where STATE_ != ? AND USER_TYPE_!=? ";
        List<Object> parameters = new ArrayList<>(1);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmTypeEnum.CARD_ADMIN.name());
        return dao.findMapList(sql, parameters);
    }

    /**
     * 获取下属代理id列表
     *
     * @param userId 代理Id
     * @return 所有的下级代理Id
     */
    public List<String> listAgentIds(String userId) throws SQLException {
        String sql = "SELECT APP_USER_ID_ FROM `ibm_card_admin` where PARENT_USER_ID_ = ? AND STATE_ != ?";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findStringList("APP_USER_ID_", sql, parameters);
    }

    /**
     * 查找用户的上级信息
     *
     * @param userId 用户Id
     */
    public Map<String, Object> findParentInfo(String userId) throws SQLException {
        String sql = "SELECT PARENT_USER_ID_,PARENT_USER_NAME_ from ibm_card_admin  where APP_USER_ID_=?  AND STATE_!=? ";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMap(sql, parameters);
    }

    /**
     * 查找管理员展示信息
     *
     * @return 管理员信息
     */
    public Map<String, Object> findAdminShowInfo(String userId) throws SQLException {
        String sql = "SELECT au.APP_USER_NAME_,au.NICKNAME_,ag.APP_GROUP_NAME_,ica.IS_ADD_,ica.PARENT_USER_NAME_,ica.STATE_, " +
                " ica.USER_TYPE_,ica.PARENT_USER_ID_ FROM app_user au " +
                " LEFT JOIN `ibm_card_admin` ica ON au.APP_USER_ID_ = ica.APP_USER_ID_ " +
                " LEFT JOIN app_user_group aug ON au.APP_USER_ID_ = aug.APP_USER_ID_ " +
                " LEFT JOIN app_group ag ON aug.APP_GROUP_ID_ = ag.APP_GROUP_ID_ " +
                " WHERE au.APP_USER_ID_ = ? AND au.STATE_ != ? AND ica.STATE_ != ? AND aug.STATE_ != ? AND ag.STATE_ != ?";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmStateEnum.DEL.name());
        return super.findMap(sql, parameters);
    }

    /**
     * 查找管理员信息
     *
     * @return 管理员信息
     */
    public Map<String, Object> findAdminInfo(String userId) throws SQLException {
        String sql = "SELECT au.APP_USER_NAME_,au.NICKNAME_,ica.IS_ADD_,ica.USER_TYPE_,ica.PARENT_USER_NAME_,ica.STATE_ FROM app_user au " +
                " LEFT JOIN `ibm_card_admin` ica ON au.APP_USER_ID_ = ica.APP_USER_ID_ " +
                " WHERE au.APP_USER_ID_ = ? AND au.STATE_ != ? AND ica.STATE_ != ?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmStateEnum.DEL.name());
        return super.findMap(sql, parameters);
    }

    /**
     * 查询是否拥有修改的权限
     *
     * @param bean        回传信息
     * @param subAgentId  被修改者
     * @param adminUserId 修改者
     * @return 不能修改 true；
     */
    public boolean hasEditPermission(JsonResultBeanPlus bean, String subAgentId, String adminUserId) throws SQLException {
        // 加载代理资料
        Map<String, Object> adminInfo = findAdminInfo(adminUserId);
        if (ContainerTool.isEmpty(adminInfo)) {
            bean.putEnum(IbmCodeEnum.IBM_404_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return true;
        }
        //是否是自己  是  false
        boolean isSelf = !subAgentId.equalsIgnoreCase(adminUserId);
        //被修改人是否存在
        Map<String, Object> subAdminInfo = findAdminInfo(subAgentId);
        if (isSelf && ContainerTool.isEmpty(subAdminInfo)) {
            bean.putEnum(IbmCodeEnum.IBM_404_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return true;
        }
        boolean isAdmin = IbmTypeEnum.cardType(adminInfo.get("USER_TYPE_"));
        if (isSelf && isAdmin) {
            //不属于从属关系
            if (!isBelong(adminUserId, subAgentId)) {
                bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return true;
            }
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("isSelf", !isSelf);
        data.put("isAdmin", !isAdmin);
        data.put("subAdminInfo", subAdminInfo);
        bean.setData(data);
        return false;
    }

    /**
     * 是否是从事关系
     *
     * @param adminUserId 上级用户ID
     * @param subAgentId  用户ID
     * @return 是 true
     */
    public boolean isBelong(String adminUserId, String subAgentId) throws SQLException {
        String sql = "SELECT IBM_CARD_ADMIN_ID_ FROM `ibm_card_admin` " +
                " where APP_USER_ID_ = ? and PARENT_USER_ID_ = ? and USER_TYPE_ != ? and STATE_ != ?";
        List<Object> parameters = new ArrayList<>(4);
        parameters.add(subAgentId);
        parameters.add(adminUserId);
        parameters.add(IbmTypeEnum.CARD_ADMIN.name());
        parameters.add(IbmStateEnum.DEL.name());
        return ContainerTool.notEmpty(super.findMapList(sql, parameters));
    }

    /**
     * 修改是否允许添加子代
     *
     * @param userId 修改id
     * @param isAdd  是否允许添加子代
     */
    public void updateIsAdd(String userId, boolean isAdd) throws SQLException {
        String sql = "UPDATE ibm_card_admin set IS_ADD_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? " +
                " WHERE APP_USER_ID_ = ? and STATE_ != ?";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(isAdd);
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        super.execute(sql, parameters);
    }

    /**
     * 修改代理状态
     *
     * @param userId 修改id
     * @param state  状态
     */
    public void updateState(String userId, String state) throws SQLException {
        String sql = "UPDATE ibm_card_admin set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? " +
                " WHERE APP_USER_ID_ = ? and STATE_ != ?";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(state);
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(userId);
        parameters.add(IbmStateEnum.DEL.name());
        super.execute(sql, parameters);
    }
    
}
