package c.a.util.core.excel.vnd;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c.a.config.SysConfig;
import c.a.util.core.request.RequestJsonUtil;
/**
 * response.setHeader
 * 
 * 
 */
public class ExcelVndHttp extends RequestJsonUtil {
	/**
	 * 生成文件;
	 * 
	 * 只设置表头;
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printForFile(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 重置
		// response.reset();
		fileName = findFileName(fileName, request);
		// 初始化编码方式和输出流
		response.setCharacterEncoding(SysConfig.utf8);
		// response.setContentType("application/vnd.ms-excel");
		// response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		// attachment --- 作为附件下载
		//
		// inline --- 在线打开
		//
		// response.setHeader("Content-Disposition", "attachment;filename= "
		// + strDate + ".xls");
		response.setHeader("Content-Disposition", "inline;filename= " + fileName);
	}
	/**
	 * 生成Excel文件;
	 * 
	 * 只设置表头;
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void printForExcel(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
	}
	/**
	 * 生成Excel文件
	 * 
	 * @param response
	 * @param listMap
	 * @throws Exception
	 */
	public void printForExcel(String fileName, List<Map> listMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		fileName = findFileName(fileName, request);
		// 初始化编码方式和输出流
		response.setCharacterEncoding(SysConfig.utf8);
		response.setContentType("application/vnd.ms-excel");
		// response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		// attachment --- 作为附件下载
		//
		// inline --- 在线打开
		//
		// response.setHeader("Content-Disposition", "attachment;filename= "
		// + strDate + ".xls");
		response.setHeader("Content-Disposition", "inline;filename= " + fileName + ".xls");
		this.printForExcel(response.getWriter(), listMap);
	}
	/**
	 * 生成Excel文件
	 * 
	 * @param pw
	 * @param listMap
	 * @throws Exception
	 */
	public void printForExcel(PrintWriter pw, List<Map> listMap) throws Exception {
		boolean flag = true;
		// BaseSystem.out.println("listMap.size()=" + listMap.size());
		// 第一个for
		for (Map jsonMap : listMap) {
			if (flag) {
				StringBuilder stringBuilder = new StringBuilder();
				Set<String> keyList = jsonMap.keySet();
				for (String key : keyList) {
					stringBuilder.append(key);
					stringBuilder.append("\t");
				}
				// aseSystem.out.println("key=" + sb.toString());
				pw.println(stringBuilder.toString());
				// 输出value
				StringBuilder valueStringBuilder = new StringBuilder();
				Collection<String> valueCollection = jsonMap.values();
				for (String value : valueCollection) {
					valueStringBuilder.append(value);
					valueStringBuilder.append("\t");
				}
				pw.println(valueStringBuilder.toString());
				flag = false;
			} else {
				// 输出value
				StringBuilder valueStringBuilder = new StringBuilder();
				Collection<String> valueCollection = jsonMap.values();
				for (String value : valueCollection) {
					valueStringBuilder.append(value);
					valueStringBuilder.append("\t");
				}
				pw.println(valueStringBuilder.toString());
			}
		}
	}
}
