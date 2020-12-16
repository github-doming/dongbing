package c.a.util.ldap;
import java.util.List;
public class LdapConfig {

	// LDAP标记
	public static String LDAP = "LDAP";

	// 过滤器
	public String filter = null;
	// 属性key
	private String attributeKeyList = null;
	// 账户
	private String account = null;
	// 密码
	private String password = null;
	// 基本DN
	private String dnRoot = null;
	// 地址
	private List<String> urlList = null;
	// 基准
	private String dnDatum = null;
	// 用户编号属性
	protected String attUserNo;
	// 用户账户属性
	protected String attUserAcc;
	// 用户名称属性
	protected String attUserName;
	// 用户密码属性
	protected String attUserPwd;
	// 用户电话属性
	protected String attUserTel;
	// 用户邮件属性
	protected String attUserMail;
	// 用户描述属性
	protected String attUserDescription;
	// 部门名称属性
	protected String attDeptName;
	// 部门描述属性
	protected String attDeptDescription;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDnRoot() {
		return dnRoot;
	}
	public void setDnRoot(String dnRoot) {
		this.dnRoot = dnRoot;
	}
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	public String getDnDatum() {
		return dnDatum;
	}
	public void setDnDatum(String dnDatum) {
		this.dnDatum = dnDatum;
	}
	public String getAttributeKeyList() {
		return attributeKeyList;
	}
	public void setAttributeKeyList(String attributeKeyList) {
		this.attributeKeyList = attributeKeyList;
	}
	public String getAttUserNo() {
		return attUserNo;
	}
	public void setAttUserNo(String attUserNo) {
		this.attUserNo = attUserNo;
	}
	public String getAttUserAcc() {
		return attUserAcc;
	}
	public void setAttUserAcc(String attUserAcc) {
		this.attUserAcc = attUserAcc;
	}
	public String getAttUserName() {
		return attUserName;
	}
	public void setAttUserName(String attUserName) {
		this.attUserName = attUserName;
	}
	public String getAttUserPwd() {
		return attUserPwd;
	}
	public void setAttUserPwd(String attUserPwd) {
		this.attUserPwd = attUserPwd;
	}
	public String getAttUserTel() {
		return attUserTel;
	}
	public void setAttUserTel(String attUserTel) {
		this.attUserTel = attUserTel;
	}
	public String getAttUserMail() {
		return attUserMail;
	}
	public void setAttUserMail(String attUserMail) {
		this.attUserMail = attUserMail;
	}
	public String getAttDeptName() {
		return attDeptName;
	}
	public void setAttDeptName(String attDeptName) {
		this.attDeptName = attDeptName;
	}
	public String getAttUserDescription() {
		return attUserDescription;
	}
	public void setAttUserDescription(String attUserDescription) {
		this.attUserDescription = attUserDescription;
	}
	public String getAttDeptDescription() {
		return attDeptDescription;
	}
	public void setAttDeptDescription(String attDeptDescription) {
		this.attDeptDescription = attDeptDescription;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}

}
