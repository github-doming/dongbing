package c.a.util.core.excel.poi;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c.a.config.SysConfig;
/**
 * 
 * response.setHeader
 * 
 * 
 * 
 */
public class ExcelPoiHttpUtil extends ExcelPoiUtil {
	/**
	 * 导出excel文件
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printForExcel(String fileName, List<Map<String, Object>> listMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		fileName = findFileName(fileName, request);
		// 初始化编码方式和输出流
		response.setCharacterEncoding(SysConfig.utf8);
		// response.setContentType("application/vnd.ms-excel");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		// attachment --- 作为附件下载
		//
		// inline --- 在线打开
		//
		// response.setHeader("Content-Disposition", "attachment;filename= "
		// + strDate + ".xls");
		response.setHeader("Content-Disposition", "inline;filename= " + fileName + ".xls");
		ServletOutputStream outputStream = response.getOutputStream();
		this.doWriteFile(outputStream, listMap);
	}
}
