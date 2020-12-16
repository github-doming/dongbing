package c.a.util.core.upload;
import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * 创建一个进度监听器
 * 
 * @ClassName: UploadTools
 * @Description:
 * @author
 * @date 2016年1月6日 下午5:42:16
 * 
 */
public class UploadUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	// 保存路径
	private static final String FILE_PATH = "/upload/file";
	// 临时路径
	private static final String TEMP_PATH = "/upload/temp";
	// 文件路径,临时目录
	private String allTempPath;
	// 文件路径,最终目录
	private String allFilePath;
	// 文件路径,最终目录
	private String yourFileDirectory;
	// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录(默认可以不用设置)
	private String yourTempDirectory = "d://delete";
	// 设置最多只允许在内存中存储的数据,单位:字节
	// public static int yourMaxMemorySize = 1024 * 1024;
	private int yourMaxMemorySize = 1;
	// 设置允许用户上传文件大小,单位:字节
	private int yourMaxRequestSize = 100 * 1024 * 1024;
	public UploadUtil() {
		super();
	}
	/**
	 * 对外开放
	 * 
	 * 上传文件并得到多个File
	 * 
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map findFile_URLDecoder(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap map = null;
		UploadBean uploadBean = null;
		File newFile = null;
		// 判断表单是否是Multipart类型的。这里可以直接对request进行判断
		// 确保请求实际上是一个文件上传请求很简单，File Upload的提供了一个静态方法使它非常简单。
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			map = new HashMap();
			// 为基于硬盘文件的项目集创建一个工厂
			// 创建文件处理工厂，它用于生成 FileItem 对象。
			// FileItemFactory factory = new DiskFileItemFactory();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置文件的缓存路径
			yourTempDirectory = servletContext.getRealPath(this.TEMP_PATH);
			log.trace("yourTempDirectory =" + yourTempDirectory);
			// 设置工厂的约束条件
			// 设置最多只允许在内存中存储的数据,单位:字节
			factory.setSizeThreshold(yourMaxMemorySize);
			java.io.File file_yourTempDirectory = new java.io.File(
					yourTempDirectory);
			if (!file_yourTempDirectory.exists()) {
				file_yourTempDirectory.mkdirs();
			}
			// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录(默认可以不用设置)
			log.trace("factory.getSizeThreshold()="
					+ factory.getSizeThreshold());
			factory.setRepository(file_yourTempDirectory);
			// 创建一个新的文件上传处理器
			ServletFileUpload servletFileUpload = new ServletFileUpload(
					factory);
			// 设置可请求在最大可上传数据量
			// 设置允许用户上传文件大小,单位:字节
			servletFileUpload.setSizeMax(yourMaxRequestSize);
			// 创建一个进度监听器
			ProgressListener progressListener = new ProgressListener() {
				private long megaBytes = -1;
				public void update(long pBytesRead, long pContentLength,
						int pItems) {
					long mBytesLong= pBytesRead / 1000000;
					// long mBytesLong = pBytesRead / 10;
					if (megaBytes == mBytesLong) {
						return;
					}
					megaBytes = mBytesLong;
					log.trace("We are currently reading item " + pItems);
					if (pContentLength == -1) {
						log.trace("So far, " + pBytesRead
								+ " bytes have been read.");
					} else {
						log.trace("So far, " + pBytesRead + " of "
								+ pContentLength + " bytes have been read.");
					}
				}
			};
			servletFileUpload.setProgressListener(progressListener);
			// 解析请求
			// 上传文件,并解析出所有的表单字段，包括普通字段和文件字段
			List items = servletFileUpload.parseRequest(request);
			if (true) {
				// 下面对每个字段进行处理，分普通字段和文件字段
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					// 如果是普通字段
					if (fileItem.isFormField()) {
						// 是普通的字段
						uploadBean = new UploadBean();
						if (false) {
							log.trace(fileItem.getFieldName()
									+ "   "
									+ fileItem.getName()
									+ "   "
									+ new String(fileItem.getString().getBytes(
											"iso8859-1"), "utf-8"));
							fileItem.getFieldName();// 得到字段name属性的值
							fileItem.getName();// 得到file字段的文件名全路径名，如果不是file字段，为null
							fileItem.getString();// 得到该字段的值,默认的编码格式
							fileItem.getString("UTF-8");// 指定编码格式
						}
						log.trace("普通字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("普通字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("普通字段,得到该字段的值 1=" + fileItem.getString());
						log.trace("普通字段,得到该字段的值 2="
								+ fileItem.getString("UTF-8"));
						String s3 = new String(fileItem.getString().getBytes(
								"iso8859-1"), "utf-8");
						log.trace("普通字段,得到该字段的值 3=" + s3);
						String s5 = URLDecoder.decode(fileItem.getString(),
								"UTF-8");
						log.trace("普通字段,得到该字段的值 5=" + s5);
						uploadBean.setFieldName(fileItem.getFieldName());
						uploadBean.setValue(s5);
						map.put(uploadBean.getFieldName(), uploadBean.getValue());
					} else {
						// 文件字段
						uploadBean = new UploadBean();
						if (false) {
							log.trace(fileItem.getFieldName() + "   "
									+ fileItem.getName() + "   " + // 得到file字段的文件名全路径名
									fileItem.isInMemory() + "    " + // 用来判断FileItem类对象封装的主体内容是存储在内存中，还是存储在临时文件中，如果存储在内存中则返回true，否则返回false
									fileItem.getContentType() + "   " + // 文件类型
									fileItem.getSize()); // 文件大小
						}
						log.trace("文件字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("文件字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("文件字段,内存=" + fileItem.isInMemory());
						log.trace("文件字段,文件大小=" + fileItem.getSize());
						// 什么东西都有了 ，想怎么处理都可以了
						// 保存文件，其实就是把缓存里的数据写到目标路径下
						if (fileItem.getName() != null
								&& fileItem.getSize() != 0) {
							File fullFile = new File(fileItem.getName());
							// 设置文件的目标路径
							yourFileDirectory = servletContext
									.getRealPath(this.FILE_PATH);
							this.allTempPath = this.yourTempDirectory + "\\"
									+ fullFile.getName();
							uploadBean.setTempPath(allTempPath);
							this.allFilePath = yourFileDirectory + "\\"
									+ fullFile.getName();
							uploadBean.setFilePath(allFilePath);
							newFile = new File(allFilePath);
							fileItem.write(newFile);
							uploadBean.setFieldName(fileItem.getFieldName());
							uploadBean.setName(fileItem.getName());
							uploadBean.setInMemory(fileItem.isInMemory());
							uploadBean.setSize(fileItem.getSize());
							uploadBean.setFile(newFile);
							map.put(uploadBean.getFieldName(), uploadBean);
						} else {
							log.trace("文件没有选择 或 文件内容为空");
						}
					}
				}
			}
		}
		return map;
	}
	/**
	 * 上传文件并得到多个File
	 * 
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> findFile(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> map = null;
		UploadBean uploadBean = null;
		File newFile = null;
		// 判断表单是否是Multipart类型的。这里可以直接对request进行判断
		// 确保请求实际上是一个文件上传请求很简单，File Upload的提供了一个静态方法使它非常简单。
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			map = new HashMap<String,Object>();
			// 为基于硬盘文件的项目集创建一个工厂
			// 创建文件处理工厂，它用于生成 FileItem 对象。
			// FileItemFactory factory = new DiskFileItemFactory();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置文件的缓存路径
			yourTempDirectory = servletContext.getRealPath(this.TEMP_PATH);
			log.trace("yourTempDirectory =" + yourTempDirectory);
			// 设置工厂的约束条件
			// 设置最多只允许在内存中存储的数据,单位:字节
			factory.setSizeThreshold(yourMaxMemorySize);
			java.io.File file_yourTempDirectory = new java.io.File(
					yourTempDirectory);
			if (!file_yourTempDirectory.exists()) {
				file_yourTempDirectory.mkdirs();
			}
			// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录(默认可以不用设置)
			log.trace("factory.getSizeThreshold()="
					+ factory.getSizeThreshold());
			factory.setRepository(file_yourTempDirectory);
			// 创建一个新的文件上传处理器
			ServletFileUpload servletFileUpload = new ServletFileUpload(
					factory);
			// 设置可请求在最大可上传数据量
			// 设置允许用户上传文件大小,单位:字节
			servletFileUpload.setSizeMax(yourMaxRequestSize);
			// 创建一个进度监听器
			ProgressListener progressListener = new ProgressListener() {
				private long megaBytes = -1;
				public void update(long pBytesRead, long pContentLength,
						int pItems) {
					long mBytes = pBytesRead / 1000000;
					// long mBytes = pBytesRead / 10;
					if (megaBytes == mBytes) {
						return;
					}
					megaBytes = mBytes;
					log.trace("We are currently reading item " + pItems);
					if (pContentLength == -1) {
						log.trace("So far, " + pBytesRead
								+ " bytes have been read.");
					} else {
						log.trace("So far, " + pBytesRead + " of "
								+ pContentLength + " bytes have been read.");
					}
				}
			};
			servletFileUpload.setProgressListener(progressListener);
			// 解析请求
			// 上传文件,并解析出所有的表单字段，包括普通字段和文件字段
			List items = servletFileUpload.parseRequest(request);
			if (true) {
				// 下面对每个字段进行处理，分普通字段和文件字段
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					// 如果是普通字段
					if (fileItem.isFormField()) {
						// 是普通的字段
						uploadBean = new UploadBean();
						if (false) {
							log.trace(fileItem.getFieldName()
									+ "   "
									+ fileItem.getName()
									+ "   "
									+ new String(fileItem.getString().getBytes(
											"iso8859-1"), "utf-8"));
							fileItem.getFieldName();// 得到字段name属性的值
							fileItem.getName();// 得到file字段的文件名全路径名，如果不是file字段，为null
							fileItem.getString();// 得到该字段的值,默认的编码格式
							fileItem.getString("UTF-8");// 指定编码格式
						}
						log.trace("普通字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("普通字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("普通字段,得到该字段的值 1=" + fileItem.getString());
						log.trace("普通字段,得到该字段的值 2="
								+ fileItem.getString("UTF-8"));
						String s3 = new String(fileItem.getString().getBytes(
								"iso8859-1"), "utf-8");
						log.trace("普通字段,得到该字段的值 3=" + s3);
						String s5 = URLDecoder.decode(fileItem.getString(),
								"UTF-8");
						log.trace("普通字段,得到该字段的值 5=" + s5);
						uploadBean.setFieldName(fileItem.getFieldName());
						uploadBean.setValue(s3);
						map.put(uploadBean.getFieldName(), uploadBean.getValue());
					} else {
						// 文件字段
						uploadBean = new UploadBean();
						if (false) {
							log.trace(fileItem.getFieldName() + "   "
									+ fileItem.getName() + "   " + // 得到file字段的文件名全路径名
									fileItem.isInMemory() + "    " + // 用来判断FileItem类对象封装的主体内容是存储在内存中，还是存储在临时文件中，如果存储在内存中则返回true，否则返回false
									fileItem.getContentType() + "   " + // 文件类型
									fileItem.getSize()); // 文件大小
						}
						log.trace("文件字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("文件字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("文件字段,内存=" + fileItem.isInMemory());
						log.trace("文件字段,文件大小=" + fileItem.getSize());
						// 什么东西都有了 ，想怎么处理都可以了
						// 保存文件，其实就是把缓存里的数据写到目标路径下
						if (fileItem.getName() != null
								&& fileItem.getSize() != 0) {
							File fullFile = new File(fileItem.getName());
							// 设置文件的目标路径
							yourFileDirectory = servletContext
									.getRealPath(this.FILE_PATH);
							allTempPath = this.yourTempDirectory + "\\"
									+ fullFile.getName();
							uploadBean.setTempPath(this.allTempPath);
							allFilePath = yourFileDirectory + "\\"
									+ fullFile.getName();
							uploadBean.setFilePath(allFilePath);
							newFile = new File(allFilePath);
							fileItem.write(newFile);
							uploadBean.setFieldName(fileItem.getFieldName());
							uploadBean.setName(fileItem.getName());
							uploadBean.setInMemory(fileItem.isInMemory());
							uploadBean.setSize(fileItem.getSize());
							uploadBean.setFile(newFile);
							map.put(uploadBean.getFieldName(), uploadBean);
						} else {
							log.trace("文件没有选择 或 文件内容为空");
						}
					}
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * 上传文件并得到一个File
	 * @deprecated
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public File findFile_v1(ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		File newFile = null;
		// 判断表单是否是Multipart类型的。这里可以直接对request进行判断
		// 确保请求实际上是一个文件上传请求很简单，File Upload的提供了一个静态方法使它非常简单。
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 为基于硬盘文件的项目集创建一个工厂
			// 创建文件处理工厂，它用于生成 FileItem 对象。
			// FileItemFactory factory = new DiskFileItemFactory();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置文件的缓存路径
			yourTempDirectory = servletContext.getRealPath("/upload/temp/");
			log.trace("yourTempDirectory =" + yourTempDirectory);
			// 设置工厂的约束条件
			// 设置最多只允许在内存中存储的数据,单位:字节
			factory.setSizeThreshold(yourMaxMemorySize);
			java.io.File d = new java.io.File(yourTempDirectory);
			if (!d.exists()) {
				d.mkdirs();
			}
			// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录(默认可以不用设置)
			log.trace("factory.getSizeThreshold()="
					+ factory.getSizeThreshold());
			factory.setRepository(d);
			// 创建一个新的文件上传处理器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置可请求在最大可上传数据量
			// 设置允许用户上传文件大小,单位:字节
			upload.setSizeMax(yourMaxRequestSize);
			// 创建一个进度监听器
			ProgressListener progressListener = new ProgressListener() {
				private long megaBytes = -1;
				public void update(long pBytesRead, long pContentLength,
						int pItems) {
					long mBytes = pBytesRead / 1000000;
					// long mBytes = pBytesRead / 10;
					if (megaBytes == mBytes) {
						return;
					}
					megaBytes = mBytes;
					log.trace("We are currently reading item " + pItems);
					if (pContentLength == -1) {
						log.trace("So far, " + pBytesRead
								+ " bytes have been read.");
					} else {
						log.trace("So far, " + pBytesRead + " of "
								+ pContentLength + " bytes have been read.");
					}
				}
			};
			upload.setProgressListener(progressListener);
			// 解析请求
			// 上传文件,并解析出所有的表单字段，包括普通字段和文件字段
			List items = upload.parseRequest(request);
			if (true) {
				// 下面对每个字段进行处理，分普通字段和文件字段
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					// 如果是普通字段
					if (fileItem.isFormField()) {
						// 是普通的字段
						if (false) {
							log.trace(fileItem.getFieldName()
									+ "   "
									+ fileItem.getName()
									+ "   "
									+ new String(fileItem.getString().getBytes(
											"iso8859-1"), "utf-8"));
							fileItem.getFieldName();// 得到字段name属性的值
							fileItem.getName();// 得到file字段的文件名全路径名，如果不是file字段，为null
							fileItem.getString();// 得到该字段的值,默认的编码格式
							fileItem.getString("UTF-8");// 指定编码格式
						}
						log.trace("普通的字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("普通的字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("普通的字段,得到该字段的值=" + fileItem.getString());
					} else {// 文件字段
						if (false) {
							log.trace(fileItem.getFieldName() + "   "
									+ fileItem.getName() + "   " + // 得到file字段的文件名全路径名
									fileItem.isInMemory() + "    " + // 用来判断FileItem类对象封装的主体内容是存储在内存中，还是存储在临时文件中，如果存储在内存中则返回true，否则返回false
									fileItem.getContentType() + "   " + // 文件类型
									fileItem.getSize()); // 文件大小
						}
						log.trace("文件字段,字段name属性的值="
								+ fileItem.getFieldName());
						log.trace("文件字段,file字段的文件名全路径名="
								+ fileItem.getName());
						log.trace("文件字段,内存=" + fileItem.isInMemory());
						log.trace("文件字段,s文件大小=" + fileItem.getSize());
						// 什么东西都有了 ，想怎么处理都可以了
						// 保存文件，其实就是把缓存里的数据写到目标路径下
						if (fileItem.getName() != null
								&& fileItem.getSize() != 0) {
							File fullFile = new File(fileItem.getName());
							// 设置文件的目标路径
							String yourFileDirectory = servletContext
									.getRealPath("/upload/file/");
							newFile = new File(yourFileDirectory + "//"
									+ fullFile.getName());
							fileItem.write(newFile);
						} else {
							log.trace("文件没有选择 或 文件内容为空");
						}
					}
				}
			}
		}
		return newFile;
	}
}
