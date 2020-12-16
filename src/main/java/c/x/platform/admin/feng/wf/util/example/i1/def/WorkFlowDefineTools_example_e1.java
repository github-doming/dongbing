package c.x.platform.admin.feng.wf.util.example.i1.def;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.file.FileUtil;
import c.a.util.core.path.PathUtil;
import c.a.util.core.test.CommTest;

public class WorkFlowDefineTools_example_e1 extends CommTest {
	@Test
	public void execute() {
		// 时间计算
		Calendar calendar = Calendar.getInstance();
		long long_start = calendar.getTimeInMillis();
		// 执行业务

		try {

			PathUtil pcw = new PathUtil();

			String filePath_in = pcw
					.findPath("/config/co/chen/simple/business/wf/example/i1/jbpm.xml");

			log.trace("path=" + filePath_in);

			String filePath_out = "d://gen//b.txt";
			String string$file_content = "";
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb;

			log.trace("文件是否存在="
					+ fileUtil.isFileExistsByAbsoluteFilePath(filePath_in));
			sb = fileUtil.read(filePath_in);
			log.trace("content=" + sb.toString());
			string$file_content = sb.toString() + string$file_content;
			fileUtil.write(string$file_content, filePath_out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 时间计算
		calendar = Calendar.getInstance();
		long long_end = calendar.getTimeInMillis();
		long long_t = long_end - long_start;
		BaseLog.trace("花费时间t=" + long_t);
	}

}
