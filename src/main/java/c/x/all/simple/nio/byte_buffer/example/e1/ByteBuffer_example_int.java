package c.x.all.simple.nio.byte_buffer.example.e1;
import c.x.all.simple.nio.byte_buffer.core.NioByteBuffer_int;
import c.x.all.simple.nio.byte_buffer.example.e3.ByteBuffer_example_e3;
/**
 * 
 * 全部为数字
 * 
 * 
 * 
 */
public class ByteBuffer_example_int {
	public static void main(String[] args) {
		int str = NioByteBuffer_int.arrayByte2int(NioByteBuffer_int.write());
		System.out.println(str);
		str = ByteBuffer_example_e3
				.arrayByte2int(ByteBuffer_example_e3.write());
		System.out.println(str);
	}
}
