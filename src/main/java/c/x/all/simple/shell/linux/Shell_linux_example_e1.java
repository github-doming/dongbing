package c.x.all.simple.shell.linux;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 显示文件列表
 * 
 * 
 */
public class Shell_linux_example_e1 {
	public static void main(String args[]) {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		try {
			process = Runtime.getRuntime().exec("ls ..");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String line : processList) {
			System.out.println(line);
		}
	}
}
