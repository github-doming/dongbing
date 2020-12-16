package com.cloud.common.core;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
/**
 * MVC 对外API解析类
 *
 * @Author: Dongming
 * @Date: 2020-06-18 10:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseApiAction extends BaseMvcData {
	protected JsonResultBeanPlus bean = new JsonResultBeanPlus();
	protected JSONObject jsonData;

	/**
	 * 下一个action
	 *
	 * @return 运行结果
	 * @throws Exception 运行错误
	 */
	protected abstract Object execute() throws Exception;

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
			return bean.put401Data();
		}
		if (System.currentTimeMillis() - NumberTool.getLong(time) > 10 * 1000L) {
			bean.putEnum(CodeEnum.CLOUD_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if (Md5Tool.verify(time, valiDate)) {
			return execute();
		}
		bean.putEnum(CodeEnum.CLOUD_403_DATA_ERROR);
		bean.putSysEnum(CodeEnum.CODE_403);
		return bean;
	}
}
