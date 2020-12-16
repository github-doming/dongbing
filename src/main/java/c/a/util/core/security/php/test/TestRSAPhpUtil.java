package c.a.util.core.security.php.test;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.ProjectConfig;
import c.a.util.core.security.php.Base64PhpUtil;
import c.a.util.core.security.php.RSAPhpConfig;
import c.a.util.core.security.php.RSAPhpUtil;
public class TestRSAPhpUtil {
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(ProjectConfig.class);
		RSAPhpUtil rsaPhpUtil = new RSAPhpUtil();
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			PrivateKey privateKey = RSAPhpUtil.loadPrivateKey(RSAPhpConfig.PRIVATE_KEY);
			PublicKey publicKey = rsaPhpUtil.loadPublicKey(RSAPhpConfig.PUBLIC_KEY);
			//javax.crypto.IllegalBlockSizeException: Data must not be longer than 117 bytes
			//String accName = "1998年世界杯回到世界杯之父雷米特的祖国法国，而之前连续两届世界杯被挡在决赛圈门外的法国队凭借九十年代以来卓有成效的青训体系，培养出了包括齐达内、德约卡夫、图拉姆、巴特斯在内的一大批世界级球星，成为国际足坛举足轻重的新兴力量。另一方面，上届冠军巴西队，在94年之后人才辈出，其中尤以在巴塞罗那和国际米兰连拿两届世界足球先生的罗纳尔多最引人注目，此外，荷兰队在三剑客退役之后以博格坎普和阿贾克斯95黄金一代为首的足坛巨星，基本完成了国家队的新老交替，神奇教练希丁克即将迈出他不可思议的世界杯执教生涯的第一步；德国队虽然两年前在英格兰获得欧洲杯冠军，但令人遗憾的是，1990年那群为联邦德国队第三次夺得世界杯的老臣们依然大部分留队，被认为是贝肯鲍尔之后德国最优秀的自由人萨默尔因重伤告别世界杯，为球队前景蒙上了阴影；意大利队人才济济，巴乔宝刀未老、皮耶罗、因扎吉风头正劲，但遗憾的是，他们的统帅老马尔蒂尼却是一位著名的食古不化者；阿根廷队在少帅帕萨雷拉上任之后清洗了大批异己，其中包括留发不留队的雷东多和卡尼吉亚，神奇教练米卢火线接管尼日利亚，在曼联出人头地的小贝却被霍德尔按在替补席上，独立后的克罗地亚继欧洲杯之后再次出现在世界杯舞台上，1998年世界杯的大幕，在1998年6月10日徐徐拉开。 [1] ";
			String accName = "你今天吃了吗";
			byte[] encryptByteArray = RSAPhpUtil.encryptData(accName.getBytes(), publicKey);
			//log.trace("加密encryptByteArray=" + new String(encryptByteArray, "utf-8"));
			String dataEncode = Base64PhpUtil.encode(encryptByteArray);
			log.trace("加密data=" + dataEncode);
			byte[] dataDecode = Base64PhpUtil.decode(dataEncode);
			//log.trace("解密encryptByteArray=" + new String(dataDecode, "utf-8"));
			byte[] plainByteArray = RSAPhpUtil.decryptData(dataDecode, privateKey);
			String plain = new String(plainByteArray, "utf-8");
			log.trace("plain=" + plain);
			byte[] plainByteArray2 = RSAPhpUtil.decryptData(encryptByteArray, privateKey);
			String plain2 = new String(plainByteArray2, "utf-8");
			log.trace("plain2=" + plain2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String main2(String dataEncode) throws Exception {
		Logger log = LogManager.getLogger(ProjectConfig.class);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PrivateKey privateKey = RSAPhpUtil.loadPrivateKey(RSAPhpConfig.PRIVATE_KEY);
		log.trace("加密data=" + dataEncode);
		byte[] dataDecode = Base64PhpUtil.decode(dataEncode);
		byte[] plainByteArray = RSAPhpUtil.decryptData(dataDecode, privateKey);
		String plain = new String(plainByteArray, "utf-8");
		return plain;
	}
}
