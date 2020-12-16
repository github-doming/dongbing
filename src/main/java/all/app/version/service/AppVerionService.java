package all.app.version.service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_config.t.entity.AppConfigT;
import all.gen.app_config.t.service.AppConfigTService;
public class AppVerionService extends AppConfigTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	public AppConfigT findVerson() throws Exception {
		String sql = "SELECT * FROM app_config  where state_!='DEL' order by version_ desc";
		AppConfigT entity = (AppConfigT )this.dao.findObject(AppConfigT.class, sql, null);
		return entity;
	}
}
