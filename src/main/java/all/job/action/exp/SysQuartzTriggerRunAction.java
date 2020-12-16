package all.job.action.exp;

import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.job.service.SysQuartzTriggerService;
import c.a.util.core.string.StringUtil;

/**
 * 定时器触发器信息
 *
 * @Author: Dongming
 * @Date: 2020-05-15 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzTriggerRunAction extends QuartzAction {

	@Override
	public String run() throws Exception {

		String quartzTriggerIdStr =  jsonData.getString("id");
		SysQuartzTriggerService service = new SysQuartzTriggerService();
		if (StringUtil.isNotBlank(quartzTriggerIdStr)){
			for (String quartzTriggerId : quartzTriggerIdStr.split(",")) {
				SysQuartzTriggerT entity = service.find(quartzTriggerId);
				service.run(entity);
			}
		}else {
			for (SysQuartzTriggerT quartzTriggerT: service.findObjectAll()){
				service.run(quartzTriggerT);
			}
		}
		jrb.setSuccess(true);
		return this.returnJson(jrb);
	}

}
