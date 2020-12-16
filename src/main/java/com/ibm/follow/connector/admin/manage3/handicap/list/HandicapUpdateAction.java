package com.ibm.follow.connector.admin.manage3.handicap.list;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 修改或保存盘口信息
 * @Author: Dongming
 * @Date: 2019-11-04 13:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/update", method = HttpConfig.Method.POST) public class HandicapUpdateAction
		extends CommAdminAction {
	private String handicapId;
	private String handicapCode;
	private String handicapWorth;
	private String handicapName;
	private String handicapExplain;
	private String desc;
	private String sn;
	private Date nowTime;
	private IbmTypeEnum category;
	private IbmTypeEnum type;

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}
		handicapId = request.getParameter("HANDICAP_ID_");
		handicapCode = request.getParameter("HANDICAP_CODE_");
		handicapWorth = request.getParameter("HANDICAP_WORTH_");
		handicapName = request.getParameter("HANDICAP_NAME_");
		handicapExplain = request.getParameter("HANDICAP_EXPLAIN_");
		String handicapCategory = request.getParameter("HANDICAP_CATEGORY_");
		String handicapType = request.getParameter("HANDICAP_TYPE_");
		sn = request.getParameter("SN_");
		if (StringTool.isEmpty(handicapCode, handicapName, handicapCategory, handicapType)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			category = IbmTypeEnum.valueCustomerTypeOf(handicapCategory);
			type = IbmTypeEnum.valueOf(handicapType);
			if (category == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_TYPE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
            desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",修改盘口,")
                    .concat(handicapId);

			IbmAdminHandicapService handicapService = new IbmAdminHandicapService();
			if (StringTool.isEmpty(handicapId)) {
				if (handicapService.isExist(handicapCode, handicapCategory)) {
					bean.putEnum(IbmCodeEnum.IBM_403_EXIST_HANDICAP);
					bean.putSysEnum(IbmCodeEnum.CODE_403);
					return bean;
				}
				saveHandicap(handicapService);
				return bean.success();
			}
			nowTime = new Date();

			IbmHandicap handicapInfo = handicapService.find(handicapId);
			//提取旧数据
			IbmTypeEnum oldCategory = IbmTypeEnum.valueOf(handicapInfo.getHandicapCategory());
			IbmTypeEnum oldType = IbmTypeEnum.valueOf(handicapInfo.getHandicapType());

			//更新数据
			updateHandicap(handicapService, handicapInfo);

			IbmManageRoleService roleService = new IbmManageRoleService();
			//添加新权限
			int onlineNumberMax = addRoleHandicapId(handicapService, roleService);
			//盘口类别不一致
			if (!oldCategory.equals(category)) {
				//移除旧权限
				roleService.removeHandicapId(handicapId, oldType, oldCategory, nowTime, desc);

				// 删除有关该盘口的所有信息
				HandicapTool.delete(handicapId, oldCategory, desc, nowTime);

				//将用户添加到盘口用户表里
				List<IbmTypeEnum> types = getIbmTypeEnums(type);
				HandicapTool
						.save(handicapId, handicapCode, handicapName, onlineNumberMax, category, types, desc, nowTime);
			} else {
				//移除旧权限
				roleService.removeHandicapId(handicapId, oldType, category, nowTime, desc);

				List<IbmTypeEnum> delTypes = new ArrayList<>();
				List<IbmTypeEnum> addTypes = new ArrayList<>();
				getTypes(oldType, delTypes, addTypes);

				if (ContainerTool.notEmpty(delTypes)) {
					HandicapTool.delete(handicapId, delTypes, category, desc, nowTime);
				}
				if (ContainerTool.notEmpty(addTypes)) {
					HandicapTool.save(handicapId, handicapCode, handicapName, onlineNumberMax, category, addTypes, desc,
							nowTime);
				}
			}
			bean.success();
		} catch (Exception e) {
			log.error("修改或保存盘口信息错误", e);
			throw e;
		}
		return bean;
	}

	/**
	 * 更新盘口
	 *
	 * @param handicapService 盘口服务类
	 * @param handicapInfo    盘口信息
	 */
	private void updateHandicap(IbmAdminHandicapService handicapService, IbmHandicap handicapInfo) throws Exception {
		handicapInfo.setHandicapName(handicapName);
		handicapInfo.setHandicapExplain(handicapExplain);
		handicapInfo.setHandicapCategory(category.name());
		handicapInfo.setHandicapType(type.name());
		handicapInfo.setHandicapWorthT(NumberTool.longValueT(handicapWorth));
		handicapInfo.setDesc(desc);
		handicapInfo.setSn(NumberTool.getInteger(sn, 0));
		handicapInfo.setUpdateTime(nowTime);
		handicapInfo.setUpdateTimeLong(System.currentTimeMillis());
		handicapInfo.setDesc(desc);
		handicapService.update(handicapInfo);

	}

	/**
	 * 保存盘口
	 *
	 * @param handicapService 盘口服务类
	 */
	private void saveHandicap(IbmAdminHandicapService handicapService) throws Exception {
		nowTime = new Date();
		IbmHandicap handicap = new IbmHandicap();
		handicap.setIbmHandicapId(handicapId);
		handicap.setHandicapName(handicapName);
		handicap.setHandicapCode(handicapCode);
		handicap.setHandicapExplain(handicapExplain);
		handicap.setHandicapCategory(category);
		handicap.setHandicapType(type);
		handicap.setHandicapWorthT(NumberTool.longValueT(handicapWorth));
		handicap.setDesc(desc);
		handicap.setSn(NumberTool.getInteger(sn, 0));
		handicap.setCreateTime(nowTime);
		handicap.setCreateTimeLong(System.currentTimeMillis());
		handicap.setUpdateTimeLong(System.currentTimeMillis());
		handicap.setState(IbmStateEnum.OPEN);
		handicapId = handicapService.save(handicap);

		desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",添加盘口,")
				.concat(handicapId);
		//将盘口信息保存至角色
		IbmManageRoleService roleService = new IbmManageRoleService();
		int onlineNumberMax = addRoleHandicapId(handicapService, roleService);
		//将用户添加到盘口用户表里
		List<IbmTypeEnum> types = getIbmTypeEnums(type);
		HandicapTool.save(handicapId, handicapCode, handicapName, onlineNumberMax, category, types, desc, nowTime);

	}

	/**
	 * 添加角色盘口主键
	 *
	 * @param handicapService 盘口服务类
	 * @param roleService     角色服务类
	 * @return 最大在线数
	 */
	private int addRoleHandicapId(IbmAdminHandicapService handicapService, IbmManageRoleService roleService)
			throws Exception {
		int onlineNumberMax;
		if (roleService.isExistRole(type)) {
			onlineNumberMax = roleService.addHandicapId(handicapId, type, category, nowTime, desc);
		} else {
			List<String> handicapIds = handicapService.listIdByType(type);
			onlineNumberMax = roleService.save(handicapIds, type, category, nowTime, desc);
		}
		return onlineNumberMax;
	}

	/**
	 * 获取转移类型列表
	 *
	 * @param type 类型列表
	 * @return 转移类型列表
	 */
	private List<IbmTypeEnum> getIbmTypeEnums(IbmTypeEnum type) {
		List<IbmTypeEnum> types = new ArrayList<>(4);
		switch (type) {
			case FREE:
				types.add(IbmTypeEnum.FREE);
				break;
			case CHARGE:
				types.add(IbmTypeEnum.CHARGE);
                break;
			case ADMIN:
				types.add(IbmTypeEnum.ADMIN);
                break;
			case SYS:
				types.add(IbmTypeEnum.SYS);
				break;
			default:
				throw new RuntimeException("错误的用户类型捕捉");
		}
		return types;
	}

	/**
	 * 获取转移类型列表
	 *
	 * @param oldType  类型列表
	 * @param delTypes 转移类型列表
	 * @param addTypes 新增类型列表
	 */
	private void getTypes(IbmTypeEnum oldType, List<IbmTypeEnum> delTypes, List<IbmTypeEnum> addTypes) {
		if (IbmTypeEnum.FREE.equal(oldType)) {
			switch (type) {
				case SYS:
					delTypes.add(IbmTypeEnum.ADMIN);
                    break;
				case ADMIN:
					delTypes.add(IbmTypeEnum.CHARGE);
                    break;
				case CHARGE:
					delTypes.add(IbmTypeEnum.FREE);
                    break;
				case FREE:
					break;
				default:
					throw new RuntimeException("错误的用户类型捕捉");
			}
		} else if (IbmTypeEnum.CHARGE.equal(oldType)) {
			switch (type) {
				case SYS:
					delTypes.add(IbmTypeEnum.ADMIN);
                    break;
				case ADMIN:
					delTypes.add(IbmTypeEnum.CHARGE);
                    break;
				case CHARGE:
					break;
				case FREE:
					addTypes.add(IbmTypeEnum.FREE);
					break;
				default:
					throw new RuntimeException("错误的用户类型捕捉");
			}
		} else if (IbmTypeEnum.ADMIN.equal(oldType)) {
			switch (type) {
				case SYS:
					delTypes.add(IbmTypeEnum.ADMIN);
                    break;
				case ADMIN:
					break;
				case FREE:
					addTypes.add(IbmTypeEnum.FREE);
                    break;
				case CHARGE:
					addTypes.add(IbmTypeEnum.CHARGE);
					break;
				default:
					throw new RuntimeException("错误的用户类型捕捉");
			}
		} else if (IbmTypeEnum.SYS.equal(oldType)) {
			switch (type) {
				case SYS:
					break;
				case FREE:
					addTypes.add(IbmTypeEnum.FREE);
                    break;
				case CHARGE:
					addTypes.add(IbmTypeEnum.CHARGE);
                    break;
				case ADMIN:
					addTypes.add(IbmTypeEnum.ADMIN);
					break;
				default:
					throw new RuntimeException("错误的用户类型捕捉");
			}
		} else {
			throw new RuntimeException("错误的用户类型捕捉");
		}
	}

}
