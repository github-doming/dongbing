package c.a.util.core.jdbc.mysql;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
/**
 * 
 * 
 * Page控制器
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class PageMysqlBean<T> extends PageCoreBean<T> {
	/**
	 * 构造函数
	 * 
	 * @param pageSize
	 */
	public PageMysqlBean(Integer pageSize) {
		super(pageSize);
	}
	/**
	 * 构造函数
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public PageMysqlBean(Integer pageIndex, int pageSize) {
		super(pageIndex, pageSize);
	}
	
	/**
	 * 找出First
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public void executeFindFirst(Integer pageIndex, Integer pageSize) {
		if (pageIndex < 1) {
			// return;
			pageIndex = 1;
		}
		if (pageSize < 1) {
			// return;
			pageSize = Integer.MAX_VALUE;
			this.isFindAll = true;
		}
		if (this.isFindAll) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
			this.pageSize = pageSize;
			this.start = ((pageIndex - 1) * pageSize);
			this.end = ((pageIndex - 1) * pageSize) + pageSize;
		}
	}
	
}
