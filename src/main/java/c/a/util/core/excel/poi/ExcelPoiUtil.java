package c.a.util.core.excel.poi;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import c.a.util.core.request.RequestJsonUtil;
public class ExcelPoiUtil extends RequestJsonUtil{
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
	
		Workbook workbook = new HSSFWorkbook();
		this.doWriteFile(workbook, listMap);
		// 写入数据并关闭文件
		workbook.write(outputStream);
		workbook.close();
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
	public Workbook doWriteFile(Workbook workbook, List<Map<String, Object>> listMap) throws Exception {
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet("new sheet");
		int i = 1;
		// 第1个for
		for (Map<String, Object> map : listMap) {
			// 第2行
			Row row = sheet.createRow(i);
			int j = 0;
			if (i == 1) {
				j = 0;
				// 第2个for
				Set<String> keyList = map.keySet();
				// 表头的key
				int k = i - 1;
				Row keyRow = sheet.createRow(k);
				for (String key : keyList) {
					// keyRow.createCell(j).setCellValue(createHelper.createRichTextString(key));
					keyRow.createCell(j).setCellValue((key));
					j = j + 1;
				}
				// 表头的value
				j = 0;
				Collection<Object> valueCollection = map.values();
				for (Object value : valueCollection) {
					// row.createCell(j).setCellValue(createHelper.createRichTextString(value));
					if (value != null) {
						row.createCell(j).setCellValue(value.toString());
					} else {
						row.createCell(j).setCellValue("");
					}
					j = j + 1;
				}
			} else {
				j = 0;
				Collection<Object> valueCollection = map.values();
				for (Object value : valueCollection) {
					// row.createCell(j).setCellValue(createHelper.createRichTextString(value));
					if (value != null) {
						row.createCell(j).setCellValue(value.toString());
					} else {
						row.createCell(j).setCellValue("");
					}
					j = j + 1;
				}
			}
			i = i + 1;
		}
		return workbook;
	}
}
