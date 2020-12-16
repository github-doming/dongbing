package c.a.util.core.upload;
import java.io.File;
public class UploadBean {
	// 文件路径,临时目录
	private String tempPath;
	// 文件路径,最终目录
	private String filePath;
	// 字段name属性的值
	private String fieldName;
	// 字段的值fileItem.getString()
	private String value;
	// file字段的文件名全路径名fileItem.getName()
	private String name;
	// fileItem.isInMemory()
	// 用来判断FileItem类对象封装的主体内容是存储在内存中，还是存储在临时文件中，如果存储在内存中则返回true，否则返回false
	private boolean inMemory;
	// 文件类型fileItem.getContentType()
	private String contentType;
	// 文件大小 fileItem.getSize()
	private long size;
	// 文件
	private File file;
	public String getTempPath() {
		return tempPath;
	}
	public void setTempPath(String pathTemp) {
		tempPath = pathTemp;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String pathFile) {
		filePath= pathFile;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isInMemory() {
		return inMemory;
	}
	public void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
