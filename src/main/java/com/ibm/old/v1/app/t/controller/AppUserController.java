package com.ibm.old.v1.app.t.controller;
import com.ibm.old.v1.cloud.ibm_available_time.t.entity.IbmAvailableTimeT;
import com.ibm.old.v1.cloud.ibm_available_time.t.service.IbmAvailableTimeTService;
import com.ibm.old.v1.cloud.ibm_point.t.entity.IbmPointT;
import com.ibm.old.v1.cloud.ibm_point.t.service.IbmPointTService;
import com.ibm.old.v1.cloud.ibm_rep_available_time.t.entity.IbmRepAvailableTimeT;
import com.ibm.old.v1.cloud.ibm_rep_available_time.t.service.IbmRepAvailableTimeTService;
import com.ibm.old.v1.cloud.ibm_rep_point.t.entity.IbmRepPointT;
import com.ibm.old.v1.cloud.ibm_rep_point.t.service.IbmRepPointTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.util.Date;
/**
 * @Description: 初始化用户基本信息
 * @Author: zjj
 * @Date: 2019-05-05 11:09
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUserController {

	public void initUser(String userId) throws Exception {
		//初始化用户可用时长信息
		initAvailableTime(userId);

		//初始化用户积点信息
		initPoint(userId);

		//TODO 初始化用户盘口信息

		//TODO 初始化用户方案信息
	}
	/**
	 * 初始化用户积点信息
	 * @param userId 用户id
	 */
	private void initPoint(String userId) throws Exception {
		IbmPointTService pointTService = new IbmPointTService();
		IbmPointT pointT = pointTService.findByUserId(userId);

		if (pointT != null) {
			return;
		}

		Date nowTime = new Date();
		//初始化最初日志
		IbmRepPointTService repPointTService = new IbmRepPointTService();
		IbmRepPointT repPointT = new IbmRepPointT();
		repPointT.setAppUserId(userId);
		repPointT.setTitle("初始化最初日志");
		repPointT.setPreT(0);
		repPointT.setNumberT(0);
		repPointT.setBalanceT(0);
		repPointT.setCreateTime(nowTime);
		repPointT.setCreateTimeLong(nowTime.getTime());
		repPointT.setUpdateTime(nowTime);
		repPointT.setUpdateTimeLong(nowTime.getTime());
		repPointT.setState(IbmStateEnum.OPEN.name());
		String repPointId =  repPointTService.save(repPointT);


		//初始化积分
		pointT = new IbmPointT();
		pointT.setAppUserId(userId);
		pointT.setRepPointId(repPointId);
		pointT.setTotalPointT(0);
		pointT.setUseablePointT(0);
		pointT.setFrozenPointT(0);
		pointT.setCreateTime(nowTime);
		pointT.setCreateTimeLong(nowTime.getTime());
		pointT.setUpdateTime(nowTime);
		pointT.setUpdateTimeLong(nowTime.getTime());
		pointT.setState(IbmStateEnum.OPEN.name());
		pointTService.save(pointT);
	}
	/**
	 * 初始化用户可用时长信息
	 * @param userId 用户id
	 */
	public void initAvailableTime(String userId) throws Exception {
		IbmAvailableTimeTService availableTimeTService = new IbmAvailableTimeTService();
		IbmAvailableTimeT availableTimeT = availableTimeTService.findByUserId(userId);
		if (availableTimeT != null) {
			return;
		}
		Date nowTime = new Date();
		//初始化最初日志
		IbmRepAvailableTimeTService repAvailableTimeTService = new IbmRepAvailableTimeTService();
		IbmRepAvailableTimeT repAvailableTimeT = new IbmRepAvailableTimeT();
		repAvailableTimeT.setUserId(userId);
		repAvailableTimeT.setUsedPointT(0);
		repAvailableTimeT.setAddTimeLong(0);
		repAvailableTimeT.setStartTime(nowTime);
		repAvailableTimeT.setStartTimeLong(nowTime.getTime());
		repAvailableTimeT.setEndTime(nowTime);
		repAvailableTimeT.setEndTimeLong(nowTime.getTime());
		repAvailableTimeT.setRepEndTime(nowTime);
		repAvailableTimeT.setRepEndTimeLong(nowTime.getTime());
		repAvailableTimeT.setCreateTime(nowTime);
		repAvailableTimeT.setCreateTimeLong(nowTime.getTime());
		repAvailableTimeT.setUpdateTime(nowTime);
		repAvailableTimeT.setUpdateTimeLong(nowTime.getTime());
		repAvailableTimeT.setState(IbmStateEnum.OPEN.name());
		repAvailableTimeT.setDesc("初始化最初日志");
		String repAvailableTimeId = repAvailableTimeTService.save(repAvailableTimeT);

		//初始化可用时间
		availableTimeT = new IbmAvailableTimeT();
		availableTimeT.setAppUserId(userId);
		availableTimeT.setRepAvailableTimeId(repAvailableTimeId);
		availableTimeT.setStartTime(nowTime);
		availableTimeT.setStartTimeLong(nowTime.getTime());
		availableTimeT.setEndTime(nowTime);
		availableTimeT.setEndTimeLong(nowTime.getTime());
		availableTimeT.setCreateTime(nowTime);
		availableTimeT.setCreateTimeLong(nowTime.getTime());
		availableTimeT.setCreateUser("doming");
		availableTimeT.setUpdateTime(nowTime);
		availableTimeT.setUpdateTimeLong(nowTime.getTime());
		availableTimeT.setUpdateUser("doming");
		availableTimeT.setState(IbmStateEnum.OPEN.name());
		availableTimeTService.save(availableTimeT);
	}
}
