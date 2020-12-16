package com.ibm.old.v1.cloud.ibm_sys_bet_odds_detail.w.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_sys_bet_odds_detail.w.entity.IbmSysBetOddsDetailW;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IbmSysBetOddsDetailWService extends BaseServicePlus {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String  save(IbmSysBetOddsDetailW entity) throws Exception {

		return dao.save(entity);
	}
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "update ibm_sys_bet_odds_detail set state_='DEL' where IBM_SYS_BET_ODDS_DETAIL_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 逻辑删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "update ibm_sys_bet_odds_detail set state_='DEL' where IBM_SYS_BET_ODDS_DETAIL_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}

	
	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from ibm_sys_bet_odds_detail where IBM_SYS_BET_ODDS_DETAIL_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	
/**
	 * 
	 * 物理删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if(idArray!=null){
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from ibm_sys_bet_odds_detail where IBM_SYS_BET_ODDS_DETAIL_ID_ in("
				+ stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}
		

	}
	
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(IbmSysBetOddsDetailW entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public IbmSysBetOddsDetailW find(String id) throws Exception {
		return (IbmSysBetOddsDetailW) this.dao.find(IbmSysBetOddsDetailW.class, id);

	}

	

	

	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {

			
		String sqlCount ;
		String sql ;
		ArrayList<Object> parameters = new ArrayList<>();
		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_sys_bet_odds_detail where state_!='DEL'";
			sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_sys_bet_odds_detail where state_!='DEL'";
			sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by " + sortFieldName
					+ " " + sortOrderName;
		}

		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex, pageSize
				, sqlCount);
				
		return basePage;
	}
	
	
	
	/**
	 * 
	 * 分页
	 * 
	 * @param sortFieldName
	 * @param sortOrderName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<IbmSysBetOddsDetailW> findObject(String sortFieldName, String sortOrderName,
			Integer pageIndex, Integer pageSize) throws Exception {
		String sqlCount ;
		String sql  ;

		if (StringUtil.isBlank(sortFieldName)
				|| StringUtil.isBlank(sortOrderName)) {
			sqlCount = "SELECT count(*) FROM ibm_sys_bet_odds_detail where state_!='DEL'";
			sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sqlCount = "SELECT count(*) FROM ibm_sys_bet_odds_detail where state_!='DEL'";
			sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by " +sortFieldName
					+ " " + sortOrderName;
		}

		return dao.page(IbmSysBetOddsDetailW.class,
				sql, null, pageIndex, pageSize, sqlCount);
	}
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {

		String sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by UPDATE_TIME_ desc";

		return  this.dao.findMapList( sql,null);
	}
	
	/**
	 * 
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<IbmSysBetOddsDetailW> findObjectAll() throws Exception {

		String sql = "SELECT * FROM ibm_sys_bet_odds_detail  where state_!='DEL' order by UPDATE_TIME_ desc";

		return this.dao.findObjectList(IbmSysBetOddsDetailW.class, sql);
	}



	/**
	 * 获取游戏的赔率
	 * @param gameId 游戏id
	 * @return 赔率
	 */
	public Map<Object, Object> mapOddsByGame(String gameId) throws SQLException {
		String sql ="SELECT GAME_PLAY_NAME_ as key_,ODDS_ as value_ FROM ibm_sys_bet_odds_detail WHERE GAME_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findKVMap(sql,parameterList);
	}
}
