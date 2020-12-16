package c.a.util.core.excel.jxl;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import c.a.util.core.request.RequestJsonUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * response.setHeader
 * 
 * 
 * 
 */
public class ExcelJxlUtil extends RequestJsonUtil{
	/**
	 *  生成文件
	 * @Title: doWriteFile 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param filePath
	 * @param listMap
	 * @throws Exception 
	 * 返回类型 void
	 */
	public void doWriteFile(String filePath, List<Map<String, Object>> listMap) throws Exception {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		this.doWriteFile(outputStream, listMap);
	}
	/**
	 * 生成文件
	 * @Title: doWriteFile 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param outputStream
	 * @param listMap
	 * @throws Exception 
	 * 返回类型 void
	 */
	public void doWriteFile(OutputStream outputStream, List<Map<String, Object>> listMap) throws Exception {
		WritableWorkbook writableWorkbook = null;
		writableWorkbook = Workbook.createWorkbook(outputStream);
		writableWorkbook = this.doWriteFile(writableWorkbook, listMap);
		// 写入数据并关闭文件
		writableWorkbook.write();
		writableWorkbook.close();
		outputStream.close();
	}
	/**
	 * List<Map>生成excel的算法
	 * 
	 * @param workbook
	 * @param listMap
	 * @return
	 * @throws Exception
	 */
	public WritableWorkbook doWriteFile(WritableWorkbook writableWorkbook, List<Map<String, Object>> listMap)
			throws Exception {
		// 生成一个名为第一页的sheet
		WritableSheet sheet = writableWorkbook.createSheet("第一页", 0);
		int i = 1;
		// 第1个for
		for (Map<String, Object> map : listMap) {
			// 第2行
			int j = 0;
			if (i == 1) {
				j = 0;
				// 第2个for
				Set<String> keyList = map.keySet();
				// 表头的key
				//int k = i - 1;
				for (String key : keyList) {
					// 第一个0表示第一列,第二个0表示第一行,字符串表示内容
					Label label = new Label(j, 0, key);
					// 添加数据到Sheet
					sheet.addCell(label);
					j = j + 1;
				}
				// 表头的value
				j = 0;
				Collection<Object> value_Collection = map.values();
				for (Object value : value_Collection) {
					// 第一个0表示第一列,第二个0表示第一行,字符串表示内容
					Label label =null;
					if (value != null) {
						 label = new Label(j, 1, value.toString());
					} else {
						 label = new Label(j, 1,"");
					}
					// 添加数据到Sheet
					sheet.addCell(label);
					j = j + 1;
				}
			} else {
				j = 0;
				Collection<Object> valueCollection = map.values();
				for (Object value : valueCollection) {
					// 第一个0表示第一列,第二个0表示第一行,字符串表示内容
					Label label =null;
					if (value != null) {
						 label = new Label(j, i, value.toString());
					} else {
						 label = new Label(j, i, "");
					}
					// 添加数据到Sheet
					sheet.addCell(label);
					j = j + 1;
				}
			}
			i = i + 1;
		}
		return writableWorkbook;
	}
}
