package com.ibs.plan.common.core.action;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommBaseAction;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
/**
 * 客户端 MVC基类
 *
 * @Author: Dongming
 * @Date: 2020-05-19 10:15
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseClientAction extends CommBaseAction {
	JsonResultBeanPlus bean = new JsonResultBeanPlus();
	JSONObject jsonData;

	/**
	 * 下一个action
	 * @return 运行结果
	 * @throws Exception 运行错误
	 */
	protected abstract Object execute() throws Exception ;

	@Override public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		 jsonData = JSON.parseObject(json);
		String time = jsonData.remove("time").toString();
		String valiDate = jsonData.remove("valiDate").toString();
		if (StringTool.isEmpty(time, valiDate)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		if (System.currentTimeMillis() - NumberTool.getLong(time) > 10 * 1000L) {
			bean.putEnum(CodeEnum.IBS_403_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if (Md5Tool.verify(time, valiDate)) {
			return execute();
		}
		bean.putEnum(CodeEnum.IBS_403_ERROR);
		bean.putSysEnum(CodeEnum.CODE_403);
		return bean;
	}
}
