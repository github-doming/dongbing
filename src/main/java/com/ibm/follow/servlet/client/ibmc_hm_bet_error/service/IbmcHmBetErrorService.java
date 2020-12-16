package com.ibm.follow.servlet.client.ibmc_hm_bet_error.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet_error.entity.IbmcHmBetError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcHmBetErrorService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmBetError对象数据
	 */
	public String save(IbmcHmBetError entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_bet_error的 IBMC_HM_BET_ERROR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_bet_error set state_='DEL' where IBMC_HM_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_error的 IBMC_HM_BET_ERROR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_hm_bet_error set state_='DEL' where IBMC_HM_BET_ERROR_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_bet_error的 IBMC_HM_BET_ERROR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_bet_error where IBMC_HM_BET_ERROR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_BET_ERROR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_error的 IBMC_HM_BET_ERROR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibmc_hm_bet_error where IBMC_HM_BET_ERROR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmBetError实体信息
	 *
	 * @param entity IbmcHmBetError实体
	 */
	public void update(IbmcHmBetError entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_bet_error表主键查找IbmcHmBetError实体
	 *
	 * @param id ibmc_hm_bet_error 主键
	 * @return IbmcHmBetError实体
	 */
	public IbmcHmBetError find(String id) throws Exception {
		return (IbmcHmBetError) this.dao.find(IbmcHmBetError.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_error where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmBetError数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmBetError数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_error where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmcHmBetError.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmBetError数据信息
	 *
	 * @return 可用<IbmcHmBetError>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_error  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmBetError.class, sql);
	}
	/**
	 * 保存投注异常信息
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param hmBetInfoId  投注信息主键
	 * @param gameCode     游戏code
	 * @param period       期数
	 * @param failBetInfo  投注内容
	 * @param errorBetInfo 错误投注信息
	 * @param state        处理状态
	 * @return
	 */
	public String save(String existHmId, String hmBetInfoId, GameUtil.Code gameCode, Object period, String failBetInfo,
			String errorBetInfo, String state) throws Exception {
		IbmcHmBetError hmBetError = new IbmcHmBetError();
		hmBetError.setExistHmId(existHmId);
		hmBetError.setHmBetInfoId(hmBetInfoId);
		hmBetError.setGameCode(gameCode.name());
		hmBetError.setPeriod(period);
		hmBetError.setFailBetInfo(failBetInfo);
		hmBetError.setErrorBetInfo(errorBetInfo);
		hmBetError.setCreateTime(new Date());
		hmBetError.setCreateTimeLong(System.currentTimeMillis());
		hmBetError.setUpdateTime(new Date());
		hmBetError.setUpdateTimeLong(System.currentTimeMillis());
		hmBetError.setState(state);
		return dao.save(hmBetError);
	}
	/**
	 * 批量添加错误投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @param errorMap  错误投注项map
	 */
	public void save(String existHmId, GameUtil.Code gameCode, Object period, Map<String, Object> errorMap)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append(
				"insert into ibmc_hm_bet_error (IBMC_HM_BET_ERROR_ID_,EXIST_HM_ID_,GAME_CODE_,PERIOD_,FAIL_BET_INFO_,");
		sql.append("ERROR_BET_INFO_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES ");
		Date nowTime = new Date();
		for (Object value : errorMap.values()) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(existHmId);
			parameters.add(gameCode.name());
			parameters.add(period);
			parameters.add(value);
			parameters.add("投注金额不符合盘口限额");
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.length() - 1);
		super.dao.execute(sql.toString(), parameters);
	}
	/**
	 * 获取错误信息
	 *
	 * @param hmBetInfoIds 投注信息ids
	 * @return
	 */
	public List<Map<String, Object>> findErrorInfo(List<String> hmBetInfoIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("select hbe.HM_BET_INFO_ID_,hbi.HM_BET_ID_LIST_,hbe.FAIL_BET_INFO_,hbe.ERROR_BET_INFO_")
				.append(" from ibmc_hm_bet_error hbe LEFT JOIN ibmc_hm_bet_info hbi")
				.append(" on hbe.HM_BET_INFO_ID_=hbi.IBMC_HM_BET_INFO_ID_ where HM_BET_INFO_ID_ in(");
		for (String hmBetInfoId : hmBetInfoIds) {
			sql.append("?,");
			parameters.add(hmBetInfoId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameters);
	}
}
