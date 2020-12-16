package c.x.all.simple.nio.byte_buffer.core;
import java.nio.ByteBuffer;
/**
 * 全部为数字
 * 
 * 
 */
public class NioByteBuffer_int {
	/**
	 * 读取
	 * 
	 * @param bytes
	 * @return
	 */
	public static int arrayByte2int(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		// 回绕缓冲区
		// buffer.flip();
		// 这个方法用来将缓冲区准备为数据传出状态,
		// 执行以上方法后,输出通道会从数据的开头而不是末尾开始.
		// 回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
		bb.flip();
		return bb.getInt();
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.putInt(1234512345);
		return buf.array();
	}
}
