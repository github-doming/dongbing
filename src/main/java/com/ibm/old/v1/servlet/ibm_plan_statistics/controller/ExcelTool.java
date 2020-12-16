package com.ibm.old.v1.servlet.ibm_plan_statistics.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-07-27 17:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ExcelTool {

	public static void writeStatisticsResult(String fileName, JSONObject result) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			if (!file.createNewFile()) {
				throw new IOException("创建文件失败，文件路径：" + fileName);
			}
		}
		try (OutputStream outputStream = new FileOutputStream(file); HSSFWorkbook workbook = new HSSFWorkbook()) {

			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			HSSFSheet sheet;

			sheet = workbook.createSheet("统计结果");
			sheet.createFreezePane(0, 1);
			addGeneralTitleCell(sheet, style);

			JSONArray general = result.getJSONArray("general");
			addGeneralCell(general, sheet, style);
			autoSizeColumn(sheet);

			sheet = workbook.createSheet("统计明细");
			sheet.createFreezePane(4, 2);
			//写入表头
			JSONArray groupTitle = result.getJSONArray("groupTitle");
			addItemTitleCell(groupTitle, sheet, style);

			JSONArray detail = result.getJSONArray("detail");
			addDetailCell(detail, groupTitle, sheet, style);
			autoSizeColumn(sheet);
			workbook.write(outputStream);
		}
	}
	/**
	 * 添加合计表头
	 *
	 * @param sheet 工作簿
	 * @param style 样式
	 */
	private static void addGeneralTitleCell(HSSFSheet sheet, HSSFCellStyle style) {
		HSSFRow row = sheet.createRow(0);

		createCell(row, style, 0, "日期");
		createCell(row, style, 1, "投注次数");
		createCell(row, style, 2, "赢");
		createCell(row, style, 3, "亏");
		createCell(row, style, 4, "总投注金额");
		createCell(row, style, 5, "输赢金额");
		createCell(row, style, 6, "最高盈利");
		createCell(row, style, 7, "最低亏损");
		createCell(row, style, 8, "某期最大下注");
		createCell(row, style, 9, "某期最高盈利");
		createCell(row, style, 10, "某期最低亏损");
		createCell(row, style, 11, "最大连错");
		createCell(row, style, 12, "前五连错");
		createCell(row, style, 13, "最大连对");
		createCell(row, style, 14, "前五连对");
		createCell(row, style, 15, "炸人次数");
		createCell(row, style, 16, "炸人凶手");
	}

	/**
	 * 添加合计
	 *
	 * @param generals 合计信息
	 * @param sheet    工作簿
	 * @param style    样式
	 */
	private static void addGeneralCell(JSONArray generals, HSSFSheet sheet, HSSFCellStyle style) {
		HSSFRow row;
		for (int i = 0; i < generals.size(); i++) {
			row = sheet.createRow(i + 1);
			row.setRowStyle(style);
			JSONArray general = generals.getJSONArray(i);

			for (int j = 0; j < general.size(); j++) {
				createCell(row, style, j, general.getString(j));
			}
		}
	}

	/**
	 * 添加明细表头
	 *
	 * @param groupTitle 标题组
	 * @param sheet      工作簿
	 * @param style      样式
	 */
	private static void addItemTitleCell(JSONArray groupTitle, HSSFSheet sheet, HSSFCellStyle style) {

		HSSFRow row0 = sheet.createRow(0);
		HSSFRow row1 = sheet.createRow(1);

		createCell(row0, style, 0, "日期");
		CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
		sheet.addMergedRegion(region);

		createCell(row0, style, 1, "期数");
		region = new CellRangeAddress(0, 1, 1, 1);
		sheet.addMergedRegion(region);

		createCell(row0, style, 2, "开奖号码");
		region = new CellRangeAddress(0, 1, 2, 2);
		sheet.addMergedRegion(region);

		createCell(row0, style, 3, "冠亚和");
		region = new CellRangeAddress(0, 1, 3, 3);
		sheet.addMergedRegion(region);

		int groupSize = groupTitle.size();
		for (int i = 0; i < groupSize; i++) {
			int groupStart = i * 5 + 4;

			createCell(row0, style, groupStart, groupTitle.getString(i));
			region = new CellRangeAddress(0, 0, groupStart, groupStart + 4);
			sheet.addMergedRegion(region);

			createCell(row1, style, groupStart, "投注");
			createCell(row1, style, groupStart + 1, "金额");
			createCell(row1, style, groupStart + 2, "中奖");
			createCell(row1, style, groupStart + 3, "错误数");
			createCell(row1, style, groupStart + 4, "资金变化");
		}

		int totalStart = 4 + groupSize * 5;

		createCell(row0, style, totalStart, "合计");
		region = new CellRangeAddress(0, 0, totalStart, totalStart + 3);
		sheet.addMergedRegion(region);

		createCell(row1, style, totalStart, "当期下注金额");
		createCell(row1, style, totalStart + 1, "当期输赢");
		createCell(row1, style, totalStart + 2, "当日输赢");
		createCell(row1, style, totalStart + 3, "累计金额");
	}

	/**
	 * 添加明细信息
	 *
	 * @param details    明细内容
	 * @param groupTitle 标题组
	 * @param sheet      工作簿
	 * @param style      样式
	 */
	private static void addDetailCell(JSONArray details, JSONArray groupTitle, HSSFSheet sheet, HSSFCellStyle style) {
		HSSFRow row;
		HSSFCell cell;
		int groupSize = groupTitle.size();
		for (int i = 0; i < details.size(); i++) {
			row = sheet.createRow(i + 2);
			JSONArray detail = details.getJSONArray(i);

			//基本信息
			for (int j = 0; j < 4; j++) {
				createCell(row, style, j, detail.getString(j));
			}
			//循环组
			JSONArray groups = detail.getJSONArray(4);
			int len = groups.size();
			for (int j = 0; j < len; j++) {
				JSONObject groupItem = groups.getJSONObject(j);
				//第几组
				String title = groupItem.getString("group");
				int index = groupTitle.indexOf(title);

				JSONArray item = groupItem.getJSONArray("item");
				for (int k = 0; k < 4; k++) {

					cell = row.createCell(4 + index * 5 + k);
					cell.setCellStyle(style);
					cell.setCellValue(item.getString(k).replace("_", "\r\n"));
				}
				createCell(row, style, 8 + index * 5, item.getBigDecimal(4).toString());

			}
			//盈亏信息
			for (int j = 0; j < 3; j++) {
				cell = row.createCell(4 + 5 * groupSize + j);
				cell.setCellStyle(style);
				cell.setCellValue(detail.getString(5 + j));
			}
			createCell(row, style, 7 + 5 * groupSize, detail.getBigDecimal(8).toString());
		}

	}

	/**
	 * 创建单元格
	 *
	 * @param row    单元格所在行
	 * @param style  样式
	 * @param column 所在列
	 * @param value  单元格值
	 */
	private static void createCell(HSSFRow row, HSSFCellStyle style, int column, String value) {
		HSSFCell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	/**
	 * 自动调整列宽
	 *
	 * @param sheet 工作簿
	 */
	private static void autoSizeColumn(HSSFSheet sheet) throws UnsupportedEncodingException {
		int maxColumn = sheet.getRow(1).getPhysicalNumberOfCells();
		for (int i = 0; i < maxColumn; i++) {
			sheet.autoSizeColumn(i);
		}
		//获取当前列的宽度，然后对比本列的长度，取最大值
		for (int columnNum = 0; columnNum <= maxColumn; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row currentRow;
				//当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}

				if (currentRow.getCell(columnNum) != null) {
					Cell currentCell = currentRow.getCell(columnNum);
					int length = currentCell.toString().split("\r\n")[0].getBytes("GBK").length;
					if (columnWidth < length + 1) {
						columnWidth = length + 1;
					}
				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}
	}
}
