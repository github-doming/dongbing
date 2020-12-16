package c.x.all.simple.nio.byte_buffer.example.e1;
import c.x.all.simple.nio.byte_buffer.core.NioByteBuffer_str;
/**
 * 
 * 全部为字符串
 * 
 * 
 */
public class ByteBuffer_example_str {
	public static void main(String[] args) {
		// byte[]=>char[]=>string
		String str = NioByteBuffer_str.array_char2string(NioByteBuffer_str
				.arrayByte2arrayChar(NioByteBuffer_str.write()));
		System.out.println(str);
		// byte[]=>char[]=>string
		str = NioByteBuffer_str.array_byte2string(NioByteBuffer_str.write());
		System.out.println(str);
	}
}
