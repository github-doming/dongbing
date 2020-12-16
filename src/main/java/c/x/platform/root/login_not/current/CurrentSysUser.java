package c.x.platform.root.login_not.current;
import java.util.List;

import all.gen.sys_user.t.entity.SysUserT;
public class CurrentSysUser extends SysUserT {
	// sso
	private String sso;
	// 允许菜单menu allow list
	// 当前用户拥有的的菜单url
	private List<String> menuAllowList;
	// 当前用主户拥有的权限编码
	private List<String> permissionCodeList;
	// set与get
	// {
	public String getSso() {
		return sso;
	}
	public void setSso(String ssoInput) {
		sso = ssoInput;
	}
	public List<String> getMenuAllowList() {
		return menuAllowList;
	}
	public void setMenuAllowList(List<String> menuAllowListInput) {
		menuAllowList = menuAllowListInput;
	}
	public List<String> getPermissionCodeList() {
		return permissionCodeList;
	}
	public void setPermissionCodeList(List<String> permissionCodeListInput) {
		permissionCodeList = permissionCodeListInput;
	}
	// }
	// set与get
}
