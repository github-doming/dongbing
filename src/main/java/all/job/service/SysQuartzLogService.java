package all.job.service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
public class SysQuartzLogService extends SysQuartzLogTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void clearPhysical() throws Exception {
		String sql = "delete from sys_quartz_log";
		dao.execute(sql, null);

		}
}
