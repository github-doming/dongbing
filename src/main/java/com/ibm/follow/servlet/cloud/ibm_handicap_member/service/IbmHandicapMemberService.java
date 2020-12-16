package com.ibm.follow.servlet.cloud.ibm_handicap_member.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.connector.pc.customer.login.MemberLoginAction;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHandicapMemberService extends BaseServiceProxy {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHandicapMember对象数据
	 */
	public String save(IbmHandicapMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_handicap_member set state_='DEL' where IBM_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_handicap_member set state_='DEL' where IBM_HANDICAP_MEMBER_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_handicap_member where IBM_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HANDICAP_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_handicap_member的 IBM_HANDICAP_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_handicap_member where IBM_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHandicapMember实体信息
	 *
	 * @param entity IbmHandicapMember实体
	 */
	public void update(IbmHandicapMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_handicap_member表主键查找IbmHandicapMember实体
	 *
	 * @param id ibm_handicap_member 主键
	 * @return IbmHandicapMember实体
	 */
	public IbmHandicapMember find(String id) throws Exception {
		return (IbmHandicapMember) this.dao.find(IbmHandicapMember.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHandicapMember数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHandicapMember数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmHandicapMember.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHandicapMember数据信息
	 *
	 * @return 可用<IbmHandicapMember>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHandicapMember.class, sql);
	}

	/**
	 * 查询该用户所有账号信息
	 *
	 * @param userId     用户id
	 * @param handicapId 盘口id
	 * @return 账号信息
	 */
	public List<Map<String, Object>> listAllAccount(String userId, String handicapId) throws SQLException {
		String sql = "SELECT ihm.IBM_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_, ihm.MEMBER_ACCOUNT_ FROM "
				+ " ibm_handicap_member ihm LEFT JOIN ibm_hm_info ihi ON ihm.IBM_HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ "
				+ " WHERE ihm.APP_USER_ID_ = ? AND ihm.HANDICAP_ID_ = ? AND ihm.STATE_ = ? AND ihi.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(userId);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.LOGIN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取 盘口会员主键
	 *
	 * @param userId         用户主键
	 * @param memberAccount  会员账户
	 * @param handicapId     盘口主键
	 * @return 盘口会员主键
	 */
	public Map<String,Object> findMemberInfo(String userId, String memberAccount, String handicapId) throws SQLException {
		String sql = "SELECT IBM_HANDICAP_MEMBER_ID_,OPERATING_,STATE_ FROM `ibm_handicap_member` where APP_USER_ID_ = ? and "
				+ " MEMBER_ACCOUNT_ = ? and HANDICAP_ID_ = ? and STATE_ != ? LIMIT 1";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(memberAccount);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql,parameterList);
	}

	/**
	 * 会员账号密码登录 修改
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param memberPassword   会员密码
	 * @param nowTime          更新时间
	 */
	public void update(String handicapMemberId, String memberPassword,String handicapItemId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_handicap_member SET MEMBER_PASSWORD_ = ?,HANDICAP_ITEM_ID_=?,OPERATING_=?,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(memberPassword);
        parameterList.add(handicapItemId);
        parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(MemberLoginAction.class.getName().concat("，会员账号密码登录"));
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 会员ID登录 修改
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param landedTimeLong   定时登录事件
	 * @param memberAccount    会员账户
	 * @param memberPassword   会员密码
	 * @param handicapItemId   盘口详情主键
	 */
	public void update(String handicapMemberId, Long landedTimeLong, String memberAccount, String memberPassword,
			String handicapItemId) throws SQLException {
		String sql = "UPDATE ibm_handicap_member set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
		List<Object> parameterList = new ArrayList<>(9);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());

		if (StringTool.notEmpty(landedTimeLong)) {
			sql = sql.concat(",LANDED_TIME_LONG_ = ? ");
			parameterList.add(landedTimeLong + 5000L);
		}else{
            sql = sql.concat(",OPERATING_ = ? ");
            parameterList.add(IbmStateEnum.LOGIN.name());
        }
		if (StringTool.notEmpty(memberAccount)) {
			sql = sql.concat(",MEMBER_ACCOUNT_ = ? ");
			parameterList.add(memberAccount);
		}
		if (StringTool.notEmpty(memberPassword)) {
			sql = sql.concat(",MEMBER_PASSWORD_ = ? ");
			parameterList.add(memberPassword);
		}
		if (StringTool.notEmpty(handicapItemId)) {
			sql = sql.concat(",HANDICAP_ITEM_ID_ = ? ");
			parameterList.add(handicapItemId);
		}
		sql = sql.concat(",DESC_ = ? where IBM_HANDICAP_MEMBER_ID_ = ? and STATE_ = ?");
		parameterList.add(MemberLoginAction.class.getName().concat("，会员ID登录"));
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取会员信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 会员信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_ITEM_ID_,OPERATING_ FROM `ibm_handicap_member` "
				+ " where IBM_HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取盘口主键
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param userId           用户主键
	 * @return 盘口主键
	 */
	public String findHandicapId(String handicapMemberId, String userId) throws SQLException {
		String sql = "SELECT HANDICAP_ID_ FROM `ibm_handicap_member` "
				+ " where IBM_HANDICAP_MEMBER_ID_ = ? and APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * 主键是否存在
	 * @param handicapMemberId 代理Id
	 *
	 **/
	public boolean isExist(String handicapMemberId) throws SQLException {
		String sql = "SELECT STATE_ FROM `ibm_handicap_member` where IBM_HANDICAP_MEMBER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.findMap(sql,parameterList));
	}

	/**
	 * 获取登录信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 登录信息
	 */
	public Map<String, Object> findLoginInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihm.HANDICAP_CODE_,ihm.HANDICAP_ID_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,ihm.MEMBER_ACCOUNT_, "
				+ " ihm.MEMBER_PASSWORD_ FROM `ibm_handicap_member` ihm LEFT JOIN ibm_handicap_item ihi ON "
				+ " ihi.IBM_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_ WHERE ihm.IBM_HANDICAP_MEMBER_ID_ = ? AND ihm.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取登录信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 登录信息
	 */
	public Map<String, Object> findLoginValiInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT ihm.HANDICAP_CODE_,ihm.HANDICAP_ID_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,ihm.MEMBER_ACCOUNT_, "
				+ " ihm.MEMBER_PASSWORD_ FROM `ibm_handicap_member` ihm LEFT JOIN ibm_handicap_item ihi ON "
				+ " ihi.IBM_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_ WHERE ihm.IBM_HANDICAP_MEMBER_ID_ = ? AND ihm.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.FIRST.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 批量获取多会员登录信息
	 *
	 * @param handicapMemberIds 盘口会员ids
	 * @return
	 */
	public Map<String, Map<String, Object>> findLoginInfo(Set<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select ihm.IBM_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_,ihm.HANDICAP_CODE_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,"
						+ "ihm.MEMBER_ACCOUNT_,ihm.MEMBER_PASSWORD_ from ibm_handicap_member ihm LEFT JOIN ibm_handicap_item ihi ON ihi.IBM_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_"
						+ " WHERE ihm.STATE_ = ? AND ihm.IBM_HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(list)) {
			return new HashMap<>(1);
		}
		Map<String, Map<String, Object>> handicapMemberInfo = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			handicapMemberInfo.put(map.get("HANDICAP_MEMBER_ID_").toString(), map);
		}
		return handicapMemberInfo;
	}

	/**
	 * 获取一分钟以内的定时登录的盘口会员
	 *
	 * @return 盘口会员信息列表
	 */
	public List<Map<String, Object>> listTimeLanded() throws SQLException {
		String sql = "SELECT HANDICAP_URL_,HANDICAP_CAPTCHA_,ihm.HANDICAP_ID_,ihi.HANDICAP_MEMBER_ID_,"
				+ " ihm.MEMBER_ACCOUNT_,MEMBER_PASSWORD_,ihm.APP_USER_ID_ FROM `ibm_handicap_member` ihm"
				+ " LEFT JOIN ibm_hm_info ihi ON IBM_HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ AND ihi.STATE_ != ? "
				+ " LEFT JOIN ibm_handicap_item ii ON ihm.HANDICAP_ITEM_ID_ = IBM_HANDICAP_ITEM_ID_ AND ii.STATE_ != ? "
				+ " WHERE LANDED_TIME_LONG_ BETWEEN ? AND ? AND ihm.STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis() + 60000L);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 逻辑删除数据 根据appUserId
	 *
	 * @param appUserId 用户ID
	 */
	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "UPDATE ibm_handicap_member SET STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}


    /**
     * 修改操作
     * @param handicapMemberId      盘口会员id
     */
    public void updateOperating(String handicapMemberId) throws SQLException {
        String sql="update ibm_handicap_member set OPERATING_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_MEMBER_ID_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapMemberId);
        super.dao.execute(sql,parameterList);
    }
    /**
     * 批量修改操作
     * @param handicapMemberIds      盘口会员ids
     */
    public void updateOperating(List<String> handicapMemberIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("update ibm_handicap_member set OPERATING_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_MEMBER_ID_ in(");
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameterList.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }

    /**
     * 修改90s前操作为登陆或登出的会员信息
     */
    public void updateOperating() throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select IBM_HANDICAP_MEMBER_ID_ from ibm_handicap_member where (OPERATING_=? or OPERATING_=?) and STATE_!=?")
                .append(" and UPDATE_TIME_LONG_ < ?");
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(IbmStateEnum.LOGOUT.name());
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(System.currentTimeMillis()-90*1000L);
        List<String> handicapMemberIds=super.dao.findStringList("IBM_HANDICAP_MEMBER_ID_",sql.toString(),parameterList);
        if(ContainerTool.isEmpty(handicapMemberIds)){
            return ;
        }
        sql.delete(0,sql.length());
        parameterList.clear();
        sql.append("update ibm_handicap_member set OPERATING_=?,UPDATE_TIME_LONG_=?,DESC_=? where IBM_HANDICAP_MEMBER_ID_ in(");
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("自动管理修改操作状态");
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameterList.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }


    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAllLoginHm(String state) throws Exception {
        String sql = "SELECT au.APP_USER_NAME_,ihi.HANDICAP_MEMBER_ID_,ihi.MEMBER_ACCOUNT_,ihm.HANDICAP_CODE_,"
                + " ihi.STATE_,ich.CLIENT_ID_,ich.CLIENT_CODE_,ic.REGISTER_IP_ FROM ibm_hm_info ihi"
                + " LEFT JOIN ibm_handicap_member ihm ON ihi.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_"
                + " LEFT JOIN app_user au ON ihi.APP_USER_ID_ = au.APP_USER_ID_"
                + " LEFT JOIN ibm_client_hm ich ON ihi.HANDICAP_MEMBER_ID_ = ich.HANDICAP_MEMBER_ID_"
                + " LEFT JOIN ibm_client ic ON ich.CLIENT_ID_ = ic.IBM_CLIENT_ID_"
                + " WHERE ihm.STATE_!=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        if(StringTool.notEmpty(state)){
            sql+=" and ihi.STATE_=?";
            parameterList.add(state);
        }
        return this.dao.findMapList(sql, parameterList);
    }
    /**
     * 获取操作为登陆和登出的盘口会员
     * @return
     * @param existHmIds
     */
    public List<String> listOperatingHaId(Set<Object> existHmIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("SELECT ich.EXIST_HM_ID_ from ibm_client_hm ich LEFT JOIN ibm_handicap_member ihm ON ich.HANDICAP_MEMBER_ID_=ihm.IBM_HANDICAP_MEMBER_ID_");
        sql.append(" where ihm.OPERATING_ IN(?,?) AND ich.EXIST_HM_ID_ in (");
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(IbmStateEnum.LOGOUT.name());
        for(Object existHmId:existHmIds){
            sql.append("?,");
            parameterList.add(existHmId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findStringList("EXIST_HM_ID_",sql.toString(),parameterList);
    }

    /**
     * 获取盘口会员信息
     * @param handicapMemberIds 盘口会员ids
     * @return
     */
    public List<Map<String, Object>> findMemberInfos(List<String> handicapMemberIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameterList = new ArrayList<>();
        sql.append("select IBM_HANDICAP_MEMBER_ID_ as HANDICAP_MEMBER_ID_,MEMBER_ACCOUNT_,MEMBER_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_CODE_");
        sql.append(" from ibm_handicap_member LEFT JOIN ibm_handicap_item on HANDICAP_ITEM_ID_=IBM_HANDICAP_ITEM_ID_ where IBM_HANDICAP_MEMBER_ID_ in(");
        for(String handicapMemberId:handicapMemberIds){
            sql.append("?,");
            parameterList.add(handicapMemberId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findMapList(sql.toString(),parameterList);
    }

    /**
     * 获取用户会员信息
     * @param appUserId     用户id
     * @return
     */
    public List<Map<String, Object>> findMemberByUserId(String appUserId) throws SQLException {
        String sql="SELECT ihm.IBM_HANDICAP_MEMBER_ID_,ihm.HANDICAP_ID_,ihi.STATE_ FROM"
                + " ibm_handicap_member ihm LEFT JOIN ibm_hm_info ihi ON ihm.IBM_HANDICAP_MEMBER_ID_=ihi.HANDICAP_MEMBER_ID_"
                + " WHERE ihm.APP_USER_ID_ =? AND ihm.STATE_ =?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }

    /**
     * 逻辑删除盘口会员相关信息
     * @param handicapMemberId      盘口会员id
     */
    public void delHmInfo(String handicapMemberId,String desc) throws SQLException {
        String sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
                + " HANDICAP_MEMBER_ID_ =? and STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(desc);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.DEL.name());

        super.dao.execute(String.format(sqlFormat, "ibm_hm_bet_item"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_notice"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_profit"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_item"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_period"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_period_vr"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_vr"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_set"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_game_set"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_hm_info"), parameterList);

        sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
                + " IBM_HANDICAP_MEMBER_ID_ =? and STATE_ != ?";
        super.dao.execute(String.format(sqlFormat, "ibm_handicap_member"), parameterList);
    }

    /**
     * 获取详情信息
     * @param handicapMemberId  盘口会员id
     * @return 详情信息
     */
    public Map<String, Object> findItemInfo(String handicapMemberId) throws SQLException {
        String sql="SELECT au.APP_USER_NAME_,ihm.MEMBER_ACCOUNT_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,ihm.LANDED_TIME_LONG_,ihm.HANDICAP_ID_"
                + " FROM ibm_handicap_member ihm LEFT JOIN ibm_handicap_item ihi ON ihm.HANDICAP_ITEM_ID_=ihi.IBM_HANDICAP_ITEM_ID_ LEFT JOIN app_user au ON ihm.APP_USER_ID_ = au.APP_USER_ID_"
                + " WHERE ihm.IBM_HANDICAP_MEMBER_ID_=? and ihm.STATE_=?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql,parameterList);
    }

	/**
	 * 获取用户名和会员名
	 * @param handicapMemberId 会员主键
	 * @return  用户名和会员名
	 */
	public Map<String, Object> findName(String handicapMemberId) throws SQLException {
    	String sql = "SELECT ihm.MEMBER_ACCOUNT_, au.APP_USER_NAME_ FROM `ibm_handicap_member` ihm "
				+ " LEFT JOIN app_user au ON ihm.APP_USER_ID_ = au.APP_USER_ID_  where IBM_HANDICAP_MEMBER_ID_ = ?"
				+ " and ihm.STATE_ != ? and au.STATE_ != ?;";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql,parameterList);
	}

    /**
     * 获取盘口会员账号信息
     * @param handicapMemberId      盘口会员id
     * @return
     */
    public Map<String, Object> findMemberAccountInfo(String handicapMemberId) throws SQLException {
        String sql="SELECT ihm.APP_USER_ID_,ihm.HANDICAP_CODE_,ihi.HANDICAP_MEMBER_ID_,ihi.MEMBER_ACCOUNT_ from"
                + " ibm_handicap_member ihm LEFT JOIN ibm_hm_info ihi ON ihm.IBM_HANDICAP_MEMBER_ID_=ihi.HANDICAP_MEMBER_ID_ "
                + " where ihm.IBM_HANDICAP_MEMBER_ID_=? ";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(handicapMemberId);
        return super.findMap(sql,parameterList);
    }

	/**
	 * 获取主键信息
	 * @param appUserId 游戏主键
	 * @param handicapCode 盘口编码
	 * @return 主键信息列表
	 */
	public List<String> listId(String appUserId, String handicapCode) throws SQLException {
		String sql = "SELECT IBM_HANDICAP_MEMBER_ID_ as key_ FROM `ibm_handicap_member` "
				+ " where HANDICAP_CODE_ = ? and APP_USER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapCode);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findStringList(sql,parameterList);
	}
}
