package com.ibm.follow.servlet.client.ibmc_hm_bet_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.entity.IbmcHmBetInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcHmBetInfoService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmBetInfo对象数据
	 */
	public String save(IbmcHmBetInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_bet_info的 IBMC_HM_BET_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_bet_info set state_='DEL' where IBMC_HM_BET_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_BET_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_info的 IBMC_HM_BET_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_bet_info set state_='DEL' where IBMC_HM_BET_INFO_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_bet_info的 IBMC_HM_BET_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_bet_info where IBMC_HM_BET_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_BET_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_info的 IBMC_HM_BET_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_bet_info where IBMC_HM_BET_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmBetInfo实体信息
	 *
	 * @param entity IbmcHmBetInfo实体
	 */
	public void update(IbmcHmBetInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_bet_info表主键查找IbmcHmBetInfo实体
	 *
	 * @param id ibmc_hm_bet_info 主键
	 * @return IbmcHmBetInfo实体
	 */
	public IbmcHmBetInfo find(String id) throws Exception {
		return (IbmcHmBetInfo) this.dao.find(IbmcHmBetInfo.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmBetInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmBetInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmBetInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmBetInfo数据信息
	 *
	 * @return 可用<IbmcHmBetInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmBetInfo.class, sql);
	}
	/**
	 * 保存会员投注信息
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param hmBetIds    已存会员投注信息主键list
	 * @param period      期数
	 * @param gameCode    游戏code
	 * @param betInfoList 投注内容
	 * @param funds       跟投金额
	 * @return
	 */
	public String save(String existHmId, String hmBetIds, Object period, GameUtil.Code gameCode,
			List<String> betInfoList, long funds) throws Exception {
		IbmcHmBetInfo hmBetInfo = new IbmcHmBetInfo();
		hmBetInfo.setExistHmId(existHmId);
		hmBetInfo.setHmBetIdList(hmBetIds);
		hmBetInfo.setPeriod(period);
		hmBetInfo.setGameCode(gameCode.name());
		hmBetInfo.setBetInfo(betInfoList);
		hmBetInfo.setBetFundT(funds);
		hmBetInfo.setCreateTime(new Date());
		hmBetInfo.setCreateTimeLong(System.currentTimeMillis());
		hmBetInfo.setUpdateTime(new Date());
		hmBetInfo.setUpdateTimeLong(System.currentTimeMillis());
		hmBetInfo.setState(IbmStateEnum.OPEN.name());
		return super.dao.save(hmBetInfo);
	}
	/**
	 * 修改执行状态
	 *
	 * @param hmBetInfoId 投注信息主键
	 * @param state       执行状态
	 */
	public void updateState(String hmBetInfoId, String state) throws SQLException {
		String sql = "update ibmc_hm_bet_info set STATE_=? where IBMC_HM_BET_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(state);
		parameters.add(hmBetInfoId);
		super.dao.execute(sql, parameters);
	}
	/**
	 * 修改执行状态
	 *
	 * @param hmBetInfoIds 投注信息ids
	 * @param state       执行状态
	 */
	public void updateState(List<String> hmBetInfoIds, String state) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibmc_hm_bet_info set STATE_=? where IBMC_HM_BET_INFO_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		parameters.add(state);
		for(String hmBetInfoId:hmBetInfoIds){
			sql.append("?,");
			parameters.add(hmBetInfoId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(), parameters);
	}
	/**
	 * 获取投注信息code
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param period      期数
	 * @param gameCode    游戏code
	 * @param state       状态
	 * @return
	 */
	public List<String> findHmBetInfoIds(String existHmId, Object period, GameUtil.Code gameCode, String state)
			throws SQLException {
		String sql="select IBMC_HM_BET_INFO_ID_ from ibmc_hm_bet_info where EXIST_HM_ID_=?"
				+" and GAME_CODE_=? and PERIOD_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(existHmId);
        parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(state);
		List<String> list= super.dao.findStringList("IBMC_HM_BET_INFO_ID_",sql, parameters);
		if(ContainerTool.isEmpty(list)){
			return list;
		}
		updateState(list, IbmStateEnum.FINISH.name());
		return list;
	}
	/**
	 * 获取投注信息code
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param period      期数
	 * @param gameCode    游戏code
	 * @return
	 */
	public List<String> findHmBetInfoIds(String existHmId, Object period, GameUtil.Code gameCode)
			throws SQLException {
		String sql="select IBMC_HM_BET_INFO_ID_ from ibmc_hm_bet_info where EXIST_HM_ID_=?"
				+" and GAME_CODE_=? and PERIOD_=?  and (STATE_=? or STATE_=?)";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
        parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(IbmStateEnum.FAIL.name());
		parameters.add(IbmStateEnum.AGAIN.name());
		List<String> list= super.dao.findStringList("IBMC_HM_BET_INFO_ID_",sql, parameters);
		if(ContainerTool.isEmpty(list)){
			return list;
		}
		updateState(list, IbmStateEnum.FINISH.name());
		return list;
	}

	/**
	 * 获取错误投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param period    期数
	 * @param gameCode  游戏code
	 * @return
	 */
	public Map<String, String> findErrorInfo(String existHmId, Object period, GameUtil.Code gameCode, String state)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select hbi.IBMC_HM_BET_INFO_ID_,hbi.HM_BET_ID_LIST_,hbe.ERROR_BET_INFO_ from")
				.append(" ibmc_hm_bet_info hbi LEFT JOIN ibmc_hm_bet_error hbe ON hbi.IBMC_HM_BET_INFO_ID_=hbe.HM_BET_INFO_ID_")
				.append(" WHERE hbi.EXIST_HM_ID_=? and hbi.PERIOD_=? and hbi.GAME_CODE_=? and hbi.STATE_=?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(period);
		parameters.add(gameCode);
		parameters.add(state);
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(list)) {
			return null;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		Map<String, String> errorInfo = new HashMap<>(list.size());

		sql.append("update ibmc_hm_bet_info set STATE_=? where IBMC_HM_BET_INFO_ID_ in(");
		parameters.add(IbmStateEnum.FINISH.name());
		for (Map<String, Object> map : list) {
			sql.append("?,");
			parameters.add(map.get("IBMC_HM_BET_INFO_ID_"));
			String[] hmBetIdList = map.get("HM_BET_ID_LIST_").toString().split(",");
			for (String hmBetId : hmBetIdList) {
				if (errorInfo.containsKey(hmBetId)) {
					errorInfo.put(hmBetId,
							map.get(hmBetId).toString().concat(",").concat(map.get("ERROR_BET_INFO_").toString()));
				} else {
					errorInfo.put(hmBetId, map.get("ERROR_BET_INFO_").toString());
				}

			}
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
		return errorInfo;
	}
	/**
	 * @param existHmId
	 * @param period
	 * @param gameCode
	 * @return
	 */
	public Map<String, String> findFailInfo(String existHmId, Object period, GameUtil.Code gameCode, String state)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select hbi.IBMC_HM_BET_INFO_ID_,hbi.HM_BET_ID_LIST_,hbf.FAIL_BET_INFO_,hbf.DESC_ from")
				.append(" ibmc_hm_bet_info hbi LEFT JOIN ibmc_hm_bet_fail hbf ON hbi.IBMC_HM_BET_INFO_ID_=hbf.HM_BET_INFO_ID_")
				.append(" WHERE hbi.EXIST_HM_ID_=? and hbi.PERIOD_=? and hbi.GAME_CODE_=? and hbi.STATE_=?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(period);
		parameters.add(gameCode);
		parameters.add(state);
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(list)) {
			return null;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		Map<String, String> errorInfo = new HashMap<>(list.size());

		sql.append("update ibmc_hm_bet_info set STATE_=? where IBMC_HM_BET_INFO_ID_ in(");
		parameters.add(IbmStateEnum.FINISH.name());
		for (Map<String, Object> map : list) {
			sql.append("?,");
			parameters.add(map.get("IBMC_HM_BET_INFO_ID_"));
			String[] hmBetIdList = map.get("HM_BET_ID_LIST_").toString().split(",");
			for (String hmBetId : hmBetIdList) {
				if (errorInfo.containsKey(hmBetId)) {
					errorInfo.put(hmBetId, map.get(hmBetId).toString().concat(",")
							.concat(map.get("DESC_").toString()).concat(map.get("FAIL_BET_INFO_").toString()));
				} else {
					errorInfo.put(hmBetId, map.get("DESC_").toString().concat(map.get("FAIL_BET_INFO_").toString()));
				}
			}
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
		return errorInfo;
	}
}
