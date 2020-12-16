package com.ibm.follow.connector.admin.manage3.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapAgentService;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapMemberService;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.entity.IbmManageRole;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:为角色添加盘口
 * @Author: wwj
 * @Date: 2019/11/13 18:02
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/role/add")
public class HandicapAddAction extends CommAdminAction {

    String handicapId;
    String handicapCode;
    String handicapName;

    Date nowTime;
    IbmTypeEnum type;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        handicapId = request.getParameter("HANDICAP_ID");
        handicapCode = request.getParameter("HANDICAP_CODE_");
        handicapName = request.getParameter("HANDICAP_NAME_");
        String roleId = request.getParameter("roleId");

        if (StringTool.isEmpty(handicapId, handicapCode, handicapName, roleId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmTypeEnum category = HandicapUtil.category(handicapId);

            String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",修改盘口,")
                    .concat(handicapId);

            IbmManageRoleService roleService = new IbmManageRoleService();
            IbmManageRole manageRole = roleService.find(roleId);

            type = IbmTypeEnum.valueOf(manageRole.getRoleCode());
            nowTime = new Date();

            IbmHandicapService handicapService = new IbmHandicapService();
            IbmHandicap handicapInfo = handicapService.find(handicapId);
            List<IbmTypeEnum> delTypes = new ArrayList<>();
            List<IbmTypeEnum> addTypes = new ArrayList<>();

            int onlineNumberMax = roleService.addHandicapId(handicapId, type, category, nowTime, desc);

            //提取旧数据
            IbmTypeEnum oldCategory = IbmTypeEnum.valueOf(handicapInfo.getHandicapCategory());
            IbmTypeEnum oldType = IbmTypeEnum.valueOf(handicapInfo.getHandicapType());
            // 更新盘口用户类型
            handicapService.updateByHandicapIdAndType(handicapId, adminUser.getUserName(), desc, type, nowTime);

            // 移除旧权限
            roleService.removeHandicapId(handicapId, oldType, oldCategory, nowTime, desc);
            // 移除用户数据
            getTypes(oldType, delTypes, addTypes);
            if (ContainerTool.notEmpty(delTypes)) {
                delete(handicapId, delTypes, category, desc, nowTime);
            }
            // 添加新用户盘口信息
            if (ContainerTool.notEmpty(addTypes)) {
                HandicapTool.save(handicapId, handicapCode, handicapName, onlineNumberMax, category, addTypes, desc,
                        nowTime);
            }

            bean.success();
        } catch (Exception e) {
            log.error("添加角色盘口错误", e);
            bean.error(e.getMessage());
        }
        return bean;
    }


    /**
     * 移除盘口中的所有数据
     *
     * @param handicapId 盘口主键
     * @param types      用户类型列表
     * @param category   盘口类别
     * @param desc       描述
     * @param nowTime    移除时间
     */
    public static void delete(String handicapId, List<IbmTypeEnum> types, IbmTypeEnum category, String desc,
                              Date nowTime) throws Exception {
        List<String> userIds = new AppUserService().listIdByTypes(types);
        if (ContainerTool.isEmpty(userIds)) {
            return;
        }
		LogoutHaController haController=new LogoutHaController();
		LogoutHmController hmController=new LogoutHmController();
        if (category.equal(IbmTypeEnum.AGENT)) {
            // 退出所有托管盘口代理
            List<String> handicapAgentIds = new IbmHaInfoService().listHostingHaId(handicapId, userIds);
            for (String handicapAgentId : handicapAgentIds) {
				//用户登出清理数据
				haController.execute(handicapAgentId);
            }
            // 删除盘口代理
            new IbmAdminHandicapAgentService().delByHandicapId(userIds, handicapId, nowTime, desc);
        } else {
            // 退出所有托管盘口会员
            List<String> handicapMemberIds = new IbmHmInfoService().listHostingHmId(handicapId, userIds);
            for (String handicapMemberId : handicapMemberIds) {
				hmController.execute(handicapMemberId);
            }
            // 删除盘口会员
            new IbmAdminHandicapMemberService().delByHandicapId(userIds, handicapId, nowTime, desc);
        }
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
                case ADMIN:
                    delTypes.add(IbmTypeEnum.CHARGE);
                case CHARGE:
                    delTypes.add(IbmTypeEnum.FREE);
                case FREE:
                    break;
                default:
                    throw new RuntimeException("错误的用户类型捕捉");
            }
        } else if (IbmTypeEnum.CHARGE.equal(oldType)) {
            switch (type) {
                case SYS:
                    delTypes.add(IbmTypeEnum.ADMIN);
                case ADMIN:
                    delTypes.add(IbmTypeEnum.CHARGE);
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
                case ADMIN:
                    break;
                case FREE:
                    addTypes.add(IbmTypeEnum.FREE);
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
                case CHARGE:
                    addTypes.add(IbmTypeEnum.CHARGE);
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
