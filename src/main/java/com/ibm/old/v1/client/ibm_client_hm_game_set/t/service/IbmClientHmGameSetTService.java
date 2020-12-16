package com.ibm.old.v1.client.ibm_client_hm_game_set.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.entity.IbmClientHmGameSetT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientHmGameSetTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientHmGameSetT对象数据
	 */
	public String save(IbmClientHmGameSetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_hm_game_set的 IBM_CLIENT_HM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_hm_game_set set state_='DEL' where IBM_CLIENT_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm_game_set的 IBM_CLIENT_HM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_hm_game_set set state_='DEL' where IBM_CLIENT_HM_GAME_SET_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_hm_game_set的 IBM_CLIENT_HM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_hm_game_set where IBM_CLIENT_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_hm_game_set的 IBM_CLIENT_HM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_client_hm_game_set where IBM_CLIENT_HM_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHmGameSetT实体信息
	 *
	 * @param entity IbmClientHmGameSetT实体
	 */
	public void update(IbmClientHmGameSetT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_hm_game_set表主键查找IbmClientHmGameSetT实体
	 *
	 * @param id ibm_client_hm_game_set 主键
	 * @return IbmClientHmGameSetT实体
	 */
	public IbmClientHmGameSetT find(String id) throws Exception {
		return (IbmClientHmGameSetT) this.dao.find(IbmClientHmGameSetT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientHmGameSetT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientHmGameSetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_hm_game_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientHmGameSetT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientHmGameSetT数据信息
	 *
	 * @return 可用<IbmClientHmGameSetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_game_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientHmGameSetT.class, sql);
	}

	/**
	 * 找到已存在的客户端盘口会员游戏设置
	 *
	 * @param existHmId 盘口会员已存在id
	 * @param gameId    游戏id
	 * @return 客户端盘口会员游戏设置
	 */
	public IbmClientHmGameSetT findExist(String existHmId, String gameId) throws Exception {
		String sql = "SELECT * FROM ibm_client_hm_game_set "
				+ "  where CLIENT_EXIST_HM_ID_ = ? and GAME_ID_ = ? and state_ !='DEL'";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(gameId);
		return (IbmClientHmGameSetT) super.dao.findObject(IbmClientHmGameSetT.class, sql, parameters);
	}

	/**
	 * 获取开始投注的盘口会员信息
	 *
	 * @param existHmId 盘口会员已存在id
	 * @param gameId    游戏id
	 * @return 开始投注的盘口会员信息
	 */
	public Map<String, Object> findBetHmInfo(String existHmId, String gameId) throws SQLException {
		String sql = "SELECT hgs.GAME_CODE_,hgs.BET_SECOND_,hgs.SPLIT_TWO_SIDE_AMOUNT_,hgs.BET_STATE_,hgs.SPLIT_NUMBER_AMOUNT_,"
				+ " hgs.BET_LIMIT_ FROM ibm_client_hm_game_set hgs WHERE hgs.CLIENT_EXIST_HM_ID_ = ? AND hgs.GAME_ID_ = ?"
				+ " AND hgs.state_ != ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);
	}
	/**
	 * 修改盘口会员所有盘口游戏投注状态
	 * @param clientExistHmId		已存在盘口会员id
	 */
	public int findAllExist(String clientExistHmId) throws SQLException {
		String sql = "update ibm_client_hm_game_set set BET_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? "
				+ "  where CLIENT_EXIST_HM_ID_ = ? and state_ !='DEL'";
		List<Object> parameters = new ArrayList<>(4);
		Date nowTime=new Date();
		parameters.add(IbmTypeEnum.FALSE.name());
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(clientExistHmId);
		return super.dao.execute(sql,parameters);
	}
}
