package c.x.all.simple.nio.channels.file.example.e5;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import c.x.all.simple.nio.byte_buffer.core.NioByteBuffer_str;
import c.a.tools.log.custom.common.BaseLog;
/**
 * 
 * 
 * 读取并写入
 * 
 * 转成FileChannel
 * 
 * 
 * 一行一行的解析
 * 
 * 
 * 
 * 
 * 
 */
public class FileChannel_example_e5 {
	public static void main(String[] args) {
		// 时间计算
		Calendar calendar = Calendar.getInstance();
		long start = calendar.getTimeInMillis();
		// 执行业务
		try {
			String filePath_in = "x://io//a.txt";// 花费时间t=151
			String filePath_out = "x://io//b.txt";
			FileInputStream cFileInputStream = new FileInputStream(filePath_in);
			FileChannel cFileChannel_in = cFileInputStream.getChannel();
			// ByteBuffer cByteBuffer = ByteBuffer.allocate(1024 * 1024 * 50);
			// ByteBuffer cByteBuffer = ByteBuffer.allocate(1024 * 1024 * 1);
			// ByteBuffer cByteBuffer = ByteBuffer.allocate( 1);
			ByteBuffer cByteBuffer = ByteBuffer.allocate(1024);
			FileOutputStream cFileOutputStream = new FileOutputStream(
					filePath_out);
			FileChannel cFileChannel_out = cFileOutputStream.getChannel();
			while (true) {
				cByteBuffer.clear();
				int flag = cFileChannel_in.read(cByteBuffer);
				if (flag == -1) {
					break;
				}
				String str_all = NioByteBuffer_str
						.array_char2string(NioByteBuffer_str
								.arrayByte2arrayChar(cByteBuffer.array()));
				System.out.println("str_all=" + str_all);
				String arrayStr[] = str_all.split("\r\n");
				for (String s : arrayStr) {
					// System.out.println("s="+s);
					System.out.println("s=" + s.trim());
				}
				// 输出
				long size$FileChannel_out = cFileChannel_out.size();
				// 反转
				cByteBuffer.flip();
				int size_expect = cByteBuffer.remaining();
				// System.out.println("size_expect="+size_expect);
				cFileChannel_out.write(cByteBuffer, size$FileChannel_out);
				if (cByteBuffer.hasRemaining()) {
					System.out.println("cByteBuffer.remaining()="
							+ cByteBuffer.remaining());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 时间计算
		calendar = Calendar.getInstance();
		long end = calendar.getTimeInMillis();
		long t = end - start;
		BaseLog.trace("花费时间t=" + t);
		System.out.println("end");
	}
}
