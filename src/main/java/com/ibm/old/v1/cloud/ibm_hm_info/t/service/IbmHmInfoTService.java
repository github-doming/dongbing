package com.ibm.old.v1.cloud.ibm_hm_info.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_hm_info.t.entity.IbmHmInfoT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmInfoTService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmHmInfoT对象数据
	 */
	public String save(IbmHmInfoT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_hm_info的 IBM_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_info set state_='DEL' where IBM_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_INFO_ID_主键id数组的数据
	 * @param idArray 要删除ibm_hm_info的 IBM_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_info set state_='DEL' where IBM_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_hm_info的 IBM_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_info where IBM_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_INFO_ID_主键id数组的数据
	 * @param idArray 要删除ibm_hm_info的 IBM_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_info where IBM_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmInfoT实体信息
	 * @param entity IbmHmInfoT实体
	 */
	public void update(IbmHmInfoT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_info表主键查找IbmHmInfoT实体
	 * @param id ibm_hm_info 主键
	 * @return IbmHmInfoT实体
	 */
	public IbmHmInfoT find(String id) throws Exception {
		return (IbmHmInfoT) this.dao.find(IbmHmInfoT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmInfoT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmHmInfoT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmInfoT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmInfoT数据信息
	 * @return 可用<IbmHmInfoT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmInfoT. class,sql);
	}
	/**
	 * 批量插入自动更新投注状态
	 * @param autoList	用户信息list
	 * @param state		投注状态
	 */
	public void autoBettingState (List<Map<String, Object>> autoList,String state) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"INSERT INTO ibm_hm_info (IBM_HM_INFO_ID_,HANDICAP_MEMBER_ID_,GAME_CODE_,INFO_TYPE_,INFO_CONTENT_,INFO_STATE_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES ");
		List<Object> parameterList = new ArrayList<>(autoList.size() * 11);
		for(Map<String ,Object> map:autoList){
			sql.append("(?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(map.get("HANDICAP_MEMBER_ID_"));
			parameterList.add(map.get("GAME_CODE_"));
			parameterList.add("BETTING_STATE");
			parameterList.add(state);
			parameterList.add(IbmStateEnum.PROCESS.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 修改所有投注状态
	 * @param handicapMemberIds    盘口会员id
	 * @param state                投注状态
	 */
	public void allAutoBettingState(List<String> handicapMemberIds, String state) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"INSERT INTO ibm_hm_info (IBM_HM_INFO_ID_,HANDICAP_MEMBER_ID_,INFO_TYPE_,INFO_CONTENT_,INFO_STATE_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES ");
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.size() * 10);
		for(String handicapMemberId:handicapMemberIds){
			sql.append("(?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(handicapMemberId);
			parameterList.add("ALL_BETTING_STATE");
			parameterList.add(state);
			parameterList.add(IbmStateEnum.PROCESS.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 *	获取盘口会员信息
	 * @param handicapMemberId    盘口会员id
	 * @param data					 回传数据
	 */
	public void findByHm(String handicapMemberId, Map<String, Object> data,String className) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("select INFO_TYPE_,INFO_CONTENT_,GAME_CODE_ from ibm_hm_info where HANDICAP_MEMBER_ID_=? and INFO_STATE_=? and STATE_!=? ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.PROCESS.name());
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String,Object> map=super.dao.findMap(sql.toString(),parameterList);
		if(ContainerTool.isEmpty(map)){
			return ;
		}
		parameterList.clear();
		sql.delete(0,sql.length());

		sql.append("update ibm_hm_info set DESC_=?,INFO_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_=? AND STATE_!=?");
		parameterList.add(className+"获取盘口会员信息");
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		//判断
		switch (map.get("INFO_TYPE_").toString()) {
			case "BETTING_STATE":
				sql.append(" AND INFO_TYPE_=? AND GAME_CODE_=? ");
				parameterList.add(map.get("INFO_TYPE_"));
				parameterList.add(map.get("GAME_CODE_"));

				data.put("gameCode",map.get("GAME_CODE_"));
				break;
			case "ALL_BETTING_STATE":
			case "IBM_403_USER_BAN_BET":
				sql.append(" AND INFO_TYPE_=? OR INFO_TYPE_=? ");
				parameterList.add(map.get("INFO_TYPE_"));
				parameterList.add("BETTING_STATE");
				break;
			default:
				break;
		}
		super.dao.execute(sql.toString(),parameterList);

		data.put("code",map.get("INFO_TYPE_"));
		data.put("info",map.get("INFO_CONTENT_"));
	}
}
