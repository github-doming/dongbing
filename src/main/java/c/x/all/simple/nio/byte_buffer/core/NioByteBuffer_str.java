package c.x.all.simple.nio.byte_buffer.core;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
/**
 * 
 * 全部为字符串
 * 
 * 
 */
public class NioByteBuffer_str {
	/**
	 * byte[]=>char[]
	 * 
	 * @param bytes
	 * @return
	 */
	public static char[] arrayByte2arrayChar(byte[] bytes) {
		// 将字节转为字符(解码)
		Charset cs = Charset.forName("UTF-8");
		// 长度
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		// 回绕缓冲区
		// buffer.flip();
		// 这个方法用来将缓冲区准备为数据传出状态,
		// 执行以上方法后,输出通道会从数据的开头而不是末尾开始.
		// 回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}
	/**
	 * char[]=>string
	 * 
	 * @param data
	 * @return
	 */
	public static String array_char2string(char[] data) {
		return String.valueOf(data);
	}
	/**
	 * 
	 * 读取
	 * 
	 * byte[]=>char[]=>string
	 * 
	 * @param data
	 * @return
	 */
	public static String array_byte2string(byte[] data) {
		return array_char2string(arrayByte2arrayChar(data));
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.put("abcde".getBytes());
		buf.put("12345".getBytes());
		return buf.array();
	}
}
