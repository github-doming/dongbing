package com.ibm.follow.connector.admin.manage3.handicapUser;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 盘口代理会员用户列表
 * @Author: lxl
 * @Date: 2019-10-14 11:09
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/agentUser") public class HandicapAgentUserAction extends CommAdminAction {

	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapCode = request.getParameter("handicapCode");
		//给予默认值
		if (StringTool.isEmpty(handicapCode)){
			handicapCode = HandicapUtil.Code.IDC.name();
		}
		//给予默认值
		if (StringTool.isEmpty(HandicapUtil.Code.value(handicapCode))) {
			log.error(" 查询 盘口代理会员列表  出错");
		}

		Map<String, Object> map = new HashMap<>(1);
		List<Map<String,Object>> mapList = new ArrayList<>();
		try {
			List<Map<String, Object>> maps = new IbmHaUserService().findUserAll();
			//如果不为前端传过来的盘口code，则remove
			for (Map<String,Object> map1 : maps) {
				if (map1.get("HANDICAP_CODE_").equals(handicapCode)) {
					map1.put("CREATE_TIME_LONG_",stampToDate(map1.get("CREATE_TIME_LONG_").toString()));
					mapList.add(map1);
				}
			}
			// 后台添加拥有盘口信息
			List<Map<String,Object>> codes = new ArrayList();
			for (HandicapUtil.Code rate: HandicapUtil.Code.values()) {
				Map<String,Object> handicapMap = new HashMap<>(2);
				handicapMap.put("handicapCode", rate);
				handicapMap.put("handicapName", rate.getName());
				codes.add(handicapMap);
			}
			map.put("agentUsers", mapList);
			map.put("codes", codes);
		} catch (Exception e) {
			log.info("查询盘口代理会员列表 失败");
		}
		return new ModelAndView("/pages/com/ibm/admin/manager/handicapUser/handicapAgentUser.jsp", map);
	}

	/**
	 * 数据库string转Date
	 *
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static void main(String[] args) {
		// 后台添加拥有盘口信息
		List<Map<String,Object>> codes = new ArrayList();
		for (HandicapUtil.Code rate: HandicapUtil.Code.values()) {
			Map<String,Object> handicapMap = new HashMap<>(2);
			handicapMap.put("handicapCode", rate);
			handicapMap.put("handicapName", rate.getName());
			codes.add(handicapMap);
		}
		System.out.println(codes);
	}

}
