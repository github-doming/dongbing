package c.a.util.netty.example;
import c.a.util.netty.core.TcpNettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
public class e1 {
	public static void main(String[] args) {
		try {
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			String inputStr = "a1234567890";
			byte[] byteArray = inputStr.getBytes("UTF-8");
			ByteBuf byteBuf = Unpooled.buffer();

			byteBuf.writeInt(byteArray.length);
			byteBuf.writeBytes(byteArray);

			String returnStr = nettyUtil.doByteBuf2String(byteBuf);
			System.out.println("returnStr=" + returnStr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
