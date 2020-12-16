package org.doming.example.idear.test;
import org.doming.core.tools.FileTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-11-12 10:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FormatFile {

	public static List<String> format(String fileName) {
		List<String> list = FileTool.readFileByLines(fileName);
		List<String> array = new ArrayList<>();

		//追加头信息
		array.add("@charset \"utf-8\";");
		array.add("//iPhone 6尺寸作为设计稿基准");
		array.add("$vm_base: 750;");
		array.add("@function vw($px) {");
		array.add("  @return ($px / 750) * 100vw;");
		array.add("}");
		array.add("");


		StringBuilder sb;
		String[] strs;
		String a;
		double b;
		for (String str : list) {
			if (StringTool.isContains("rem", str)) {
				sb = new StringBuilder(" ");
				strs = str.split(" ");
				for (String s : strs) {
					if (StringTool.isContains("rem", s)) {
						if (StringTool.isContains("rem;", s)) {
							a = s.replace("rem;", "");
							b = Double.parseDouble(a);
							sb.append("  vw(").append(b * 100).append(");");
						} else {
							a = s.replace("rem", "");
							b = Double.parseDouble(a);
							sb.append("  vw(").append(b * 100).append(")");
						}
					} else {
						sb.append(" ").append(s);
					}
				}
				array.add(sb.toString());
			} else {
				array.add(str);
			}
		}
		return array;
	}


	private static void out(String outFileName, List<String> array) {



	}
	public static void main(String[] args) {

		String fileName = "F:\\工作\\html5\\common\\css\\mine_data\\head_portrait.css";
		List<String> array = format(fileName);

		String outFileName =fileName.replace(".css",".scss");
		FileTool.writerFileByLines(outFileName,array);
		System.out.println("end===");
	}




}
