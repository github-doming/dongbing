package com.cloud.user.app_verify_mobile.service;

import com.cloud.user.app_verify_mobile.entity.AppVerifyMobile;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* APP认证_手机短信验证码 服务类
 * @author Robot
 */
public class AppVerifyMobileService extends BaseServiceProxy {

	/**
	 * 保存APP认证_手机短信验证码 对象数据
	 * @param entity AppVerifyMobile对象数据
	 */
	public String save(AppVerifyMobile entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除app_verify_mobile 的 APP_VERIFY_MOBILE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_verify_mobile set state_='DEL' where APP_VERIFY_MOBILE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_VERIFY_MOBILE_ID_主键id数组的数据
	 * @param idArray 要删除 app_verify_mobile 的 APP_VERIFY_MOBILE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_verify_mobile set state_='DEL' where APP_VERIFY_MOBILE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 app_verify_mobile  的 APP_VERIFY_MOBILE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_verify_mobile where APP_VERIFY_MOBILE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_VERIFY_MOBILE_ID_主键id数组的数据
	 * @param idArray 要删除app_verify_mobile 的 APP_VERIFY_MOBILE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_verify_mobile where APP_VERIFY_MOBILE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppVerifyMobile实体信息
	 * @param entity APP认证_手机短信验证码 实体
	 */
	public void update(AppVerifyMobile entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_verify_mobile表主键查找 APP认证_手机短信验证码 实体
	 * @param id app_verify_mobile 主键
	 * @return APP认证_手机短信验证码 实体
	 */
	public AppVerifyMobile find(String id) throws Exception {
		return dao.find(AppVerifyMobile.class,id);
	}
}
