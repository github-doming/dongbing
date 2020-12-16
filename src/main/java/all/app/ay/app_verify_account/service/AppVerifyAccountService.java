package all.app.ay.app_verify_account.service;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
public class AppVerifyAccountService extends AppVerifyAccountTService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 查找Code
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public String findCodeBySessionId(String sessionId) throws Exception {
		String sql = "select CODE_ from app_verify_account where SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(sessionId);
		return this.dao.findString("CODE_", sql, parameterList);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	public AppVerifyAccountT findObjectBySessionId(String sessionId) throws Exception {
		String sql = "select * from app_verify_account where SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(sessionId);
		return (AppVerifyAccountT) this.dao.findObjectUnique(AppVerifyAccountT.class, sql, parameterList);
	}
}
