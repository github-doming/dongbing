package com.ibm.follow.connector.admin.manage3.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取用户信息
 * @Author: wwj
 * @Date: 2019/11/12 10:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/info") public class AppUserInfoAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String appUserId = request.getParameter("appUserId");
        System.out.println("appUserId=="+appUserId);

        try{
            Map<String, Object> data = new HashMap<>(1);
            //类型
            List<Map<String, Object>> types = new ArrayList<>(2);
            types.add(HandicapTool.getTypeMap(IbmTypeEnum.FREE));
            types.add(HandicapTool.getTypeMap(IbmTypeEnum.CHARGE));
            types.add(HandicapTool.getTypeMap(IbmTypeEnum.ADMIN));
            types.add(HandicapTool.getTypeMap(IbmTypeEnum.SYS));
            data.put("types", types);

            IbmManagePointService pointService = new IbmManagePointService();
            IbmManageTimeService timeService = new IbmManageTimeService();
            IbmAdminHandicapUserService handicapUserService = new IbmAdminHandicapUserService();


            Map<String, Object> userInfo = new IbmAdminAppUserService().listShow(appUserId);
            //可用积分-结束时间
            userInfo.put("USEABLE_POINT_", pointService.getUseablePoint(appUserId));
            userInfo.put("END_TIME_", timeService.getEndTime(appUserId));

            //拥有代理盘口
            List<String>  handicapNames = handicapUserService.listHandicapNameByUserId(IbmTypeEnum.AGENT, appUserId);
            if (ContainerTool.notEmpty(handicapNames)) {
                userInfo.put("AGENT_HANDICAP_", String.join(",", handicapNames));
            }

            //拥有会员盘口
            handicapNames = handicapUserService.listHandicapNameByUserId(IbmTypeEnum.MEMBER, appUserId);
            if (ContainerTool.notEmpty(handicapNames)) {
                userInfo.put("MEMBER_HANDICAP_", String.join(",", handicapNames));
            }

            data.put("userInfo", userInfo);
            System.out.println(data);
            return new ModelAndView("/pages/com/ibm/admin/manager/user/info.jsp", data);
        }catch (Exception e){
            log.error("错误", e);
            return new JsonResultBeanPlus().error(e.getMessage());
        }
    }
}
