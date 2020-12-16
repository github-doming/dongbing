package com.ibm.old.v1.client.ibm_client_hm_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_hm_info.entity.IbmClientHmInfo;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @author Robot
 */
public class IbmClientHmInfoService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientHmInfo对象数据
	 */
	public String save(IbmClientHmInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_hm_info的 IBM_CLIENT_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_hm_info set state_='DEL' where IBM_CLIENT_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm_info的 IBM_CLIENT_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_hm_info set state_='DEL' where IBM_CLIENT_HM_INFO_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_hm_info的 IBM_CLIENT_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_hm_info where IBM_CLIENT_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm_info的 IBM_CLIENT_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_client_hm_info where IBM_CLIENT_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHmInfo实体信息
	 *
	 * @param entity IbmClientHmInfo实体
	 */
	public void update(IbmClientHmInfo entity) throws Exception {
		if (StringTool.isEmpty(entity.getIbmClientHmInfoId())) {
			dao.save(entity);
		}else{
			dao.update(entity);
		}
	}

	/**
	 * 根据ibm_client_hm_info表主键查找IbmClientHmInfo实体
	 *
	 * @param id ibm_client_hm_info 主键
	 * @return IbmClientHmInfo实体
	 */
	public IbmClientHmInfo find(String id) throws Exception {
		return (IbmClientHmInfo) this.dao.find(IbmClientHmInfo.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientHmInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientHmInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientHmInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientHmInfo数据信息
	 *
	 * @return 可用<IbmClientHmInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientHmInfo.class, sql);
	}

	/**
	 * 根据已存在盘口会员主键 查找 盘口会员信息
	 *
	 * @param existHmId 已存在盘口会员主键
	 * @return 盘口会员信息
	 */
	public IbmClientHmInfo findByExistId(String existHmId) throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_info where CLIENT_EXIST_HM_ID_ = ? and state_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmClientHmInfo) super.dao.findObject(IbmClientHmInfo.class, sql, parameterList);

	}
	/**
	 * 更新盘口会员信息
	 *
	 * @param existHmId 已存在盘口会员主键
	 * @param infoBean  信息bean
	 * @param userBean  用户bean
	 * @return 用户信息
	 */
	public Map<String, Object> updateHmInfo(String existHmId, JsonResultBeanPlus infoBean, JsonResultBeanPlus userBean)
			throws Exception {
		Date nowTime = new Date();
		IbmClientHmInfo hmInfo = findByExistId(existHmId);
		Map<String, Object> userInfo = new HashMap<>(3);
		if (hmInfo == null) {
			hmInfo = new IbmClientHmInfo();
			hmInfo.setClientExistHmId(existHmId);
			hmInfo.setCreateTime(nowTime);
			hmInfo.setCreateTimeLong(System.currentTimeMillis());
			hmInfo.setState(IbmStateEnum.OPEN.name());
		}
		if (userBean != null && userBean.isSuccess() && ContainerTool.notEmpty(userBean.getData())) {
			log.trace("盘口用户【" + existHmId + "】盘口用户信息为：" + userBean.toJsonString());
			Map<String,Object>  user = (Map<String, Object>) userBean.getData();
			// 存储盘口会员信息

			hmInfo.setMemberAccount(user.get("memberAccount"));
			hmInfo.setMemberType(user.get("memberType"));
			hmInfo.setCreditQuotaT(NumberTool.longValueT(user.get("creditQuota")));
			hmInfo.setUsedAmountT(NumberTool.longValueT(user.get("usedAmount")));
			hmInfo.setProfitAmountT(NumberTool.longValueT(user.get("profitAmount")));
			hmInfo.setUpdateTime(nowTime);
			hmInfo.setUpdateTimeLong(System.currentTimeMillis());
		}
		userInfo.put("NICKNAME", hmInfo.getMemberAccount());
		userInfo.put("BALANCE", NumberTool.doubleT(hmInfo.getCreditQuotaT() + hmInfo.getUsedAmountT()));
		userInfo.put("PROFIT", NumberTool.doubleT(hmInfo.getProfitAmountT()));

		hmInfo.setMemberInfoCode(infoBean.getCode());
		hmInfo.setMemberInfo(infoBean.getMessage());
		update(hmInfo);

		return userInfo;
	}
}
