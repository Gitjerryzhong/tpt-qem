package cn.edu.bnuz.tpt.util

import org.apache.tools.ant.Project
import org.apache.tools.ant.taskdefs.Zip
import org.apache.tools.ant.types.FileSet
/**
 * @ClassName: ZipCompressor
 * @CreateTime Apr 28, 2013 1:12:16 PM
 * @author : 
 * @Description: ѹ���ļ���ͨ�ù�����-����org.apache.tools.ant.Projectʵ�֣��ϼ򵥣���������Ҫ�������Ant.jar�����Ѳ���ʹ�á�
 *
 */
class ZipCompressor {
	static final int BUFFER = 8192;
	private File zipFile;

	/**
	 * ѹ���ļ����캯��
	 * @param pathName ѹ�����ļ����Ŀ¼
	 */
	public ZipCompressor(String pathName) {
		zipFile = new File(pathName);
	}

	/**
	 * ִ��ѹ������
	 * @param srcPathName ��Ҫ��ѹ�����ļ�/�ļ���
	 */
	public void compressExe(String srcPathName,String includes) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists()){
			throw new RuntimeException(srcPathName + "�����ڣ�");
		}

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
//        fileSet.setIncludes("**/photo_*.jpg"); //������Щ�ļ����ļ��� eg:zip.setIncludes("*.java");
		//fileSet.setExcludes(...); //�ų���Щ�ļ����ļ���
		fileSet.setIncludes(includes);
		zip.addFileset(fileSet);
		zip.execute();
	}
}
