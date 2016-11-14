package cn.edu.bnuz.tpt.util

import org.apache.tools.ant.Project
import org.apache.tools.ant.taskdefs.Zip
import org.apache.tools.ant.types.FileSet
/**
 * @ClassName: ZipCompressor
 * @CreateTime Apr 28, 2013 1:12:16 PM
 * @author : 
 * @Description: 压缩文件的通用工具类-采用org.apache.tools.ant.Project实现，较简单，但由于需要额外加载Ant.jar，现已不再使用。
 *
 */
class ZipCompressor {
	static final int BUFFER = 8192;
	private File zipFile;

	/**
	 * 压缩文件构造函数
	 * @param pathName 压缩的文件存放目录
	 */
	public ZipCompressor(String pathName) {
		zipFile = new File(pathName);
	}

	/**
	 * 执行压缩操作
	 * @param srcPathName 需要被压缩的文件/文件夹
	 */
	public void compressExe(String srcPathName,String includes) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists()){
			throw new RuntimeException(srcPathName + "不存在！");
		}

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
//        fileSet.setIncludes("**/photo_*.jpg"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
		//fileSet.setExcludes(...); //排除哪些文件或文件夹
		fileSet.setIncludes(includes);
		zip.addFileset(fileSet);
		zip.execute();
	}
}
