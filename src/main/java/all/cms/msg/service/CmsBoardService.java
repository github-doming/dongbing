package all.cms.msg.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.cms_board.t.entity.CmsBoardT;
import all.gen.cms_board.t.service.CmsBoardTService;
public class CmsBoardService extends CmsBoardTService {
	protected Logger log = LogManager.getLogger(this.getClass());

	public CmsBoardT findByCode(String code) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(code);
		String sql = "SELECT * from cms_board where code_=?";
		return (CmsBoardT) this.dao.findObject(CmsBoardT.class, sql, parameterList);

	}
}
