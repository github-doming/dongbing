package c.a.util.ldap;

import java.util.ArrayList;
import java.util.List;

public class LdapGroup {

	public List<LdapGroup> groupList = new ArrayList<LdapGroup>();

	public List<LdapUser> userList = new ArrayList<LdapUser>();
	// 名称是唯一的
	private String name;

	private String ou;
	// 区分名称
	private String distinguishedName;
	private String objectCategory;
	private String usnCreated;
	private String usnChanged;
	private String whenCreated;
	private String whenChanged;
	private String description;

	public List<LdapGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<LdapGroup> groupList) {
		this.groupList = groupList;
	}

	public List<LdapUser> getUserList() {
		return userList;
	}

	public void setUserList(List<LdapUser> userList) {
		this.userList = userList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public String getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(String whenCreated) {
		this.whenCreated = whenCreated;
	}

	public String getWhenChanged() {
		return whenChanged;
	}

	public void setWhenChanged(String whenChanged) {
		this.whenChanged = whenChanged;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
