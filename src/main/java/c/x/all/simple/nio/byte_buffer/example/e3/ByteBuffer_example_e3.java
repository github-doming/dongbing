package c.x.all.simple.nio.byte_buffer.example.e3;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
/**
 * 
 * 版本
 * 
 * 
 * 16进制
 * 
 * 
 * 
 * 
 */
public class ByteBuffer_example_e3 {
	/**
	 * 读取
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte arrayByte2int(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		// 回绕缓冲区
		// buffer.flip();
		// 这个方法用来将缓冲区准备为数据传出状态,
		// 执行以上方法后,输出通道会从数据的开头而不是末尾开始.
		// 回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
		bb.flip();
		return bb.get();
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(15);
		// byte v = 0x30;
		byte v = 0x20;
		buf.put(v);
		return buf.array();
	}
	public static void main(String[] args) {
		byte b = ByteBuffer_example_e3.arrayByte2int(ByteBuffer_example_e3
				.write());
		System.out.println(b);
		// 将字节转为字符(解码)
		System.out.println((int) b);
		// 16进制
		String j2 = Integer.toHexString((int) b);
		System.out.println(j2);
	}
}
