package org.doming.example.file.eg01;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
/**
 * @Description: 重命名
 * @Author: Dongming
 * @Date: 2019-06-24 14:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FileReName {
	@Test public void test01() {
		File dir = new File("D:\\Sword_Art_Online_Ⅱ");
		File[] files = dir.listFiles();
		Assert.notNull(files);
		for (File file : files) {
			if (!file.isDirectory()) {
				String fileName = file.getAbsolutePath();
				fileName = fileName.replace("[CASO&SumiSora]", "").replace("[GB][720p]", "");File newFile = new File(fileName);
				if (file.renameTo(newFile)){
					System.out.println("已重命名".concat(newFile.getName()));
				}else {
					System.out.println("Error");
				}
			}

		}
	}
	@Test public void test02(){
		File dir = new File("D:\\Sword_Art_Online_Ⅱ");
		File[] files = dir.listFiles();
		Assert.notNull(files);
		for (File file : files) {
			if (!file.isDirectory()) {
				String path = file.getAbsolutePath();
				path = path.replace("[CASO&SumiSora]", "").replace("[GB][720p]", "");

				System.out.println(path);

			}
		}
	}
}
