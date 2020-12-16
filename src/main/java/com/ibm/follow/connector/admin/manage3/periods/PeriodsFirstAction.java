package com.ibm.follow.connector.admin.manage3.periods;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 首页跳转页面
 * @Author: lxl
 * @Date: 2019-10-11 09:24
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/other/period") public class PeriodsFirstAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String game = request.getParameter("gameCode");
		if (StringTool.isEmpty(game)) {
			game = "PK10";
		}
		GameUtil.Code gameCode = GameUtil.value(game);
		if (gameCode==null) {
			log.error("期数管理 首页跳转页面出错");
		}
		List<Map<String, Object>> list = new ArrayList<>();

		List<Map<String, Object>> mapList = PeriodDataUtil.getInstance().getGamePauseList(gameCode);
		if (ContainerTool.notEmpty(mapList)) {
			for (Map<String, Object> map : mapList) {
				Map<String, Object> hashMap = new HashMap<>(2);
				hashMap.put("one", stampToDate((map.get("PAUSE_START_TIME_LONG_")).toString()));
				hashMap.put("two", stampToDate((map.get("PAUSE_END_TIME_LONG_")).toString()));
				hashMap.put("gameName", GameUtil.Code.valueOf((map.get("GAME_CODE_").toString())).getName());
				list.add(hashMap);
			}

		}
		Map<String, Object> hashMap = new HashMap<>(1);
		hashMap.put("notTime", list);
		return new ModelAndView("/pages/com/ibm/admin/manager/Period/Periods.jsp", hashMap);
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

}
