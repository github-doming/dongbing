package c.a.util.ldap;

public class LdapUser {

	// 用户账户
	private String account;
	private String code;
	private String password;
	// 名称是唯一的
	private String name;
	private String displayName;

	private String objectCategory;
	private String tel;
	private String mail;
	private String cn;

	private String userPrincipalName;

	private String distinguishedName;
	private String usnCreated;
	private String usnChanged;
	private String whenChanged;
	private String whenCreated;

	private String sn;
	private String description;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getObjectCategory() {
		return objectCategory;
	}
	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getUserPrincipalName() {
		return userPrincipalName;
	}
	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}
	public String getDistinguishedName() {
		return distinguishedName;
	}
	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
	public String getWhenChanged() {
		return whenChanged;
	}
	public void setWhenChanged(String whenChanged) {
		this.whenChanged = whenChanged;
	}
	public String getWhenCreated() {
		return whenCreated;
	}
	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUsnCreated() {
		return usnCreated;
	}
	public void setUsnCreated(String usnCreated) {
		this.usnCreated = usnCreated;
	}
	public String getUsnChanged() {
		return usnChanged;
	}
	public void setUsnChanged(String usnChanged) {
		this.usnChanged = usnChanged;
	}

}
