package c.x.all.simple.nio.channels.file.example.e3_read_write;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import c.a.tools.log.custom.common.BaseLog;
/**
 * 比较
 * 
 * c.a.tools.file.txt.custom.example.e6_read_write
 * 
 * 
 * c.x.b.feng.crud.root.io.file.example.e1_read_write
 * 
 * c.x.all.simple.nio.channels.file.example.e3_read_write
 * 
 * 
 * 读取并写入
 * 
 * 转成FileChannel
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class FileChannel_example_e3 {
	public static void main(String[] args) {
		// 时间计算
		Calendar calendar = Calendar.getInstance();
		long start = calendar.getTimeInMillis();
		// 执行业务
		try {
			if (false) {
				String filePath_in = "U://删除//虚拟机//vmware_南天//VMware-workstation-full-8.0.2-591240.exe";// 花费时间t=21768
			}
			if (false) {
				String filePath_in = "U://删除//DTLite4356-0091.zip";// 花费时间t=274
			}
			if (false) {
				String filePath_in = "U://cxy//cjx_software//安装软件//win安装软件//qq//QQ2013SP3.exe_backup";// 花费时间t=3729
			}
			if (false) {
				String filePath_in = "U://删除//EditPlus_v3.0.358H.rar";// ]花费时间t=172
			}
			if (false) {
				String filePath_in = "U://删除//DTLite4356-0091.zip";//
			}
			if (false) {
				String filePath_in = "U://删除//a.txt";// 花费时间t=148
			}
			if (false) {
				String filePath_in = "x://io//a.txt";// 花费时间t=151
			}
			if (false) {
				String filePath_out = "x://io//b.txt";
			}
			String filePath_in = "x://io//a.txt";
			String filePath_out = "x://io//b.txt";
			FileInputStream cFileInputStream = new FileInputStream(filePath_in);
			FileChannel cFileChannel_in = cFileInputStream.getChannel();
			ByteBuffer cByteBuffer = ByteBuffer.allocate(1024 * 1024 * 50);
			// ByteBuffer cByteBuffer = ByteBuffer.allocate(1024 * 1024 * 1);
			// ByteBuffer cByteBuffer = ByteBuffer.allocate( 1);
			FileOutputStream cFileOutputStream = new FileOutputStream(
					filePath_out);
			FileChannel cFileChannel_out = cFileOutputStream.getChannel();
			while (true) {
				cByteBuffer.clear();
				int flag = cFileChannel_in.read(cByteBuffer);
				if (flag == -1) {
					break;
				}
				long size$FileChannel_out = cFileChannel_out.size();
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
