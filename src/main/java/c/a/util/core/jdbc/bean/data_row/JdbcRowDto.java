package c.a.util.core.jdbc.bean.data_row;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 一行记录; list和map都是数据; list有序; map可以查询;
 * 
 * @Description:
 * @ClassName: SqlRowAyBean
 * @date 2017年2月17日 下午2:38:07
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class JdbcRowDto {
	private ArrayList<String> list;
	private HashMap<String, Object> map;
	public ArrayList<String> getList() {
		return list;
	}
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
}
