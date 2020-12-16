package c.x.platform.root.tag.permission;
import java.util.List;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.x.platform.root.tag.core.BaseBodyTagSupport;
/**
 * 权限标签
 * 
 * 
 */
public class TagPermission extends BaseBodyTagSupport {
	private String id;
	private String name;
	private String code;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String toHtml_bodyEmptyNot(String strBody) throws Exception {
		TransactionBase transactionBase = new TransactionBase();
		IJdbcTool jdbcTool = null;
		String returnStr = "";
		try {
			jdbcTool = transactionBase.findJdbcToolLocal();
			transactionBase.transactionStart(jdbcTool);
			// 执行业务
			List<String> codeList = this.findPermissionCodeList();
			if (codeList != null) {
				for (String permission_code : codeList) {
					if (code.equals(permission_code)) {
						returnStr = strBody;
						break;
					}
				}
			}
			transactionBase.transactionEnd(jdbcTool);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
			transactionBase.transactionRoll(jdbcTool);
			// 重新抛出异常
			throw e;
		} finally {
			transactionBase.transactionClose(jdbcTool);
		}
	}
	public String toHtml_bodyEmpty() throws Exception {
		return "";
	}
}
