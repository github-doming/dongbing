package c.x.all.simple.nio.byte_buffer.example.e5;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
/**
 * 数字1与16进制
 * 
 * 
 */
public class ByteBuffer_example_e7 {
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
		int c = (int) bb.get(3);
		System.out.println(c);
		return c;
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(4);
		int b = 1;
		buf.putInt(b);
		return buf.array();
	}
	public static void main(String[] args) {
		int b = ByteBuffer_example_e7.arrayByte2int(ByteBuffer_example_e7
				.write());
		System.out.println(b);
		// 16进制
		String j1 = Integer.toHexString(b);
		System.out.println(j1);
		String j2 = Integer.toHexString(32);
		System.out.println(j2);
	}
}
