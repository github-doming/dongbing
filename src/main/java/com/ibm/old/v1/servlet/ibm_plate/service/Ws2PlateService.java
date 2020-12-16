package com.ibm.old.v1.servlet.ibm_plate.service;

import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_bet.t.entity.PlateWs2BetT;
import com.ibm.old.v1.servlet.ibm_plate.plate_ws2_odd.t.service.PlateWs2OddTService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Description: WS2盘口挡板服务类
 * @Author: Dongming
 * @Date: 2018-10-20 10:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2PlateService extends PlateWs2OddTService {

	public List<Map<String, Object>> findOddInfoByType(String type) throws SQLException {
		String sql = "SELECT GAMEPLAY_CODE_ , GAMEPLAY_NAME_ , ODDS_T_ FROM `plate_ws2_odd` where  GAME_CODE_ = ? and GAMEPLAY_TYPE_ = ? and state_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add("XYFT");
		parameterList.add(type);
		return this.dao.findMapList(sql, parameterList);

	}
	public Map<String, Object> findOddInfoByCode(String code) throws SQLException {
		String sql = "SELECT ODDS_T_,GAMEPLAY_TYPE_ FROM `plate_ws2_odd` where  GAME_CODE_ = ? and GAMEPLAY_CODE_ = ? and state_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add("XYFT");
		parameterList.add(code);
		return this.dao.findMap(sql, parameterList);
	}

	public String save(PlateWs2BetT entity) throws Exception {
		return dao.save(entity);
	}
	/**
	 * 挡板项目获取赔率
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> findOddInfoByType() throws SQLException {
		String sql = "SELECT GAMEPLAY_CODE_ , GAMEPLAY_NAME_ , ODDS_T_ FROM `plate_ws2_odd` where  GAME_CODE_ = ? and state_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add("XYFT");
		return this.dao.findMapList(sql, parameterList);
	}
	
}
