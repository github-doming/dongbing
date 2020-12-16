package com.ibm.follow.servlet.cloud.core.controller.init;

import c.a.config.SysConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.entity.IbmExpUser;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service.IbmRepManageTimeService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.develop.http.HttpTool;

import java.util.*;

/**
 * @Description: 用户注册初始化控制器
 * @Author: zjj
 * @Date: 2019-09-05 13:58
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class RegisterInitController implements CloudExecutor {

    @Override
    public String execute(String appUserId) throws Exception {
		Date nowTime = new Date();
        // 初始化数据
        //自己注册的用户默认为免费用户类型
        IbmExpRoleService expRoleService=new IbmExpRoleService();
        IbmExpRole expRole=expRoleService.findByCode(IbmTypeEnum.FREE);
        //初始化用户角色信息
        IbmExpUser expUser=initUserRole(appUserId, expRole,IbmTypeEnum.SELF.name());

        // 初始化会员盘口
        Set<String> handicapCodes = new HashSet<>(Arrays.asList(expUser.getMemberAvailableHandicap().split(",")));
        initUserMemberHandicap(appUserId, handicapCodes, expRole.getHmOnlineMax());
        //初始化代理盘口
        handicapCodes = new HashSet<>(Arrays.asList(expUser.getAgentAvailableHandicap().split(",")));
        initUserAgentHandicap(appUserId, handicapCodes, expRole.getHaOnlineMax());

		//初始化点数、时间
		initPointAndTime(appUserId, nowTime, 0, System.currentTimeMillis());

        return appUserId;
    }
	public void initPointAndTime(String appUserId, Date nowTime, long useablePoint, long endTime) throws Exception {
		// 初始化点数信息
		//校验云接口数据
		JSONObject data = new JSONObject();
		data.put("userId", appUserId);
		data.put("useablePoint", useablePoint);
		String time = System.currentTimeMillis() + "";
		data.put("time", time);
		data.put("valiDate", Md5Tool.generate(time));
		Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
		String cloudUrl = url+ "/cloud/user/api/point/editPoint";
		String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
		JSONObject result = JSON.parseObject(html);
		if (!result.getBoolean("success")) {
			return;
		}
		String repId = result.getString("data");

		IbmRepManageTime repTime = new IbmRepManageTime();
		repTime.setRepPointId(repId);
		repTime.setPreId(IbmStateEnum.FIRST.name());
		repTime.setUserId(appUserId);
		repTime.setUsedPointT(0);
		repTime.setAddTimeLong(0);
		repTime.setStartTime(nowTime);
		repTime.setStartTimeLong(nowTime.getTime());
		repTime.setEndTime(nowTime);
		repTime.setEndTimeLong(nowTime.getTime());
		repTime.setRepEndTime(nowTime);
		repTime.setRepEndTimeLong(nowTime.getTime());
		repTime.setCreateTime(nowTime);
		repTime.setCreateTimeLong(nowTime.getTime());
		repTime.setUpdateTimeLong(nowTime.getTime());
		repTime.setState(IbmStateEnum.OPEN.name());
		repId = new IbmRepManageTimeService().save(repTime);

		IbmManageTime manageTime = new IbmManageTime();
		manageTime.setAppUserId(appUserId);
		manageTime.setRepTimeId(repId);
		manageTime.setStartTime(nowTime);
		manageTime.setStartTimeLong(nowTime.getTime());
		manageTime.setEndTime(new Date(endTime));
		manageTime.setEndTimeLong(endTime);
		manageTime.setCreateUser(appUserId);
		manageTime.setCreateTime(nowTime);
		manageTime.setCreateTimeLong(nowTime.getTime());
		manageTime.setUpdateTimeLong(nowTime.getTime());
		manageTime.setState(IbmStateEnum.OPEN.name());
		new IbmManageTimeService().save(manageTime);
	}

    /**
     * 初始化用户角色信息
     *
     * @param appUserId 用户id
     * @param expRole  角色信息
     */
    private IbmExpUser initUserRole(String appUserId, IbmExpRole expRole,String createUser) throws Exception {
        IbmExpUser expUser=new IbmExpUser();
        expUser.setAppUserId(appUserId);
        expUser.setExpRoleId(expRole.getIbmExpRoleId());
        expUser.setAvailableGame(expRole.getGameCodes());
        expUser.setAgentAvailableHandicap(expRole.getAgentHandicapCodes());
        expUser.setAgentOnlineMax(expRole.getAgentOnlineMax());
        expUser.setAgentOnline(0);
        expUser.setMemberAvailableHandicap(expRole.getMemberHandicapCodes());
        expUser.setMemberOnlineMax(expRole.getMemberOnlineMax());
        expUser.setMemberOnline(0);
        expUser.setCreateUser(createUser);
        expUser.setCreateTime(new Date());
        expUser.setCreateTimeLong(System.currentTimeMillis());
        expUser.setUpdateTime(new Date());
        expUser.setUpdateTimeLong(System.currentTimeMillis());
        expUser.setState(IbmStateEnum.OPEN.name());
        new IbmExpUserService().save(expUser);
       return expUser;
    }

    /**
     * 初始化用户代理盘口
     *
     * @param userId        用户主键
     * @param handicapCodes 可用盘口codes
     * @param onlineMax     最大在线数量
     */
    public void initUserAgentHandicap(String userId, Set<String> handicapCodes, int onlineMax) throws Exception {

        //获取用户拥有的所有盘口
        IbmHaUserService haUserService = new IbmHaUserService();
        List<String> allHandicapCodes = haUserService.findHandicapByUserId(userId);
        for (String handicapCode : allHandicapCodes) {
            if (handicapCodes.contains(handicapCode)) {
                handicapCodes.remove(handicapCode);
            } else {
                haUserService.delByHandicapCode(handicapCode, "移除分配资源中不存在的盘口id");
            }
        }
        //获取用户可开启的盘口
        List<Map<String, Object>> handicaps = new IbmHandicapService().findHandicap(handicapCodes, IbmTypeEnum.AGENT);
        if (ContainerTool.notEmpty(handicaps)) {
            haUserService.saveBatch(handicaps, userId, onlineMax);
        }
    }

    /**
     * 初始化用户会员盘口
     *
     * @param userId        用户id
     * @param handicapCodes 可用盘口codes
     * @param onlineMax     最大在线数量
     */
    public void initUserMemberHandicap(String userId, Set<String> handicapCodes, int onlineMax) throws Exception {
        //获取用户拥有的所有盘口codes
        IbmHmUserService hmUserService = new IbmHmUserService();
        List<String> allHandicapCodes = hmUserService.findHandicapByUserId(userId);
        for (String handicapCode : allHandicapCodes) {
            if (handicapCodes.contains(handicapCode)) {
                handicapCodes.remove(handicapCode);
            } else {
                hmUserService.delByHandicapCode(handicapCode, "移除分配资源中不存在的盘口id");
            }
        }
        //获取用户可开启的盘口信息
        List<Map<String, Object>> handicaps = new IbmHandicapService().findHandicap(handicapCodes, IbmTypeEnum.MEMBER);
        if (ContainerTool.notEmpty(handicaps)) {
            hmUserService.saveBatch(handicaps, userId, onlineMax);
        }
    }
}
