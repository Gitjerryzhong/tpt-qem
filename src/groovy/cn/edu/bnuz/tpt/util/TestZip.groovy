package cn.edu.bnuz.tpt.util

import java.io.BufferedInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.util.zip.CRC32;  
import java.util.zip.CheckedOutputStream;  

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
/**
 * @ClassName: TestZip
 * @CreateTime Apr 28, 2013 1:12:16 PM
 * @author :  参考CardReissueOrderExportService
 * @Description: 压缩文件的通用工具类-采用java.util.zip.ZipOutputStream实现，较复杂。
 *
 */
class TestZip {
    private File zipFile;  
    private String includeFile;
	private List users
	private baseDir
    /**
     * 压缩文件构造函数
     * @param pathName 压缩的文件存放目录
     */
    public TestZip(String pathName) {
        zipFile = new File(pathName);  
		
    } 
	def setIncludePrefix(String prefix){
		this.includeFile = prefix
	} 
	def setUsers(List users){
		this.users=users
	}
  
    /**
     * 执行压缩操作
     * @param srcPathName 被压缩的文件/文件夹
     */
    public void compressExe(String srcPathName) { 
		baseDir = srcPathName.substring(srcPathName.lastIndexOf("/")+1)
//		System.out.println baseDir
        File file = new File(srcPathName);  
        if (!file.exists()){
        	System.out.println("["+this.class.name+"]"+srcPathName + "Not exists!");  
        }
        try {  
//			System.out.println(srcPathName + "zip");
			 FileOutputStream fileOutputStream = new FileOutputStream(zipFile);    
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,new CRC32());    
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
            compressByType(file, out, basedir);  
            out.close()  
        } catch (Exception e) { 
        	e.printStackTrace();
        	System.out.println("["+this.class.name+"]"+e);
            throw new RuntimeException(e);  
        }  
    }  
	/**
	 * 执行压缩操作
	 * @param srcPathName 被压缩的文件/文件夹
	 */
	byte[] compressExeToWeb(String srcPathName) {
		
		baseDir = srcPathName.substring(srcPathName.lastIndexOf("/")+1)
		File file = new File(srcPathName);
		if (!file.exists()){
			System.out.println("["+this.class.name+"]"+srcPathName + "Not exists!");
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream()
			ZipOutputStream cos = new ZipOutputStream(baos)
//			 FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
//			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compressByType(file, out, basedir);
			cos.finish()
			return baos.toByteArray()
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("["+this.class.name+"]"+e);
			throw new RuntimeException(e);
		}
	}
    /**
     * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
     * @param file 
     * @param out
     * @param basedir
     */
    private void compressByType(File file, ZipOutputStream out, String basedir) {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {   
			if(checkDir(file))       	
            	this.compressDirectory(file, out, basedir);  
        } else {          	
            this.compressFile(file, out, basedir);  
        }  
    }  
  
    /**
     * 压缩一个目录
     * @param dir
     * @param out
     * @param basedir
     */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {  
		if (!dir.exists()){			
        	 return;  
        }
           
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
        	compressByType(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  
  
    /**
     * 压缩一个文件
     * @param file
     * @param out
     * @param basedir
     */
    private void compressFile(File file, ZipOutputStream out, String basedir) {  
        if (!file.exists() ) {  
            return;  
        }  
		if(includeFile && !file.name.substring(0, 6).equals(includeFile)){
			return;
		}
        	out.putNextEntry(new ZipEntry(basedir +file.name))
			file.withInputStream { input -> out << input }
			out.closeEntry()         
          
    }  
	
	/**
	 * 检查目录是否属于有效用户
	 */
	private boolean checkDir(File file){
		if(baseDir.equals(file.name)) return true
		for(String user:users){
			if(user.equals(file.name))
			return true			
		}
		return false
	}
}
