package com.cloud.recharge.card_admin.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.entity.CardAdmin;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * 充值卡管理用户 服务类
 *
 * @author Robot
 */
public class CardAdminService extends BaseServiceProxy {

	/**
	 * 保存充值卡管理用户 对象数据
	 *
	 * @param entity CardAdmin对象数据
	 */
	public String save(CardAdmin entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_admin 的 CARD_ADMIN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_admin set state_='DEL' where CARD_ADMIN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_ADMIN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_admin 的 CARD_ADMIN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_admin set state_='DEL' where CARD_ADMIN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_admin  的 CARD_ADMIN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_admin where CARD_ADMIN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_ADMIN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_admin 的 CARD_ADMIN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_admin where CARD_ADMIN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardAdmin实体信息
	 *
	 * @param entity 充值卡管理用户 实体
	 */
	public void update(CardAdmin entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_admin表主键查找 充值卡管理用户 实体
	 *
	 * @param id card_admin 主键
	 * @return 充值卡管理用户 实体
	 */
	public CardAdmin find(String id) throws Exception {
		return dao.find(CardAdmin.class, id);
	}

	/**
	 * 根据token查找用户
	 *
	 * @param token 用户token
	 * @return 用户
	 */
	public CardAdmin findUserByToken(String token) throws Exception {
		String sql = "SELECT ca.* FROM card_admin ca LEFT JOIN card_admin_token cat ON "
				+ " ca.APP_USER_ID_ = cat.APP_USER_ID_ AND cat.STATE_ = ? WHERE cat.VALUE_ = ? AND ca.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.findObject(CardAdmin.class, sql, parameterList);
	}

	/**
	 * 根据token查找用户
	 *
	 * @param userId 用户Id
	 * @return 用户
	 */
	public CardAdmin findUserByUserId(String userId) throws Exception {
		String sql = "SELECT * FROM card_admin  WHERE  APP_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findObject(CardAdmin.class, sql, parameterList);
	}

	/**
	 * 查询是否存在
	 *
	 * @param userId 用户主键
	 * @return 存在 true
	 */
	public boolean findExist(String userId) throws SQLException {
		String sql = "SELECT CARD_ADMIN_ID_ from  card_admin where APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));
	}

	/**
	 * 查询用户状态
	 *
	 * @param userId 用户主键
	 * @return USER_STATE_, USER_TYPE_
	 */
	public Map<String, Object> findUserInfo(String userId,String userName) throws SQLException {
		String sql = "SELECT USER_STATE_ ,USER_TYPE_,USER_NAME_,USER_PATH_  from  card_admin where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		if("ADMIN".equals(userId)){
			sql += " and USER_NAME_ = ?";
			parameterList.add(userName);
		}else{
			sql += " and APP_USER_ID_ = ?";
			parameterList.add(userId);
		}
		return super.findMap(sql, parameterList);
	}

	/**
	 * 查找管理员信息
	 *
	 * @param userId 用户主键
	 * @return 管理员信息
	 */
	public Map<String, Object> findAdminInfo(String userId) throws SQLException {
		String sql = "SELECT USER_NAME_,USER_ALIAS_,USER_TYPE_,IS_ADD_,USER_STATE_,PARENT_USER_ID_,(SELECT USER_NAME_ "
				+ " FROM card_admin pca WHERE pca.APP_USER_ID_ = ca.PARENT_USER_ID_ AND pca.STATE_ = ? "
				+ " ) AS PARENT_USER_NAME_ FROM card_admin ca WHERE APP_USER_ID_ = ? AND STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}

	/**
	 * 查找管理员信息
	 *
	 * @param userId 用户主键
	 * @return 管理员信息
	 */
	public String findAdminType(String userId) throws SQLException {
		String sql = "SELECT USER_TYPE_ as key_ FROM card_admin WHERE APP_USER_ID_ = ? AND STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		return super.findString(sql, parameters);
	}

	/**
	 * 获取所有的下级代理
	 *
	 * @return 所有的下级代理
	 */
	public List<Map<String, Object>> listAllAgentInfo() throws SQLException {
		String sql = "SELECT APP_USER_ID_,USER_ALIAS_,PARENT_USER_ID_ FROM card_admin WHERE USER_TYPE_ != ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(CardTool.Type.ADMIN.getUserType());
		parameters.add(StateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取所有的下级代理
	 *
	 * @return 所有的下级代理
	 */
	public Map<String, Object> userInfo() throws SQLException {
		String sql = "SELECT APP_USER_ID_ key_,USER_ALIAS_ value_ FROM card_admin WHERE STATE_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(StateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 获取所有的下级代理
	 *
	 * @return 所有的下级代理
	 */
	public PageCoreBean<Map<String, Object>> getAgentInfo(String userState,String userPath, int pageIndex, int pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT CARD_ADMIN_ID_,APP_USER_ID_,USER_NAME_,USER_ALIAS_,USER_TYPE_,PARENT_USER_ID_,IS_ADD_"
				+ ",USER_STATE_,DESC_ FROM card_admin WHERE STATE_ = ?  AND USER_PATH_ like ? AND USER_PATH_!= ?  ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(StateEnum.OPEN.name());
		parameters.add(userPath+"%");
		parameters.add(userPath);
		if(StringTool.notEmpty(userState)){
			sql +=" AND USER_STATE_ = ? ";
			parameters.add(userState);
		}
		sql += "ORDER BY CARD_ADMIN_ID_";
		sqlCount = sqlCount + sql + ") AS t  ";
		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

	/**
	 * 获取下属代理信息
	 *
	 * @return 下属代理信息
	 */
	public List<Map<String, Object>> listAgentInfo(String userId) throws SQLException {
		String sql = "SELECT APP_USER_ID_,USER_ALIAS_,PARENT_USER_ID_ FROM card_admin "
				+ " WHERE PARENT_USER_ID_ = ? AND STATE_ = ? GROUP BY APP_USER_ID_ ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		return super.findMapList(sql, parameters);

	}

	/**
	 * 查询是否拥有修改的权限
	 *
	 * @param bean       回传信息
	 * @param subAgentId 被修改者
	 * @param userId     修改者
	 * @return 不能修改 true；
	 */
	public boolean hasEditPermission(JsonResultBeanPlus bean, String subAgentId, String userId) throws SQLException {
		// 加载代理资料
		String userType = findAdminType(userId);
		if (StringTool.isEmpty(userType)) {
			bean.putEnum(CodeEnum.CLOUD_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return true;
		}
		//是否是自己  是  false
		boolean isSelf = !subAgentId.equalsIgnoreCase(userId);
		//被修改人是否存在
		Map<String, Object> subAdminInfo = findAdminInfo(subAgentId);
		if (isSelf && ContainerTool.isEmpty(subAdminInfo)) {
			bean.putEnum(CodeEnum.CLOUD_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return true;
		}
		if(CardTool.Type.ADMIN.name().equals(subAdminInfo.remove("PARENT_USER_ID_"))){
			subAdminInfo.put("PARENT_USER_NAME_",CardTool.Type.ADMIN.name());
		}
		boolean isAdmin = !CardTool.Type.isAdmin(userType);
		if (isSelf && isAdmin) {
			//不属于从属关系
			if (!isBelong(userId, subAgentId)) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return true;
			}
		}
		Map<String, Object> data = new HashMap<>(3);
		data.put("isSelf", !isSelf);
		data.put("isAdmin", !isAdmin);
		data.put("subInfo", subAdminInfo);
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
	private boolean isBelong(String adminUserId, String subAgentId) throws SQLException {
		String sql = "SELECT CARD_ADMIN_ID_ FROM card_admin WHERE "
				+ " APP_USER_ID_ = ? AND PARENT_USER_ID_ = ? AND USER_TYPE_ != ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(subAgentId);
		parameters.add(adminUserId);
		parameters.add(CardTool.Type.ADMIN.getUserType());
		parameters.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameters));
	}

	/**
	 * 修改管理信息
	 *
	 * @param userId    修改id
	 * @param desc  用户名称
	 * @param isAdd     是否允许添加子代
	 * @param userState 状态
	 */
	public void update(String userId, String desc, Boolean isAdd, String userState) throws SQLException {
		String sql = "UPDATE card_admin set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		if (StringTool.notEmpty(desc)) {
			sql += " ,DESC_ = ? ";
			parameters.add(desc);
		}
		if (isAdd != null) {
			sql += " ,IS_ADD_ = ? ";
			parameters.add(isAdd);
		}
		if (userState != null) {
			sql += " ,USER_STATE_ = ? ";
			parameters.add(userState);
		}

		sql += " WHERE APP_USER_ID_ = ? and STATE_ = ?";
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 修改用户名称
	 *
	 * @param userId   用户主键
	 * @param userName 用户名称
	 */
	public void updateUserName(String userId, String userName) throws SQLException {
		String sql = "UPDATE card_admin set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,USER_NAME_ = ? WHERE APP_USER_ID_ = ? and STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(userName);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 下属代理信息
	 *
	 * @param userType     用户类型
	 * @param parentUserId 上级Id
	 * @return 下属代理信息  APP_USER_ID_,USER_NAME_
	 */
	public List<Map<String, Object>> listCardAdminInfo(String userType, String parentUserId) throws SQLException {
		String sql = "SELECT APP_USER_ID_,USER_NAME_,CARD_ADMIN_ID_ FROM card_admin WHERE USER_TYPE_ = ? AND STATE_ != ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userType);
		parameters.add(StateEnum.DEL.name());
		if (StringTool.notEmpty(parentUserId)) {
			sql += " AND PARENT_USER_ID_ = ? ";
			parameters.add(parentUserId);
		}

		return super.findMapList(sql, parameters);
	}

	/**
	 * 查找有子代
	 * @param userPath 父路径
	 * @return 是否
	 */
	public boolean hasSubUser(String userPath) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ FROM card_admin WHERE USER_PATH_ like ? AND USER_PATH_ != ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userPath+"%");
		parameters.add(userPath);
		parameters.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findStringList(sql,parameters));
	}

	/**
	 * 获取子级用户主键
	 * @param userIds 父主键
	 * @return 子级用户主键
	 */
	public List<String> listSubUserId(List<String> userIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ as key_ FROM card_admin WHERE STATE_ = ? and PARENT_USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		for(String userId:userIds){
			sql.append("?,");
			parameters.add(userId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findStringList(sql.toString(), parameters);
	}

	/**
	 * 获取子级用户主键
	 * @param userIds 父主键
	 * @return 子级用户主键
	 */
	public Map<String,Object> subUserInfo(List<String> userIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ key_ ,USER_ALIAS_ value_ FROM card_admin WHERE STATE_ = ? and PARENT_USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		for(String userId:userIds){
			sql.append("?,");
			parameters.add(userId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return findKVMap(sql.toString(),parameters);
	}
	/**
	 * 获取用于记录的用户信息
	 * @param userId 用户主键
	 * @return 报表信息
	 */
	public Map<String, Object> findInfo4Record(String userId) throws SQLException {
		String sql = "SELECT USER_NAME_,PARENT_USER_ID_,USER_TYPE_ FROM card_admin WHERE APP_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}
}
