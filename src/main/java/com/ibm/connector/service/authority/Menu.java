package com.ibm.connector.service.authority;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * 菜单
 *
 * @Author: Dongming
 * @Date: 2020-03-30 19:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@AnnotationEntity @AnnotationTable(name = "app_menu") public class Menu implements Serializable, Comparable<Menu> {
	private String menuId;
	private String menuCode;
	private String menuName;
	private String menuUrl;
	private String permissionCode;
	private String menuPic;
	private Integer sn;
	private String state;
	Set<Menu> childMenu;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getMenuPic() {
		return menuPic;
	}

	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<Menu> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(Set<Menu> childMenu) {
		this.childMenu = childMenu;
	}

	public Menu(String menuId) {
		this.menuId = menuId;
	}

	public void attr(String menuName, String menuCode, String menuUrl, String permissionCode, String menuPic,
			Integer sn, String state) {
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.permissionCode = permissionCode;
		this.menuPic = menuPic;
		this.sn = sn;
		this.state = state;
	}

	public void addChild(Menu menu) {
		if (childMenu == null) {
			childMenu = new TreeSet<>();
		}
		childMenu.add(menu);
	}

	@Override public String toString() {
		return "Menu{" + "menuName='" + menuName + '\'' + ", childMenu=" + childMenu + '}';
	}

	@Override public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Menu menu = (Menu) o;
		return Objects.equals(menuId, menu.menuId) && Objects.equals(menuCode, menu.menuCode) && Objects
				.equals(menuName, menu.menuName) && Objects.equals(menuUrl, menu.menuUrl) && Objects
				.equals(permissionCode, menu.permissionCode) && Objects.equals(menuPic, menu.menuPic) && Objects
				.equals(sn, menu.sn);
	}

	@Override public int hashCode() {
		return Objects.hash(menuId, menuCode, menuName, sn);
	}
	@Override public int compareTo(Menu otherMenu) {
		int compare = this.sn.compareTo(otherMenu.sn);
		if (compare != 0){
			return compare;
		}
		return this.menuId.compareTo(otherMenu.menuId);
	}
}
