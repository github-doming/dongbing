package c.a.util.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class IOUtil {

	/**
	 * 
	 * 能适应tcp ip连接
	 * 
	 * inputStream转成String
	 * 
	 * 
	 * 先转byte再转String
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public String doInputStream2String(InputStream inputStream) throws IOException {
		// 将接收到的数据存到字节数组arrayByte
		int firstChar = inputStream.read();
		int available = inputStream.available();
		// BaseLog.out("总文件大小available=" + available);
		byte[] byteArray = new byte[available + 1];
		byteArray[0] = (byte) firstChar;
		inputStream.read(byteArray, 1, available);
		return this.doByteArray2String(byteArray);
	}
	/**
	 * 转换器byte2string
	 * 
	 * 编码必须为utf-8
	 * 
	 * @param byteArray
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doByteArray2String(byte[] byteArray) throws UnsupportedEncodingException {
		String str = new String(byteArray, "utf-8");
		return str;
	}
}
