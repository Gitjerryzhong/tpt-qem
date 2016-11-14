package cn.edu.bnuz.qem

import cn.edu.bnuz.qem.project.QemProject
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.qem.project.Notice
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.qem.update.UpdateTask

import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem

class ExportService {
	def messageSource
	byte[] download(def qemProject,String baseDir){
		def base
		if(qemProject instanceof UpdateTask){
			base =baseDir+"/update/"+qemProject.taskId+"/"+qemProject.commitDate.format("yyyyMMdd")
		}else{ 
			def teacherId =qemProject.teacher.id
	//		def year=getCurrentYear()
			if(qemProject instanceof QemTask )
				base =baseDir+"/task/"+teacherId+"/"+qemProject.id
			else base=baseDir+"/"+teacherId+"/"+qemProject.id
		}
//		println base
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		addEntry(base,zipFile)
		//如果是QemTask，则有可能还有立项申报时的申报材料，也需要一并下载2016-04-28
		if(qemProject instanceof QemTask &&  qemProject.projectId){
			base=baseDir+"/"+qemProject.teacher.id+"/"+qemProject.projectId
			addEntry(base,zipFile)
		}
		zipFile.finish()

		return baos.toByteArray()
	}
	def downloadGroups(def task,String baseDir){
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		task.each {item->
			def base
			if(item.taskId){
				base =baseDir+"/task/"+item.teacherId+"/"+item.taskId 
				addEntry(item.projectName+"${item.taskId}",base,zipFile)
			}
			if(item.projectId){
				base =baseDir+"/"+item.teacherId+"/"+item.projectId
				addEntry(item.projectName+"${item.projectId}",base,zipFile)
			}
		}
		zipFile.finish()
		return baos.toByteArray()
	}
	def download(String baseDir){
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		addEntry(baseDir,zipFile)
		zipFile.finish()
		return baos.toByteArray()
	}
	Workbook exportReport(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)		
		def entry =report
		def sheet0 = workbook.getSheetAt(0)		
		sheet0.getRow(1).getCell(0).setCellValue(
			messageSource.getMessage("qem.college.summary.title1", null, Locale.CHINA)+
			this.currentYear+
			messageSource.getMessage("qem.college.summary.title2", null, Locale.CHINA))
		def style =createStyle(workbook)
		def endIndex=1
		def startIndex = 3
		entry.eachWithIndex { item, index ->
			endIndex = startIndex + index
			Row row = sheet0.createRow(endIndex)
			if(item.view){
				def exps=item.view.split("###");
				item.view="";
	//			console.log(exps.length)
				for(int i=0;i<exps.length;i++)
				 {
					if(exps[i]!="")	
//					def title2=messageSource.getMessage("qem.college.summary.title3", ["${department}",	"${securityService.userName}","${teacher.email}","${teacher.officePhone}"].toArray(), Locale.CHINA)
						item.view+=messageSource.getMessage("qem.expertview", ["${i}","${exps[i]}"].toArray(), Locale.CHINA)
				 }
			}
//			计算没有评论的专家
			def experts=item.experts.split(";")
			def over=item.over.split(",")			
			experts=experts-over
			def unview=unViewExperts(experts)
			[
				index + 1,
				item.major,
				item.type,
				item.projectName,
				item.userName,
				item.currentTitle+(item.position?('/'+item.position):""),
				messageSource.getMessage("qem.level.${item.projectLevel}", null, Locale.CHINA),
				item.pass,
				item.ng,
				item.waiver,
				unview,
				item.avgScore,
				item.groupId,
				item.view
			].eachWithIndex { value, col ->
				Cell cell = row.createCell(col)
				cell.setCellStyle(style)
				cell.setCellValue(value)
			}
		}
			
		return workbook
	}
	
	
	private HSSFCellStyle createStyle(Workbook workbook) {
		// 边框样式
		HSSFCellStyle style = workbook.createCellStyle()
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN)
		style.setBorderTop(HSSFCellStyle.BORDER_THIN)
		style.setBorderRight(HSSFCellStyle.BORDER_THIN)
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN)
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER)
		return style
	}

	private String getCurrentYear(){
		def  notice= Notice.last()
		return notice?.bn
	}
	private String unViewExperts(def expertIds){
		def results = Teacher.executeQuery '''
select new map(
	GROUP_CONCAT(t.name) as expertsName	
)
from Teacher t 
where t.id in(:ids)
''',[ids:expertIds]
		return results[0]?.expertsName
	}
	private addEntry(String base,ZipOutputStream zipFile){
		File dir = new File(base)
		if(dir?.exists()){
			File[] files = dir.listFiles();
			for (File file:files) {
				if(file.isFile() && file.name.indexOf("del_")==-1){
					zipFile.putNextEntry(new ZipEntry(file.name))
					file.withInputStream { input -> zipFile << input }
					zipFile.closeEntry()
				}else if(file.isDirectory()){ //如果申报书子目录
					for (File f:file.listFiles()) {
						if(f.isFile() && f.name.indexOf("del_")==-1){
							zipFile.putNextEntry(new ZipEntry(file.name+"/"+f.name))
							f.withInputStream { input -> zipFile << input }
							zipFile.closeEntry()
						}
					}
				}
			}
		
		}
	}
	private addEntry(String entryName, String base,ZipOutputStream zipFile){
		File dir = new File(base)
		if(dir?.exists()){
			File[] files = dir.listFiles();
			for (File file:files) {
				if(file.isFile() && file.name.indexOf("del_")==-1){
					zipFile.putNextEntry(new ZipEntry(entryName+"/"+file.name))
					file.withInputStream { input -> zipFile << input }
					zipFile.closeEntry()
				}else if(file.isDirectory()){ //如果申报书子目录
					for (File f:file.listFiles()) {
						if(f.isFile() && f.name.indexOf("del_")==-1){
							zipFile.putNextEntry(new ZipEntry(entryName+"/"+file.name+"/"+f.name))
							f.withInputStream { input -> zipFile << input }
							zipFile.closeEntry()
						}
					}
				}
			}
		
		}
	}
}
