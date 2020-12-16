package c.a.util.core.excel.jxl;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c.a.config.SysConfig;
import jxl.write.WritableWorkbook;
/**
 * response.setHeader
 * 
 * 
 * 
 */
public class ExcelJxlhttpUtil extends ExcelJxlUtil {
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
		// 初始化编码方式和输出流
		response.setCharacterEncoding(SysConfig.utf8);
		// response.setContentType("application/vnd.ms-excel");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		// attachment --- 作为附件下载
		//
		// inline --- 在线打开
		//
		response.setHeader("Content-Disposition", "attachment;filename= " + fileName + ".xls");
		if (false) {
			response.setHeader("Content-Disposition", "inline;filename= " + fileName + "" + ".xls");
		}
		ServletOutputStream outputStream = response.getOutputStream();
		this.doWriteFile(outputStream, listMap);
	}
	/**
	 * @deprecated
	 * @Title: printForExcel
	 * @Description:
	 *
	 * 				参数说明
	 * @param fileName
	 * @param wwb
	 * @param request
	 * @param response
	 *            返回类型 void
	 */
	public void printForExcel(String fileName, WritableWorkbook wwb, HttpServletRequest request,
			HttpServletResponse response) {
		// 初始化编码方式和输出流
		response.setCharacterEncoding(SysConfig.utf8);
		// response.setContentType("application/vnd.ms-excel");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		// attachment --- 作为附件下载
		//
		// inline --- 在线打开
		//
		response.setHeader("Content-Disposition", "attachment;filename= " + fileName + ".xls");
		if (false) {
			response.setHeader("Content-Disposition", "inline;filename= " + fileName + "" + ".xls");
		}
		// 创建文件
		// OutputStream os = response.getOutputStream();
		// 写入数据并关闭文件
		// wwb.write();
		// wwb.close();
		// os=null;
		// os.close();
	}
}
