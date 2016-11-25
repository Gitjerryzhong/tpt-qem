package cn.edu.bnuz.qem

import java.util.List;


class AttachService {
	def messageSource
    
	/***
	 * 用于获取指定项目的所有附件
	 * @param projectId
	 * @return
	 */
	def List<String> getFileNames_Qem(filePath){
		List<String> fileNames=new ArrayList<String>()
		File dir= new File(filePath)
		if(dir.isDirectory()){
			for(File file: dir.listFiles()){
				if(file.isDirectory()){ //如果是子目录					
					for(File f:file.listFiles()){
						if(f.name.indexOf("del_")==-1) fileNames.add(file.name+"___"+f.name)
					}
				}else if(file.name.indexOf("del_")==-1){
					fileNames.add(file.name)
				}
			}
		}
		return fileNames
	}
}
