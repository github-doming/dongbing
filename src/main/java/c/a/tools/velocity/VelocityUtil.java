package c.a.tools.velocity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
public class VelocityUtil {
	/**
	 * 初始化日志路径
	 * 
	 * @param pathLog
	 * @throws UnsupportedEncodingException
	 */
	public void init(String pathLog) throws UnsupportedEncodingException {
		// log.trace("pathLog=" + pathLog);
		PathUtil pathUtil =PathThreadLocal.findThreadLocal().get();
		String path = pathUtil.findPath(VelocityConfig.velocityProperties);
		VelocityEngine engine = new VelocityEngine();
		Properties properties = new Properties();
		properties
				.setProperty("runtime.log", pathLog + "\\velocity_local_2.log");
		engine.init(properties);
		Velocity.init(path);
	}
	/**
	 * 返回html;
	 * 
	 * 能适应jboss;
	 * 
	 * @param logPath
	 * @param context
	 * @param templateFileContent模板文件内容
	 * @return
	 * @throws Exception
	 */
	public String doTemplate2html(String logPath, VelocityContext context,
			String templateFileContent) throws Exception {
		this.init(logPath);
		// 读取模板,返回字符串
		StringWriter stringWriter = new StringWriter();
		Velocity.evaluate(context, stringWriter, "core_log_velocity",
				templateFileContent);
		return stringWriter.toString();
	}
	/**
	 * 
	 * @param logPath
	 * @param templateRelativePath
	 *            模板文件相对路径
	 * @param velocityContext
	 *            上下文
	 * @param finalFolderPath
	 *            生成最终文件夹路径
	 * @param file
	 *            生成最终文件路径
	 * @throws IOException
	 */
	public void doGen(String logPath, String templateRelativePath,
			VelocityContext velocityContext, String finalFolderPath, String file)
			throws IOException {
		this.init(logPath);
		// 2
		// 读取一个模板源文件;
		Template template = Velocity.getTemplate(templateRelativePath);
		if (template == null) {
			// log.trace("template ==null");
		}
		// 3
		// 生成最终文件夹;
		new File(finalFolderPath).mkdirs();
		// 5
		// 生成最终文件;
		String filePathFinal = finalFolderPath + "\\" + file;
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				filePathFinal));
		BufferedWriter writer = writer = new BufferedWriter(
				new OutputStreamWriter(fileOutputStream, "UTF-8"));
		if (template != null) {
			template.merge(velocityContext, writer);
		}
		writer.flush();
		writer.close();
	}
}
