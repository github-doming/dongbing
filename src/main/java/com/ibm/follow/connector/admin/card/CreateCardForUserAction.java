package com.ibm.follow.connector.admin.card;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.connector.admin.manage2.cms.RecordNotifyDefine;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_card.entity.IbmCard;
import com.ibm.follow.servlet.cloud.ibm_card.service.IbmCardService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.entity.IbmCardOperateLog;
import com.ibm.follow.servlet.cloud.ibm_card_operate_log.service.IbmCardOperateLogService;
import com.ibm.follow.servlet.cloud.ibm_card_report.entity.IbmCardReport;
import com.ibm.follow.servlet.cloud.ibm_card_report.service.IbmCardReportService;
import com.ibm.follow.servlet.cloud.ibm_card_tree.entity.IbmCardTree;
import com.ibm.follow.servlet.cloud.ibm_card_tree.service.IbmCardTreeService;
import com.ibm.follow.servlet.cloud.ibm_card_user.entity.IbmCardUser;
import com.ibm.follow.servlet.cloud.ibm_card_user.service.IbmCardUserService;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import com.ibm.follow.servlet.cloud.ibm_manage_point.entity.IbmManagePoint;
import com.ibm.follow.servlet.cloud.ibm_manage_point.service.IbmManagePointService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.entity.IbmRepManagePoint;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service.IbmRepManagePointService;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.entity.IbmRepManageTime;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_time.service.IbmRepManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 指定用户开卡
 * @Author: wwj
 * @Date: 2020/5/8 10:29
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/card4User", method = HttpConfig.Method.POST)
public class CreateCardForUserAction extends CommAdminAction {

	Date date = new Date();
	private String cardTreeId, cardTreeName, userUserName, adminUserName,desc;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		// 用卡用户
		userUserName = StringTool.getString(dataMap, "userName", "");
		// 卡类型 日卡 DAY 周卡 WEEK 月卡MONTH
		String cardType = StringTool.getString(dataMap, "cardType", "");
		desc = StringTool.getString(dataMap, "desc", "");
		if (StringTool.isEmpty(userUserName, cardType)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean.toJsonString();
		}
		adminUserName = adminUser.getUserName();
		try {
			// 获取用户权限信息
			Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
			int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
			String cardAdminType = CardTools.selectUserGroup(permGrade);
			if (IbmTypeEnum.CARD_ADMIN.name().equals(cardAdminType)) {
				adminUserName = IbmTypeEnum.ADMIN.name();
				adminUserId = IbmTypeEnum.ADMIN.name();
			}
			IbmCardTreeService cardTreeService = new IbmCardTreeService();
			IbmCardReportService cardReportService = new IbmCardReportService();
			IbmCardAdminService cardAdminService = new IbmCardAdminService();
			IbmCardAdminPriceService cardAdminPriceService = new IbmCardAdminPriceService();

			// 查询卡类
			IbmCardTree cardTree = cardTreeService.findAssignTree(cardType);
			if (cardTree == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean.toJsonString();
			}
			cardTreeId= cardTree.getIbmCardTreeId();
			cardTreeName = cardTree.getCardTreeName();

			String userId = new IbmAdminAppUserService().findUserIdByUserName(userUserName);
			if(StringTool.isEmpty(userId)){
				bean.putEnum(IbmCodeEnum.IBM_403_USER);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			int proLongDay = 1;
			if("WEEK".equals(cardType)){
				proLongDay = 7;
			}else if("MONTH".equals(cardType)){
				proLongDay = 30;
			}

			// 1.创建卡并激活
			String cardId = createCard(userId,cardTree.getCardTreePoint(), cardTree.getCardPriceT());
			// 2.保存操作log
			saveLog(cardId);
			// 记录用户-开卡代理关联关系if(!IbmTypeEnum.ADMIN.name().equals(adminUserId)){
			cardUserRecord(cardId,adminUserId,userId,date);


			// 3.用户续费,保存积分记录
			long endTime = updateUserPoint(cardTree.getCardTreePoint(), proLongDay, userId, date);

			// 4.写入报表
			checkReport(cardReportService, adminUserId, adminUserName, IbmTypeEnum.SELF.name(), cardTree.getCardTreePoint(), cardTree.getCardPriceT());
			recordReport(cardReportService, cardAdminService,cardAdminPriceService, adminUserId, adminUserName, cardTree.getCardTreePoint(), cardTree.getCardPriceT());

			bean.success(endTime);
		} catch (Exception e) {
			log.error("新增充值卡出错", e);
			throw e;
		}
		return bean.toJsonString();
	}

	/**
	 * 更新积分,保存记录
	 */
	private long updateUserPoint(int point, int proLongDay, String userId, Date nowTime) throws Exception {

		IbmManagePointService managePointService = new IbmManagePointService();
		IbmManagePoint managePoint = managePointService.findByUserId(userId);

		IbmRepManagePointService repManagePointService = new IbmRepManagePointService();
		Map<String, Object> lastRepPoint = repManagePointService.findLastRepByUserId(userId);

		// 充值记录
		IbmRepManagePoint repPoint = new IbmRepManagePoint();
		repPoint.setPreId(lastRepPoint.get("preKey"));
		repPoint.setAppUserId(userId);
		repPoint.setTitle("激活充值卡《" + cardTreeName + "》,充值点数：" + point);
		repPoint.setPreT(managePoint.getUseablePointT());
		repPoint.setNumberT(NumberTool.intValueT(point));
		repPoint.setBalanceT(NumberTool.intValueT(point) + managePoint.getUseablePointT());
		repPoint.setCreateTime(nowTime);
		repPoint.setCreateTimeLong(System.currentTimeMillis());
		repPoint.setUpdateTime(nowTime);
		repPoint.setUpdateTimeLong(System.currentTimeMillis());
		repPoint.setState(IbmStateEnum.OPEN.name());
		repPoint.setDesc(userId);
		String repId = repManagePointService.save(repPoint);

		// 记录用户提醒消息
		RecordNotifyDefine.recordTriggerNotify(new IbmCmsNotifyService(), new IbmCmsUserNotifyService(), userId,userUserName, repPoint.getTitle(),"RECHARGE", nowTime);

		// 续费记录
		IbmRepManagePoint useRepPoint = new IbmRepManagePoint();
		useRepPoint.setPreId(repId);
		useRepPoint.setAppUserId(userId);
		useRepPoint.setTitle("续费时长《" + proLongDay + "》天,消费点数：" + point);
		useRepPoint.setPreT(repPoint.getBalanceT());
		useRepPoint.setNumberT(NumberTool.intValueT(point));
		useRepPoint.setBalanceT(repPoint.getBalanceT()- NumberTool.intValueT(point));
		useRepPoint.setCreateTime(nowTime);
		useRepPoint.setCreateTimeLong(System.currentTimeMillis());
		useRepPoint.setUpdateTime(nowTime);
		useRepPoint.setUpdateTimeLong(System.currentTimeMillis());
		useRepPoint.setState(IbmStateEnum.OPEN.name());
		useRepPoint.setDesc(userId);
		repId = repManagePointService.save(useRepPoint);

		// 记录用户提醒消息
		RecordNotifyDefine.recordTriggerNotify(new IbmCmsNotifyService(), new IbmCmsUserNotifyService(), userId,userUserName, useRepPoint.getTitle(),"USE", nowTime);

		managePoint.setRepPointId(repId);
		managePoint.setUpdateUser(adminUserName);
		managePoint.setUpdateTime(nowTime);
		managePoint.setUpdateTimeLong(nowTime.getTime());
		managePointService.update(managePoint);

		//修改到期时间
		IbmRepManageTimeService repManageTimeService = new IbmRepManageTimeService();
		Map<String, Object> lastRepTime = repManageTimeService.findLastRepByUserId(userId);

		IbmManageTimeService manageTimeService = new IbmManageTimeService();
		IbmManageTime time = manageTimeService.findByUserId(userId);

		long oneDayDateLong = (long) 24 * 60 * 60 * 1000;
		long endTime = time.getEndTimeLong();
		// 续费2种情况 --  1.账号已到期、2.账号还没到期
		if (endTime < System.currentTimeMillis()) {
			endTime = System.currentTimeMillis() + oneDayDateLong * proLongDay;
		} else {
			endTime += oneDayDateLong * proLongDay;
		}
		IbmRepManageTime repTime = new IbmRepManageTime();
		repTime.setRepPointId(repId);
		repTime.setPreId(lastRepTime.get("preKey"));
		repTime.setUserId(userId);
		repTime.setUsedPointT(point);
		repTime.setAddTimeLong(proLongDay);
		repTime.setStartTime(nowTime);
		repTime.setStartTimeLong(System.currentTimeMillis());
		repTime.setEndTime(new Date(endTime));
		repTime.setEndTimeLong(endTime);
		repTime.setRepEndTime(lastRepTime.get("END_TIME_"));
		repTime.setRepEndTimeLong(lastRepTime.get("END_TIME_LONG_"));
		repTime.setCreateTime(nowTime);
		repTime.setCreateTimeLong(System.currentTimeMillis());
		repTime.setUpdateTime(nowTime);
		repTime.setUpdateTimeLong(System.currentTimeMillis());
		repTime.setState(IbmStateEnum.OPEN.name());
		String repTimeId = new IbmRepManageTimeService().save(repTime);

		time.setRepTimeId(repTimeId);
		time.setEndTime(new Date(endTime));
		time.setEndTimeLong(endTime);
		time.setUpdateUser(userUserName);
		manageTimeService.update(time);

		return endTime;
	}

	/**
	 * 迭代记录给上级的报表情况
	 */
	private void recordReport(IbmCardReportService cardReportService, IbmCardAdminService cardAdminService,
							  IbmCardAdminPriceService cardAdminPriceService,String userId, String userName,int point, long price) throws Exception {

		long selfPrice = cardAdminPriceService.listParentInfo(cardTreeId, userId);
		price = selfPrice != 0 ? selfPrice : price;

		checkReport(cardReportService, userId, userName, IbmTypeEnum.HIGHER_UP.name(),point,price);
		Map<String, Object> parentInfo = cardAdminService.findParentInfo(userId);
		if (ContainerTool.isEmpty(parentInfo)) {
			return;
		}
		String parentId = parentInfo.get("PARENT_USER_ID_").toString();
		String parentName = parentInfo.get("PARENT_USER_NAME_").toString();
		recordReport(cardReportService, cardAdminService,cardAdminPriceService, parentId, parentName,point,price);
	}

	/**
	 * 校验用户报表信息
	 */
	private void checkReport(IbmCardReportService cardReportService, String userId, String userName, String target, int point, long price) throws Exception {
		IbmCardReport cardReport = cardReportService.findByCardTreeId(cardTreeId, userId, target);
		if (cardReport == null) {
			cardReport = new IbmCardReport();
			cardReport.setCardTreeId(cardTreeId);
			cardReport.setCardTreeName(cardTreeName);
			cardReport.setUserId(userId);
			cardReport.setUserName(userName);
			cardReport.setPointTotal(point);
			cardReport.setPriceTotalT(price);
			cardReport.setCardTotal(1);
			cardReport.setCardActivateTotal(1);
			cardReport.setEdition(1);
			cardReport.setTarget(target);
			cardReport.setReportTimeLong(System.currentTimeMillis());
			cardReport.setCreateTime(date);
			cardReport.setCreateTimeLong(System.currentTimeMillis());
			cardReport.setState(IbmStateEnum.OPEN.name());
			cardReportService.save(cardReport);
		} else {
			cardReportService.updateAllState(cardReport.getIbmCardReportId(), point, price);
		}
	}

	/**
	 * 创建充值卡
	 */
	private String createCard(String userId,int point, long price) throws Exception {
		IbmCard card = new IbmCard();
		card.setCardTreeId(cardTreeId);
		card.setOwneUserId(adminUserId);
		card.setOwnerName(adminUserName);
		card.setUseUserId(userId);
		card.setUserName(userUserName);
		card.setCardPassword(RandomTool.getNumLetter32());
		card.setCardTreeName(cardTreeName);
		card.setCardTreePoint(point);
		card.setCardPriceT(price);
		card.setCreateUserId(adminUserId);
		card.setCreaterName(adminUserName);
		card.setCreateTime(date);
		card.setCreateTimeLong(System.currentTimeMillis());
		card.setState(IbmStateEnum.ACTIVATE.name());
		card.setDesc(desc);
		return new IbmCardService().save(card);
	}

	/**
	 * 记录用户与代理关系
	 */
	private void cardUserRecord(String ibmCardId, String createUserId,String appUserId, Date nowTime) throws Exception {
		IbmCardUserService cardUserService = new IbmCardUserService();
		IbmCardUser cardUser = cardUserService.findByUserId(appUserId);
		if(cardUser==null){
			cardUser = new IbmCardUser();
			cardUser.setCardId(ibmCardId);
			cardUser.setCreateUserId(createUserId);
			cardUser.setUserId(appUserId);
			cardUser.setCreateTime(nowTime);
			cardUser.setCreateTimeLong(System.currentTimeMillis());
			cardUser.setUpdateTime(nowTime);
			cardUser.setUpdateTimeLong(System.currentTimeMillis());
			cardUser.setState(IbmStateEnum.OPEN.name());
			cardUserService.save(cardUser);
		}else{
			cardUser.setCardId(ibmCardId);
			cardUser.setCreateUserId(createUserId);
			cardUser.setUpdateTime(nowTime);
			cardUser.setUpdateTimeLong(System.currentTimeMillis());
			cardUserService.update(cardUser);
		}
	}

	/**
	 * 记录日志
	 */
	private void saveLog(String cardId) throws Exception {
		IbmCardOperateLog operateLog = new IbmCardOperateLog();
		operateLog.setCardId(cardId);
		operateLog.setUserId(adminUserId);
		operateLog.setOperateType("ADD");
		operateLog.setOperateContect("新增充值卡");
		operateLog.setCreateTime(new Date());
		operateLog.setCreateTimeLong(System.currentTimeMillis());
		operateLog.setState(IbmStateEnum.OPEN.name());
		new IbmCardOperateLogService().save(operateLog);
	}
}
