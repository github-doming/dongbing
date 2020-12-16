package all.job.action.exp;

import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.job.service.SysQuartzConfigService;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.string.StringUtil;

import java.util.List;

/**
 * @Author: Dongming
 * @Date: 2020-05-16 14:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SysQuartzTriggerStateAction extends QuartzAction {

	@Override
	public String run() throws Exception {


		String state = request.getParameter("state");
		if (StringUtil.isBlank(state)) {
			jrb.setCode(ReturnCodeEnum.app400Blank.toString());
			jrb.setMsg(ReturnCodeEnum.app400Blank.getMsgCn());
			jrb.setCodeSys(ReturnCodeEnum.code400.toString());
			jrb.setMsgSys(ReturnCodeEnum.code400.getMsgCn());
			jrb.setSuccess(false);
			return this.returnJson(jrb);
		}
		SysQuartzConfigService service = new SysQuartzConfigService();
		List<SysQuartzConfigT> quartzConfigs = service.findObjectAll();
		if (quartzConfigs.size() != 0) {
			if ("OPEN".equals(state)) {
				service.start(quartzConfigs.get(0));

			} else {
				service.shutdown(quartzConfigs.get(0));
			}
		}
		return this.returnJson(jrb);
	}
}
