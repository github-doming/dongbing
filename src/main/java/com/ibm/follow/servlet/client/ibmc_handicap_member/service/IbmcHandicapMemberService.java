package com.ibm.follow.servlet.client.ibmc_handicap_member.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_handicap_member.entity.IbmcHandicapMember;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHandicapMemberService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmcHandicapMember对象数据
	 */
	public String save(IbmcHandicapMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibmc_handicap_member的 IBMC_HANDICAP_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_handicap_member set state_='DEL' where IBMC_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HANDICAP_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_handicap_member的 IBMC_HANDICAP_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibmc_handicap_member set state_='DEL' where IBMC_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibmc_handicap_member的 IBMC_HANDICAP_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_handicap_member where IBMC_HANDICAP_MEMBER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HANDICAP_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除ibmc_handicap_member的 IBMC_HANDICAP_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_handicap_member where IBMC_HANDICAP_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHandicapMember实体信息
	 * @param entity IbmcHandicapMember实体
	 */
	public void update(IbmcHandicapMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_handicap_member表主键查找IbmcHandicapMember实体
	 * @param id ibmc_handicap_member 主键
	 * @return IbmcHandicapMember实体
	 */
	public IbmcHandicapMember find(String id) throws Exception {
		return (IbmcHandicapMember) this.dao.find(IbmcHandicapMember. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHandicapMember数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmcHandicapMember数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_handicap_member where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHandicapMember. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHandicapMember数据信息
	 * @return 可用<IbmcHandicapMember>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_handicap_member  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHandicapMember. class,sql);
	}
	/**
	 * 查询是否存在盘口会员信息
	 * @param existHmId		已存在盘口会员id
	 * @return
	 */
	public IbmcHandicapMember findExist(String existHmId) throws Exception {
		String sql="select * from ibmc_handicap_member where EXIST_HM_ID_=? and STATE_!=?";
		List<Object> parameters=new ArrayList<>(2);
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.DEL.name());
		return (IbmcHandicapMember)super.dao.findObject(IbmcHandicapMember.class,sql,parameters);
	}

    /**
     * 批量新增会员账号信息
     * @param handicapMemberInfos   会员账号信息
     * @param hmInfos               会员信息
     */
    public void save(JSONArray handicapMemberInfos, Map<String, Object> hmInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_handicap_member (IBMC_HANDICAP_MEMBER_ID_,EXIST_HM_ID_,HANDICAP_MEMBER_ID_,MEMBER_ACCOUNT_,"
                + "MEMBER_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        for(int i=0;i<handicapMemberInfos.size();i++){
            JSONObject handicapMemberInfo=handicapMemberInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(hmInfos.get(handicapMemberInfo.get("HANDICAP_MEMBER_ID_")));
            parameters.add(handicapMemberInfo.get("HANDICAP_MEMBER_ID_"));
            parameters.add(handicapMemberInfo.get("MEMBER_ACCOUNT_"));
            parameters.add(handicapMemberInfo.get("MEMBER_PASSWORD_"));
            parameters.add(handicapMemberInfo.get("HANDICAP_URL_"));
            parameters.add(handicapMemberInfo.get("HANDICAP_CAPTCHA_"));
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(nowTime);
            parameters.add(nowTime.getTime());
            parameters.add(IbmStateEnum.OPEN.name());
        }
        sql.delete(sql.length()-1,sql.length());
        super.dao.execute(sql.toString(),parameters);
    }
}
