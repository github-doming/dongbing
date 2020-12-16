package c.x.all.simple.nio.byte_buffer.example.e5;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
/**
 * 数字1
 * 
 * 
 */
public class ByteBuffer_example_e6 {
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
		int b = ByteBuffer_example_e6.arrayByte2int(ByteBuffer_example_e6
				.write());
		System.out.println(b);
	}
}
