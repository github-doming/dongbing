package c.a.util.core.jdbc.bean.nut;
import java.util.ArrayList;
import java.util.List;
import c.a.util.core.asserts.AssertUtil;
/**
 * 
 * 
 * Page控制器
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public abstract class PageCoreBean<T> {
	// 1;结果集
	protected List<T> list = new ArrayList<T>();
	// 2;first记录集开始位置
	protected Integer start = -1;
	// 3;limit
	protected Integer limit = -1;
	// 3;end
	protected Integer end = -1;
	// 4;总的记录数
	protected Long totalCount = -1l;
	// 5;每页记录数
	protected Integer pageSize = -1;
	// 6;第一页
	protected Integer firstPage = 1;
	// 7;前一页
	// 不能为大写的,Boolean,因为javax.el解析会出错
	protected boolean hasPreviousPage = false; // 是否有前一页
	protected Integer prePage = -1;
	// 8;当前页/选择页数
	protected Integer pageIndex = -1;
	// 9;下一页
	// 不能为大写的,Boolean,因为javax.el解析会出错
	protected boolean hasNextPage = false;; // 是否有下一页
	protected Integer nextPage = -1;
	// 10;最后一页
	// 11;总页数
	protected Long totalPages = -1l; // 总页数
	// 12;是否查所有的记录
	// 不能为大写的,Boolean,因为javax.el解析会出错
	protected boolean isFindAll = false;
	/**
	 * 构造函数
	 * 
	 * @param pageSize
	 */
	public PageCoreBean(Integer pageSize) {
		this.pageIndex = 1;
		this.pageSize = pageSize;
		// 执行
		this.executeFindFirst(this.pageIndex, this.pageSize);
	}
	/**
	 * 构造函数
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public PageCoreBean(Integer pageIndex, Integer pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		// 执行
		this.executeFindFirst(this.pageIndex, this.pageSize);
	}
	/**
	 * 找出First
	 * 
	 * @param pageIndex
	 * @param pageSize
	 */
	public abstract void executeFindFirst(Integer pageIndex, Integer pageSize);
	/**
	 * 
	 * 找出总页数
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param totalCount
	 */
	public void executeFindTotalCount(Long totalCount) {
		AssertUtil.isNull(totalCount, "不能为空");
		AssertUtil.less(totalCount, 0l, "不能小于0");
		if (this.isFindAll) {
			this.totalCount = totalCount;
			// 计算总页数
			totalPages = 1l;
			// 前一页
			this.prePage = 1;
			this.hasPreviousPage = false;
			// 后一页
			this.nextPage = 1;
			this.hasNextPage = false;
		} else {
			this.totalCount = totalCount;
			// 计算总页数
			Long count = totalCount / pageSize;
			if (totalCount % pageSize > 0) {
				count = count + 1;
			}
			totalPages = count;
			// 前一页
			this.hasPreviousPage = (pageIndex - 1 >= 1);
			if (hasPreviousPage) {
				this.prePage = pageIndex - 1;
			} else {
				this.prePage = firstPage;
			}
			// 后一页
			this.hasNextPage = (pageIndex + 1 <= this.totalPages);
			if (hasNextPage) {
				this.nextPage = pageIndex + 1;
			} else {
				this.nextPage = pageIndex;
			}
		}
	}
	public void setTotalCount(Long totalCountInput) {
		this.totalCount = totalCountInput;
		// 执行
		this.executeFindTotalCount(this.totalCount);
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> data) {
		this.list = data;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public Integer getPrePage() {
		return prePage;
	}
	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isFindAll() {
		return isFindAll;
	}
	public void setFindAll(boolean findAll) {
		this.isFindAll = findAll;
	}
	public Long getTotalCount() {
		return totalCount;
	}
}
