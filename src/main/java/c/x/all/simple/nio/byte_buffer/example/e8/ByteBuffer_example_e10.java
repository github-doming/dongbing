package c.x.all.simple.nio.byte_buffer.example.e8;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 21位字节转成ByteBean
 * 
 * 
 * 
 */
public class ByteBuffer_example_e10 {
	/**
	 * 读取
	 * 
	 * @param bytes
	 * @return
	 */
	public static ByteBean arrayByte2bean(byte[] bytes) {
		ByteBean bean = new ByteBean();
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		// 回绕缓冲区
		// buffer.flip();
		// 这个方法用来将缓冲区准备为数据传出状态,
		// 执行以上方法后,输出通道会从数据的开头而不是末尾开始.
		// 回绕保持缓冲区中的数据不变,只是准备写入而不是读取.
		bb.flip();
		List<Byte> list = new ArrayList<Byte>();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= bytes.length; i++) {
			if (i == 1) {
				// System.out.println("i=" + bb.getInt());
				bean.setStatus(bb.getInt());
			}
			// if (i == 2) {
			//
			// System.out.println("i2=" + bytes[i]);
			// }
			// if (i == 3) {
			//
			// System.out.println("i3=" + bytes[i]);
			// }
			if (i >= 4 && i <= 19) {
				// System.out.println("i4=" + (char) bytes[i]);
				// char c=(char) bytes[i];
				sb.append((char) bytes[i]);
			}
			if (i >= 20 && i <= 20) {
				// System.out.println("i20=" + bytes[i]);
				String version = Integer.toHexString(bytes[i]);
				bean.setVersion(version);
				// System.out.println(" version=" + version);
			}
		}
		bean.setAuthenticatorISMG(sb.toString());
		return bean;
	}
	/**
	 * 
	 * 写入
	 * 
	 * @return
	 */
	public static byte[] write() {
		ByteBuffer buf = ByteBuffer.allocate(21);
		int i = 256;
		buf.putInt(i);
		buf.put("abcdeabcdeabcdea".getBytes());
		byte v = 0x30;
		// byte v = 0x20;
		buf.put(v);
		return buf.array();
	}
	public static void main(String[] args) {
		ByteBean b = ByteBuffer_example_e10
				.arrayByte2bean(ByteBuffer_example_e10.write());
		System.out.println(b.getStatus());
		System.out.println(b.getAuthenticatorISMG());
		System.out.println(b.getVersion());
	}
}
