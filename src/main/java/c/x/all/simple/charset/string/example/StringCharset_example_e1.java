package c.x.all.simple.charset.string.example;
import java.io.UnsupportedEncodingException;
import c.x.all.simple.charset.string.StringCharset;
public class StringCharset_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StringCharset cStringCharset = new StringCharset();
			String str = "This is a 中文的 String!";
			System.out.println("str: " + str);
			String gbk = cStringCharset.toGBK(str);
			System.out.println("转换成GBK码: " + gbk);
			System.out.println();
			String ascii = cStringCharset.toASCII(str);
			System.out.println("转换成US-ASCII码: " + ascii);
			gbk = cStringCharset.changeCharset(ascii, StringCharset.US_ASCII,
					StringCharset.GBK);
			System.out.println("再把ASCII码的字符串转换成GBK码: " + gbk);
			System.out.println();
			String iso88591 = cStringCharset.toISO_8859_1(str);
			System.out.println("转换成ISO-8859-1码: " + iso88591);
			gbk = cStringCharset.changeCharset(iso88591,
					StringCharset.ISO_8859_1, StringCharset.GBK);
			System.out.println("再把ISO-8859-1码的字符串转换成GBK码: " + gbk);
			System.out.println();
			String utf8 = cStringCharset.toUTF_8(str);
			System.out.println("转换成UTF-8码: " + utf8);
			gbk = cStringCharset.changeCharset(utf8, StringCharset.UTF_8,
					StringCharset.GBK);
			System.out.println("再把UTF-8码的字符串转换成GBK码: " + gbk);
			System.out.println();
			String utf16be = cStringCharset.toUTF_16BE(str);
			System.out.println("转换成UTF-16BE码:" + utf16be);
			gbk = cStringCharset.changeCharset(utf16be, StringCharset.UTF_16BE,
					StringCharset.GBK);
			System.out.println("再把UTF-16BE码的字符串转换成GBK码: " + gbk);
			System.out.println();
			String utf16le = cStringCharset.toUTF_16LE(str);
			System.out.println("转换成UTF-16LE码:" + utf16le);
			gbk = cStringCharset.changeCharset(utf16le, StringCharset.UTF_16LE,
					StringCharset.GBK);
			System.out.println("再把UTF-16LE码的字符串转换成GBK码: " + gbk);
			System.out.println();
			String utf16 = cStringCharset.toUTF_16(str);
			System.out.println("转换成UTF-16码:" + utf16);
			gbk = cStringCharset.changeCharset(utf16, StringCharset.UTF_16LE,
					StringCharset.GBK);
			System.out.println("再把UTF-16码的字符串转换成GBK码: " + gbk);
			String s = new String("中文".getBytes("UTF-8"), "UTF-8");
			System.out.println(s);
			System.out.println("==============================");
			str = "中国";
			System.out.println("编码=" + cStringCharset.findEncoding_v1(str));
			System.out.println("编码=" + cStringCharset.findEncoding_v2(str));
			s = new String(str.getBytes("UTF-8"), "UTF-8");
			System.out.println(s);
			s = new String(str.getBytes("GBK"), "GBK");
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
