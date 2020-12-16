package c.a.util.core.jdbc.bean.sql_custom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * sql自定义查询
 * 
 * @Description:
 * @ClassName: SqlCustomQueryBean
 * @date 2017年2月17日 下午2:51:18
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class SqlCustomBean {
	// 返回的列
	public static String TypeQuery = "Q";
	// 查询条件
	public static String TypeWhere = "W";
	public static String TypeDecimal = "DE";
	// 日期类型
	public static String TypeDate = "D";
	public static String TypeString = "S";
	public static String TypeBoolean = "B";
	public static String TypeChar = "C";
	public static String TypeByte = "BY";
	public static String TypeShort = "SH";
	public static String TypeInt = "I";
	public static String TypeLong = "L";
	public static String TypeFloat = "F";
	public static String TypeDouble = "DO";
	// in查询
	public static String TypeOperateIn = "IN";
	// BETWEEN查询
	public static String TypeOperateBetween = "BETWEEN";
	// 模糊查询
	public static String TypeOperateLike = "LI";
	// 左模糊查询
	public static String TypeOperateLikeLeft = "LL";
	// 右模糊查询
	public static String TypeOperateLikeRight = "LR";
	// 等于
	public static String TypeOperateEqual = "E";
	// 大于
	public static String TypeOperateGreater = "G";
	// 大于或等于
	public static String TypeOperateEqualGreater = "GE";
	// 小于
	public static String TypeOperateLess = "L";
	// 小于或等于
	public static String TypeOperateEqualLess = "LE";
	// 升降序
	public static String TypeOrder = "O";
	// 降序
	public static String TypeOrderDesc = "D";
	// 降序
	public static String TypeOrderAsc = "A";
	// 拼装后的sql
	private String sql = null;
	// 子查询sql(DIYSQL)
	private String sqlSub = null;
	// 表名
	private String tableName = null;
	// 参数的值(没有转换)
	private Map<String, Object> valueMap = new HashMap<String, Object>();
	// 参数的值(转换后的)
	private List<Object> valueList = new ArrayList<Object>();
	// 返回的列
	private List<String> queryFieldList = new ArrayList<String>();
	// 条件的列
	private List<String> whereFieldList = new ArrayList<String>();
	// 排序的列
	private List<String> orderFieldList = new ArrayList<String>();
	// 是否分页
	private boolean page = false;
	// 第几页
	private Integer pageIndex = 1;
	// 每页记录数
	private Integer pageSize = Integer.MAX_VALUE;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSqlSub() {
		return sqlSub;
	}
	public void setSqlSub(String sqlSub) {
		this.sqlSub = sqlSub;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public boolean isPage() {
		return page;
	}
	public void setPage(boolean page) {
		this.page = page;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Map<String, Object> getValueMap() {
		return valueMap;
	}
	public void setValueMap(Map<String, Object> valueMap) {
		this.valueMap = valueMap;
	}
	public List<Object> getValueList() {
		return valueList;
	}
	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}
	public List<String> getQueryFieldList() {
		return queryFieldList;
	}
	public void setQueryFieldList(List<String> queryFieldList) {
		this.queryFieldList = queryFieldList;
	}
	public List<String> getWhereFieldList() {
		return whereFieldList;
	}
	public void setWhereFieldList(List<String> whereFieldList) {
		this.whereFieldList = whereFieldList;
	}
	public List<String> getOrderFieldList() {
		return orderFieldList;
	}
	public void setOrderFieldList(List<String> orderFieldList) {
		this.orderFieldList = orderFieldList;
	}
	public List<Object> findValueList() {
		boolean isAddValue = false;
		for (String whereFieldStr : whereFieldList) {
			Object value = this.valueMap.get(whereFieldStr);
			// 值是否为空
			if (StringUtil.isBlank(value)) {
				continue;
			}
			String[] valueArray = value.toString().split(",");
			// 值是否为空
			if (StringUtil.isBlank(valueArray)) {
				continue;
			}

			String[] whereFieldArray = whereFieldStr.split("\\#");
			String typeWhere = whereFieldArray[0];
			if (whereFieldArray.length == 3
					&& SqlCustomBean.TypeWhere.equals(typeWhere)) {
				String whereField = whereFieldArray[1];
				String where = whereFieldArray[2];
				isAddValue = false;
				if (!isAddValue) {
					if (where.equals(TypeOperateLike)) {
						isAddValue = true;
						value = "%" + value + "%";
						this.valueList.add(value);
					}
				}
				if (!isAddValue) {
					if (where.equals(TypeOperateLikeLeft)) {
						isAddValue = true;
						value = "%" + value + "";
						this.valueList.add(value);
					}
				}
				if (!isAddValue) {
					if (where.equals(TypeOperateLikeRight)) {
						isAddValue = true;
						value = "" + value + "%";
						this.valueList.add(value);
					}
				}
				if (!isAddValue) {
					if (where.equals(TypeOperateIn)) {
						isAddValue = true;
						String[] whereValueArray = value.toString().split(",");
						for (String whereValue : whereValueArray) {
							this.valueList.add(whereValue);
						}
					}
				}
				if (!isAddValue) {
					if (where.equals(TypeOperateBetween)) {
						isAddValue = true;
						String[] whereValueArray = value.toString().split(",");

						// AssertUtil.notEquals(2, whereValueArray.length,
						// "Between参数值个数不等于2");
						if (whereValueArray.length >= 2) {
							this.valueList.add(whereValueArray[0]);
							this.valueList.add(whereValueArray[1]);
						}

					}
				}
				if (!isAddValue) {
					this.valueList.add(value);
				}
			}
		}
		return valueList;
	}
}
