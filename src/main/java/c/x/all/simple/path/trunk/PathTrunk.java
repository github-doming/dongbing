package c.x.all.simple.path.trunk;

import javax.servlet.ServletContext;

public class PathTrunk {

	/**
	 * 读取文件夹路径(相对路径)
	 * 
	 * @param pathRelative_folder_trunk
	 * @return
	 */
	public String findFolderPath_trunk(String pathRelative_folder_trunk) {

		String path = System.getProperty("user.dir");
		if (false) {
			System.out.println("类TrunkCyPath的path=" + path);
		}

		path = path + "\\" + pathRelative_folder_trunk + "\\";

		if (false) {
			System.out.println("类TrunkCyPath的path=" + path);
		}

		return path;
	}

	/**
	 * 读取单个文件路径(相对路径)
	 * 
	 * @param pathRelative_file_trunk
	 * @return
	 */
	public String findFilePath_trunk(String pathRelative_file_trunk) {

		String path = System.getProperty("user.dir");
		if (false) {
			System.out.println("类TrunkCyPath的path=" + path);
		}
		path = path + "\\" + pathRelative_file_trunk;
		if (false) {
			System.out.println("类TrunkCyPath的path=" + path);
		}
		return path;

	}
}
