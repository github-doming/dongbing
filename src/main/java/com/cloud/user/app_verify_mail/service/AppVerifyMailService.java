package com.cloud.user.app_verify_mail.service;

import com.cloud.user.app_verify_mail.entity.AppVerifyMail;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* APP认证_邮箱验证码 服务类
 * @author Robot
 */
public class AppVerifyMailService extends BaseServiceProxy {

	/**
	 * 保存APP认证_邮箱验证码 对象数据
	 * @param entity AppVerifyMail对象数据
	 */
	public String save(AppVerifyMail entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除app_verify_mail 的 APP_VERIFY_MAIL_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_verify_mail set state_='DEL' where APP_VERIFY_MAIL_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_VERIFY_MAIL_ID_主键id数组的数据
	 * @param idArray 要删除 app_verify_mail 的 APP_VERIFY_MAIL_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_verify_mail set state_='DEL' where APP_VERIFY_MAIL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 app_verify_mail  的 APP_VERIFY_MAIL_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_verify_mail where APP_VERIFY_MAIL_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_VERIFY_MAIL_ID_主键id数组的数据
	 * @param idArray 要删除app_verify_mail 的 APP_VERIFY_MAIL_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_verify_mail where APP_VERIFY_MAIL_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppVerifyMail实体信息
	 * @param entity APP认证_邮箱验证码 实体
	 */
	public void update(AppVerifyMail entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_verify_mail表主键查找 APP认证_邮箱验证码 实体
	 * @param id app_verify_mail 主键
	 * @return APP认证_邮箱验证码 实体
	 */
	public AppVerifyMail find(String id) throws Exception {
		return dao.find(AppVerifyMail.class,id);
	}
}
