package com.ibm.follow.connector.admin.manage3.handicap.list;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.HandicapTool;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 查看盘口信息
 * @Author: Dongming
 * @Date: 2019-11-05 18:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/info", method = HttpConfig.Method.GET) public class HandicapInfoAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
//		if (!threadJrb.isSuccess()) {
//			return returnJson(threadJrb);
//		}

		// 获取盘口ID信息
		String handicapId = request.getParameter("handicapId");
		try {
			Map<String, Object> data = new HashMap<>(2);

			List<Map<String,Object>> categories = new ArrayList<>(5);
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.AGENT));
			categories.add(HandicapTool.getTypeMap(IbmTypeEnum.MEMBER));
			data.put("categories", categories);

			List<Map<String,Object>> types = new ArrayList<>(2);
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.FREE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.CHARGE));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.ADMIN));
			types.add(HandicapTool.getTypeMap(IbmTypeEnum.SYS));
			data.put("types", types);



			// 后台展示盘口信息
			if (StringTool.notEmpty(handicapId)){
				IbmHandicap handicapInfo = new IbmAdminHandicapService().findById(handicapId);
				if (ContainerTool.isEmpty(handicapInfo)) {
					bean.putEnum(IbmCodeEnum.IBM_404_DATA);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
				}
				data.put("handicapInfo", handicapInfo);
			}else{
				List<Map<String,Object>> handicaps = new ArrayList<>();
				for (HandicapUtil.Code handicapCode: HandicapUtil.codes()) {
					Map<String,Object> handicap = new HashMap<>(2);
					handicap.put("code",handicapCode.name());
					handicap.put("name",handicapCode.getName());
					handicaps.add(handicap);
				}
				data.put("handicaps", handicaps);
			}


			return new ModelAndView("/pages/com/ibm/admin/manager/handicap/info.jsp", data);
		} catch (Exception e) {
			log.error("查看盘口信息错误", e);
			return bean.error(e.getMessage());
		}
	}
}
