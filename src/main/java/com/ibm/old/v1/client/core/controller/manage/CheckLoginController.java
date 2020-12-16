package com.ibm.old.v1.client.core.controller.manage;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;
/**
 * @Description: 校验登录
 * @Author: zjj
 * @Date: 2019-05-17 11:26
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class CheckLoginController implements ClientExecutor {

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String handicapCode = msgObj.getString("handicapCode");

		IbmHandicapEnum code = IbmHandicapEnum.valueOf(handicapCode);

		if (StringTool.isEmpty(msgObj.getString("memberPassword"))) {
			bean.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			bean.putEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		//解密密码
		String commLocalASEKey = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "");
		String password = CommASEUtil.decode(commLocalASEKey, msgObj.getString("memberPassword").trim());

		switch (code) {
			case WS2:
				Ws2Util ws2Util = Ws2Util.findInstance();
				bean = ws2Util.login(msgObj.getString("handicapUrl"), msgObj.getString("handicapCaptcha"),
						msgObj.getString("memberAccount"), password);
				break;
			case IDC:
				IdcUtil idcUtil = IdcUtil.findInstance();
				bean = idcUtil.login(msgObj.getString("handicapUrl"), msgObj.getString("handicapCaptcha"),
						msgObj.getString("memberAccount"), password);
				break;
			case SGWIN:
				SgWinUtil sgWinUtil=SgWinUtil.findInstance();
				bean=sgWinUtil.login(msgObj.getString("handicapUrl"), msgObj.getString("handicapCaptcha"),
						msgObj.getString("memberAccount"), password);
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putEnum(IbmCodeEnum.CODE_404);
				return bean;
		}

		return bean;
	}
}
