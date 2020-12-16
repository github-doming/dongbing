package com.ibm.follow.servlet.client.ibmc_hm_info.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.ibmc_hm_info.entity.IbmcHmInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmcHmInfoService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmcHmInfo对象数据
	 */
	public String save(IbmcHmInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibmc_hm_info的 IBMC_HM_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibmc_hm_info set state_='DEL' where IBMC_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBMC_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_info的 IBMC_HM_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibmc_hm_info set state_='DEL' where IBMC_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibmc_hm_info的 IBMC_HM_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibmc_hm_info where IBMC_HM_INFO_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBMC_HM_INFO_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibmc_hm_info的 IBMC_HM_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibmc_hm_info where IBMC_HM_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmcHmInfo实体信息
	 *
	 * @param entity IbmcHmInfo实体
	 */
	public void update(IbmcHmInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibmc_hm_info表主键查找IbmcHmInfo实体
	 *
	 * @param id ibmc_hm_info 主键
	 * @return IbmcHmInfo实体
	 */
	public IbmcHmInfo find(String id) throws Exception {
		return (IbmcHmInfo) this.dao.find(IbmcHmInfo.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibmc_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmcHmInfo数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmcHmInfo数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibmc_hm_info where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmcHmInfo.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmcHmInfo数据信息
	 *
	 * @return 可用<IbmcHmInfo>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibmc_hm_info  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmcHmInfo.class, sql);
	}
	/**
	 * 获取客户端盘口会员信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public IbmcHmInfo findExist(String existHmId) throws Exception {
		String sql = "select * from ibmc_hm_info where EXIST_HM_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(IbmStateEnum.DEL.name());
		return (IbmcHmInfo) super.dao.findObject(IbmcHmInfo.class, sql, parameters);
	}
	/**
	 * 修改会员金额信息
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param creditQuota  信用额度
	 * @param usedQuota    余额信息
	 * @param profitAmount 盈亏余额
	 */
	public void updateAmoutInfo(String existHmId, double creditQuota, double usedQuota, double profitAmount)
			throws SQLException {
		String sql = "update ibmc_hm_info set CREDIT_QUOTA_T_=?,USED_AMOUNT_T_=?,PROFIT_AMOUNT_T_=? where EXIST_HM_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(NumberTool.longValueT(creditQuota));
		parameterList.add(NumberTool.longValueT(usedQuota));
		parameterList.add(NumberTool.longValueT(profitAmount));
		parameterList.add(existHmId);
		parameterList.add(IbmStateEnum.LOGIN.name());
		super.dao.execute(sql, parameterList);
	}

    /**
     * 批量新增会员信息
     * @param handicapMemberInfos       盘口会员信息
     * @param hmInfos                   会员信息
     * @return
     */
    public void save(JSONArray handicapMemberInfos, Map<String, Object> hmInfos) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        Date nowTime=new Date();
        sql.append("insert into ibmc_hm_info (IBMC_HM_INFO_ID_,EXIST_HM_ID_,MEMBER_ACCOUNT_,CREATE_TIME_,CREATE_TIME_LONG_,"
                + "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        for(int i=0;i<handicapMemberInfos.size();i++){
            JSONObject handicapMemberInfo=handicapMemberInfos.getJSONObject(i);
            sql.append("(?,?,?,?,?,?,?,?),");
            parameters.add(RandomTool.getNumLetter32());
            parameters.add(hmInfos.get(handicapMemberInfo.get("HANDICAP_MEMBER_ID_")));
            parameters.add(handicapMemberInfo.get("MEMBER_ACCOUNT_"));
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
