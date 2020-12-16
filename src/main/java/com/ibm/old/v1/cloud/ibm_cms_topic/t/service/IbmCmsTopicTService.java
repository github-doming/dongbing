package com.ibm.old.v1.cloud.ibm_cms_topic.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.admin.cms_topic.w.entity.CmsTopicW;
import com.ibm.old.v1.admin.cms_topic.w.service.CmsTopicWService;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.entity.IbmCmsTopicT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmCmsTopicTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmCmsTopicT对象数据
	 */
	public String save(IbmCmsTopicT entity) throws Exception {
		return dao.save(entity);
	}


	/**
	 * 逻辑删除
	 * @param id 要删除ibm_cms_topic的 IBM_CMS_TOPIC_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cms_topic set state_= ?, where IBM_CMS_TOPIC_ID_ = ? AND READ_STATE_=? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(id);
		parameters.add(2);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CMS_TOPIC_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cms_topic的 IBM_CMS_TOPIC_ID_数组
	 */
	public void delAll(String[] idArray,String className) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cms_topic set state_='DEL',set DESC_=? where IBM_CMS_TOPIC_ID_ in(" + stringBuilder.toString() + ")";
			List<Object> parameters = new ArrayList<>(1);
			parameters.add(className+"删除ibm_cms_topic的 IBM_CMS_TOPIC_ID_数组");
			dao.execute(sql, parameters);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_cms_topic的 IBM_CMS_TOPIC_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cms_topic where IBM_CMS_TOPIC_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CMS_TOPIC_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cms_topic的 IBM_CMS_TOPIC_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cms_topic where IBM_CMS_TOPIC_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCmsTopicT实体信息
	 * @param entity IbmCmsTopicT实体
	 */
	public void update(IbmCmsTopicT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cms_topic表主键查找IbmCmsTopicT实体
	 * @param id ibm_cms_message 主键
	 * @return IbmCmsTopicT实体
	 */
	public IbmCmsTopicT find(String id) throws Exception {
		return (IbmCmsTopicT) this.dao.find(IbmCmsTopicT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cms_topic where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCmsTopicT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmCmsTopicT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cms_message where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmCmsTopicT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCmsTopicT数据信息
	 * @return 可用<IbmCmsTopicT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cms_topic  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCmsTopicT. class,sql);
	}

	/**
	 * 获取未读消息数量
	 * @param userId 用户id
	 * @return 未读消息数量
	 */
	public int findUnread(String userId) throws SQLException {
		String sql = "SELECT count(*) as cnt FROM ibm_cms_topic where USER_ID_ = ? and READ_STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.UNREAD.getVal());
		String count = super.dao.findString("cnt",sql,parameterList);
		return Integer.parseInt(count);
	}

	/**
	 * 保存对象数据
	 *
	 * @param userGroup 用户组
	 * @param cmsTopicId 消息id
	 */
	public int save(String userGroup, String cmsTopicId) throws Exception {
		AppUserService appUserService = new AppUserService();
		Date nowTime = new Date();
		CmsTopicWService cmsTopicWService = new CmsTopicWService();
		CmsTopicW cmsTopicW = cmsTopicWService.find(cmsTopicId);
		List<Map<String, Object>> list = appUserService.listByAppUserType(userGroup);
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibm_cms_topic(IBM_CMS_TOPIC_ID_,TOPIC_ID_,USER_ID_,TITLE_,TOPIC_CREATE_USER_NAME_,"
				+ "TOPIC_CREATE_TIME_,TOPIC_CREATE_TIME_LONG_,READ_STATE_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		for (Map<String, Object> aList : list) {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(uuid);
			parameterList.add(cmsTopicW.getCmsTopicId());
			parameterList.add(aList.get("APP_USER_ID_"));
			parameterList.add(cmsTopicW.getTitle());
			parameterList.add(cmsTopicW.getCreateUser());
			parameterList.add(cmsTopicW.getCreateTime());
			parameterList.add(cmsTopicW.getCreateTimeLong());
			parameterList.add(IbmStateEnum.UNREAD.getVal());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add("OPEN");
		}
		sql.replace(sql.length()-1, sql.length(), ";");
		return dao.execute(sql.toString(), parameterList);
	}

}
