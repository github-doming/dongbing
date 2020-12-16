package c.x.platform.root.compo.tree_table.parameter;
/**
 * 
 * 
 * 跟数据库有关的参数
 * 
 * 
 */
public class TreeTableNodeColumn_Event {
	private String columnId;
	private String columnName;
	private String columnSn;
	private String columnParentId;
	private String columnUrl;
	private String columnPic;
	private String columnPicOpen;
	private String columnPicClose;
	/**
	 * 搜索path
	 */
	private String columnPath;
	/**
	 * treeCode
	 */
	private String columnTreeCode;
	
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnSn() {
		return columnSn;
	}
	public void setColumnSn(String columnSequence) {
		this.columnSn = columnSequence;
	}
	public String getColumnParentId() {
		return columnParentId;
	}
	public void setColumnParentId(String columnParentId) {
		this.columnParentId = columnParentId;
	}
	public String getColumnUrl() {
		return columnUrl;
	}
	public void setColumnUrl(String columnUrl) {
		this.columnUrl = columnUrl;
	}
	public String getColumnPic() {
		return columnPic;
	}
	public void setColumnPic(String columnPic) {
		this.columnPic = columnPic;
	}
	public String getColumnPath() {
		return columnPath;
	}
	public void setColumnPath(String columnPath) {
		this.columnPath = columnPath;
	}
	public String getColumnTreeCode() {
		return columnTreeCode;
	}
	public void setColumnTreeCode(String columnTreeCode) {
		this.columnTreeCode = columnTreeCode;
	}
	public String getColumnPicOpen() {
		return columnPicOpen;
	}
	public void setColumnPicOpen(String columnPicOpen) {
		this.columnPicOpen = columnPicOpen;
	}
	public String getColumnPicClose() {
		return columnPicClose;
	}
	public void setColumnPicClose(String columnPicClose) {
		this.columnPicClose = columnPicClose;
	}

}