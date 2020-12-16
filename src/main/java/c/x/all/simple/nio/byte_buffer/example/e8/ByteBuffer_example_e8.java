package c.x.all.simple.nio.byte_buffer.example.e8;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 21位
 * 
 * 
 */
public class ByteBuffer_example_e8 {
	/**
	 * 读取
	 * 
	 * @param bytes
	 * @return
	 */
	public static List<Byte> arrayByte2int(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		// 回绕缓冲区
		// buffer.flip();
		// 这个方法用来将缓冲区准备为数据传出状态,
		// 执行以上方法后,输出通道会从数据的开头而不是末尾开始.
		// 回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
		bb.flip();
		List<Byte> list = new ArrayList<Byte>();
		for (int i = 1; i <= bytes.length; i++) {
			if (i == 3) {
				System.out.println((char) bytes[i]);
			}
		}
		return list;
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(21);
		int i = 0x00000031;
		buf.putInt(i);
		buf.put("abcdeabcdeabcdea".getBytes());
		byte v = 0x30;
		// byte v = 0x20;
		buf.put(v);
		return buf.array();
	}
	public static void main(String[] args) {
		List b = ByteBuffer_example_e8.arrayByte2int(ByteBuffer_example_e8
				.write());
		System.out.println(b);
		// 将字节转为字符(解码)
	}
}
