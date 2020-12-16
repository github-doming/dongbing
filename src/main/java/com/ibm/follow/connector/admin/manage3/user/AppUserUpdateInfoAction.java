package com.ibm.follow.connector.admin.manage3.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.entity.IbmRepManagePoint;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service.IbmRepManagePointService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service.IbmRepManageTimeService;
import org.apache.commons.collections.CollectionUtils;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 更新用户信息
 * @Author: wwj
 * @Date: 2019/11/12 10:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/user/update")
public class AppUserUpdateInfoAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try {
            String userId = request.getParameter("APP_USER_ID_");
            String changeuserPointStr = request.getParameter("USEABLE_POINT_");
            String userType = request.getParameter("USER_TYPE_");
            String endTimeStr = request.getParameter("END_TIME_");

            Date nowTime = new Date();
            IbmRepManagePointService repManagePointService = new IbmRepManagePointService();
            Map<String, Object> pointRep = repManagePointService.findLastRepByUserId(userId);
            String repPointId = "";
            //修改点数
            if (!StringTool.isEmpty(changeuserPointStr)) {
                repPointId = updateUserPoint(userId, changeuserPointStr, nowTime, repManagePointService, pointRep, repPointId);
            }
            // 修改到期时间
            if (!StringTool.isEmpty(endTimeStr)) {
                updateUserEndTime(userId, endTimeStr, nowTime, pointRep, repPointId);

            }


            IbmAdminAppUserService appUserService = new IbmAdminAppUserService();
            Map<String, Object> userInfo = appUserService.listShow(userId);
            // 修改用户类型
            if (!userInfo.get("APP_USER_TYPE_").equals(userType)) {
                appUserService.updateUserTypeByAppUserId(userId, userType, nowTime);

                IbmManageRoleService roleService = new IbmManageRoleService();
                Map<String, Object> handicapInfo = roleService.getRoleHaveHandicp(userType);

                int onLineHmMax = Integer.parseInt(handicapInfo.get("ONLINE_HA_NUMBER_MAX_").toString());
                int onLineHaMax = Integer.parseInt(handicapInfo.get("ONLINE_HA_NUMBER_MAX_").toString());

                //更新会员盘口
                updateUserMemberHandicap((String) handicapInfo.get("hmIds"), userId, onLineHmMax);
                //更新代理盘口
                updateUserAgentHandicap((String) handicapInfo.get("haIds"), userId, onLineHaMax);
            }

            return new JsonResultBeanPlus().success();
        } catch (Exception e) {
            log.error("修改用户类型错误", e);
            return new JsonResultBeanPlus().error(e.getMessage());
        }
    }

    private void updateUserEndTime(String userId, String endTimeStr, Date nowTime, Map<String, Object> pointRep, String repPointId) throws Exception {
        IbmManageTimeService timeService = new IbmManageTimeService();
        Long endLongTime = timeService.getEndLongTime(userId);
        Long changeLongTime = DateTool.get(endTimeStr, new SimpleDateFormat("yyyy-MM-dd HH:mm")).getTime();
        System.out.println("changeLongTime==" + changeLongTime);
        if (endLongTime != changeLongTime) {
            IbmRepManageTimeService repManageTimeService = new IbmRepManageTimeService();
            Map<String, Object> repTimeInfo = repManageTimeService.findLastRepByUserId(userId);
            if (StringTool.isEmpty(repPointId)) {
                repPointId = (String) pointRep.get("preKey");
            }
            Date endDate = new Date(changeLongTime);
            IbmRepManageTime repTime = new IbmRepManageTime();
            repTime.setRepPointId(repPointId);
            repTime.setPreId(repTimeInfo.get("preKey"));
            repTime.setUserId(userId);
            repTime.setUsedPointT(0);
            repTime.setAddTimeLong(0);
            repTime.setStartTime(repTimeInfo.get("START_TIME_"));
            repTime.setStartTimeLong(repTimeInfo.get("START_TIME_LONG_"));
            repTime.setEndTime(endDate);
            repTime.setEndTimeLong(changeLongTime);
            repTime.setRepEndTime(repTimeInfo.get("END_TIME_"));
            repTime.setRepEndTimeLong(repTimeInfo.get("END_TIME_LONG_"));
            repTime.setCreateTime(nowTime);
            repTime.setCreateTimeLong(System.currentTimeMillis());
            repTime.setUpdateTimeLong(System.currentTimeMillis());
            repTime.setState(IbmStateEnum.OPEN.name());
            String repId = new IbmRepManageTimeService().save(repTime);

            timeService.updateEndTime(userId, repId, endDate, nowTime);
        }
    }

    private String updateUserPoint(String userId, String changeuserPointStr, Date nowTime, IbmRepManagePointService repManagePointService, Map<String, Object> pointRep, String repPointId) throws Exception {
        IbmManagePointService pointService = new IbmManagePointService();
        Double defaultPoint = pointService.getUseablePoint(userId);
        Double changeuserPoint = Double.parseDouble(changeuserPointStr);
        if (defaultPoint - changeuserPoint != 0) {
            String title = adminUser.getUserName().concat(",修改点数:").concat(String.valueOf(changeuserPoint));
            IbmRepManagePoint repPoint = new IbmRepManagePoint();
            repPoint.setPreId(pointRep.get("preKey"));
            repPoint.setAppUserId(userId);
            repPoint.setTitle(title);
            repPoint.setPreT(pointRep.get("BALANCE_T_"));
            long number = changeuserPoint.longValue();
            long balance = defaultPoint.longValue() + number;
            repPoint.setNumberT(number);
            repPoint.setBalanceT(balance);
            repPoint.setCreateTime(nowTime);
            repPoint.setCreateTimeLong(System.currentTimeMillis());
            repPoint.setUpdateTimeLong(System.currentTimeMillis());
            repPoint.setState(IbmStateEnum.OPEN.name());
            repPointId = repManagePointService.save(repPoint);

            pointService.updatePointByAppUserId(userId, repPointId, number);

        }
        return repPointId;
    }

    /**
     * 更新用户会员盘口
     *
     * @param userId 用户主键
     */
    private void updateUserMemberHandicap(String hmIds, String userId, int onLineHmMax) throws Exception {

        //转换字符串为列表
        Set<String> handicapIds = new HashSet<>();
        CollectionUtils.addAll(handicapIds, hmIds.split(","));

        //获取用户拥有的所有盘口
        IbmHmUserService hmUserService = new IbmHmUserService();
        List<String> allHandicapIds = hmUserService.findHandicapByUserId(userId);

        System.out.println("handicapIds==" + handicapIds);
        System.out.println("allHandicapIds==" + allHandicapIds);
        for (String handicapId : allHandicapIds) {
            if (handicapIds.contains(handicapId.trim())) {
                handicapIds.remove(handicapId);
            } else {
                hmUserService.updateByAppUserId(userId, handicapId);
            }
        }
        //获取用户可开启的盘口
        List<Map<String, Object>> handicaps = new IbmHandicapService().findHandicap(String.join(",", handicapIds));
        if (ContainerTool.notEmpty(handicaps)) {
            hmUserService.saveBatch(handicaps, userId, onLineHmMax);
        }
    }

    /**
     * 更新用户代理盘口
     *
     * @param userId 用户主键
     */
    private void updateUserAgentHandicap(String haIds, String userId, int onLineHaMax) throws Exception {
        //转换字符串为列表
        Set<String> handicapIds = new HashSet<>();
        CollectionUtils.addAll(handicapIds, haIds.split(","));

        //获取用户拥有的所有盘口
        IbmHaUserService haUserService = new IbmHaUserService();
        List<String> allHandicapIds = haUserService.findHandicapByUserId(userId);

        System.out.println("allHandicapIds==" + allHandicapIds);
        for (String handicapId : allHandicapIds) {
            if (handicapIds.contains(handicapId.trim())) {
                handicapIds.remove(handicapId);
            } else {
                haUserService.delByAppUserId(userId, handicapId);
            }
        }
        //获取用户可开启的盘口
        List<Map<String, Object>> handicaps = new IbmHandicapService().findHandicap(String.join(",", handicapIds));
        if (ContainerTool.notEmpty(handicaps)) {
            haUserService.saveBatch(handicaps, userId, onLineHaMax);
        }
    }


}
