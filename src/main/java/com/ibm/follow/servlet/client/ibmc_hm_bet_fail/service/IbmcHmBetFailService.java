package com.ibm.follow.servlet.client.ibmc_hm_bet_fail.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_hm_bet_fail.entity.IbmcHmBetFail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHmBetFailService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmBetFail对象数据
	 */
	public String save(IbmcHmBetFail entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_bet_fail的 IBMC_HM_BET_FAIL_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_bet_fail set state_='DEL' where IBMC_HM_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_fail的 IBMC_HM_BET_FAIL_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_bet_fail set state_='DEL' where IBMC_HM_BET_FAIL_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_bet_fail的 IBMC_HM_BET_FAIL_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_bet_fail where IBMC_HM_BET_FAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_BET_FAIL_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_bet_fail的 IBMC_HM_BET_FAIL_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_bet_fail where IBMC_HM_BET_FAIL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmBetFail实体信息
	 *
	 * @param entity IbmcHmBetFail实体
	 */
	public void update(IbmcHmBetFail entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_bet_fail表主键查找IbmcHmBetFail实体
	 *
	 * @param id ibmc_hm_bet_fail 主键
	 * @return IbmcHmBetFail实体
	 */
	public IbmcHmBetFail find(String id) throws Exception {
		return (IbmcHmBetFail) this.dao.find(IbmcHmBetFail.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_fail where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmBetFail数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmBetFail数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_bet_fail where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmBetFail.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmBetFail数据信息
	 *
	 * @return 可用<IbmcHmBetFail>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_bet_fail  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmBetFail.class, sql);
	}
	/**
	 * 保存错误投注信息
	 *
	 * @param existHmId   已存在盘口会员id
	 * @param hmBetInfoId 投注信息主键
	 * @param gameCode    游戏code
	 * @param period      期数
	 * @param betItems    投注信息
	 * @param betType     投注类型
	 * @param msg         错误信息
	 */
	public void save(String existHmId, String hmBetInfoId, GameUtil.Code gameCode, Object period, String betItems,
			String betType, String msg) throws Exception {
		IbmcHmBetFail hmBetFail = new IbmcHmBetFail();
		hmBetFail.setExistHmId(existHmId);
		hmBetFail.setHmBetInfoId(hmBetInfoId);
		hmBetFail.setGameCode(gameCode.name());
		hmBetFail.setPeriod(period);
		hmBetFail.setFailBetInfo(betItems);
		hmBetFail.setBetType(betType);
		hmBetFail.setCreateTime(new Date());
		hmBetFail.setCreateTimeLong(System.currentTimeMillis());
		hmBetFail.setUpdateTime(new Date());
		hmBetFail.setUpdateTimeLong(System.currentTimeMillis());
		hmBetFail.setState(IbmStateEnum.OPEN.name());
		hmBetFail.setDesc(msg);
		save(hmBetFail);
	}
	/**
	 * 获取补投信息
	 * @param existHmId		已存在盘口会员id
	 * @param period			期数
	 * @param gameCode		游戏编码
	 * @return
	 */
	public List<Map<String, Object>> findAgainBetInfo(String existHmId, Object period, GameUtil.Code gameCode)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBMC_HM_BET_FAIL_ID_,HM_BET_INFO_ID_ as betInfoId,FAIL_BET_INFO_ as betInfo,BET_TYPE_ as betType")
				.append(" from ibmc_hm_bet_fail where EXIST_HM_ID_=? and GAME_CODE_=? and PERIOD_=? and STATE_=?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(period);
		parameters.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(list)) {
			return list;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		sql.append("update ibmc_hm_bet_fail set STATE_=? where IBMC_HM_BET_FAIL_ID_ in(");
		parameters.add(IbmStateEnum.CLOSE.name());
		for (Map<String, Object> map : list) {
			map.remove("ROW_NUM");
			sql.append("?,");
			parameters.add(map.remove("IBMC_HM_BET_FAIL_ID_"));
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
		return list;
	}
}
