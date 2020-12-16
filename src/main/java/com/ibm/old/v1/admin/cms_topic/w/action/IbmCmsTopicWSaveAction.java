package com.ibm.old.v1.admin.cms_topic.w.action;

import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.admin.cms_topic.w.entity.CmsTopicW;
import com.ibm.old.v1.admin.cms_topic.w.service.CmsTopicWService;
import com.ibm.old.v1.cloud.ibm_cms_topic.t.service.IbmCmsTopicTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.util.Date;

/**
 * 
 * 
 * @Description:发送消息 
 * @ClassName: IbmCmsTopicWSaveAction 
 * @date 2019年1月15日 下午5:59:15 
 * @author wck
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class IbmCmsTopicWSaveAction extends BaseAppAction{
	@Override
	public String run() throws Exception {
		
		String topicTitle = request.getParameter("TITLE_");
		String content = request.getParameter("CONTENT_");
		String userGroup = request.getParameter("APP_USER_TYPE_");
		
		//TODO 判断管理员是否有权限发送消息
		//用户组是否存在
		AppUserService appUserService = new AppUserService();
		int count = Integer.parseInt(appUserService.findByType(userGroup));
		if(count<=0){
			return null;
		}
		IbmCmsTopicTService ibmCmsTopicTService = new IbmCmsTopicTService();
		Date nowTime = new Date();
		
		CmsTopicW cmsTopicW = new CmsTopicW();
		cmsTopicW.setTitle(topicTitle);
		cmsTopicW.setContent(content);
		cmsTopicW.setState(IbmStateEnum.OPEN.name());
		cmsTopicW.setCreateUser(this.findCurrentSysUserName());
		cmsTopicW.setCreateTime(nowTime);
		cmsTopicW.setCreateTimeLong(nowTime.getTime());
		cmsTopicW.setUpdateUser(this.findCurrentSysUserName());
		cmsTopicW.setUpdateTime(nowTime);
		cmsTopicW.setUpdateTimeLong(nowTime.getTime());

		//发送消息
		CmsTopicWService cmsTopicWService = new CmsTopicWService();
		String id = cmsTopicWService.save(cmsTopicW);
		//保存发送记录
		ibmCmsTopicTService.save(userGroup, id);
			
		return CommViewEnum.Default.toString();
	}

}
