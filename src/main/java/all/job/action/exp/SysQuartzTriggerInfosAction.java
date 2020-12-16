package all.job.action.exp;

import c.a.util.job.QuartzTriggerBean;
import c.a.util.job.QuartzUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时器触发器信息
 *
 * @Author: Dongming
 * @Date: 2020-05-15 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzTriggerInfosAction extends QuartzAction {

	@Override
	public String run() throws Exception {
		QuartzUtil quartzUtil = QuartzUtil.findInstance();
		List<QuartzTriggerBean> quartzTriggerList = quartzUtil.findTriggerList();

		boolean started = QuartzUtil.findInstance().doSchedulerIsStart();
		Map<String,Object> data = new HashMap<>(2);
		data.put("list",quartzTriggerList);
		data.put("started",started);
		jrb.setData(data);
		jrb.setSuccess(true);
		return this.returnJson(jrb);
	}

}
