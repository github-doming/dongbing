package com.ibm.old.v1.pc.ibm_sys_suggest.t.action;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_sys_suggest.t.entity.IbmSysSuggestT;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_sys_suggest.t.service.IbmPcSysSuggestTService;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * 
 * 
 * @Description: 用户反馈信息
 * @ClassName: IbmSysSuggestSaveAction
 * @date 2019年1月23日 下午4:06:14 
 * @author wck
 * @Email: 810160078@qq.com
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class IbmSysSuggestSaveAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if(!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		if(appUserT == null){
			jrb.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return returnJson(jrb);
		}
		String suggest = dataMap.getOrDefault("SUGGEST_CONTENT_","").toString();
		if(StringTool.isEmpty(suggest)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		Date nowTime = new Date();
		try {
			IbmSysSuggestT sysSuggestT = new IbmSysSuggestT();
			sysSuggestT.setSuggestContent(suggest);
			sysSuggestT.setCreateUser(appUserT.getAppUserName());
			sysSuggestT.setCreateTime(nowTime);
			sysSuggestT.setCreateTimeLong(nowTime.getTime());
			sysSuggestT.setHandleState(IbmStateEnum.UNFINISH.name());
			//保存反馈信息
			IbmPcSysSuggestTService service = new IbmPcSysSuggestTService();
			service.save(sysSuggestT);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"反馈失败",e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
