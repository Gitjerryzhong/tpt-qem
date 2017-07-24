package cn.edu.bnuz.tpt

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row

import grails.transaction.Transactional
import cn.edu.bnuz.tms.export.ExcelUtils

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class TptReportService {
	def messageSource
    Workbook exportReport(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		def entries =report.items.groupBy{it.status}.entrySet().sort{it.key}

		def sheet0
		entries.eachWithIndex { entry, i ->
			if(i == 0) { // 设置第1页的名称
				sheet0 = workbook.getSheetAt(0)
				workbook.setSheetName(0, getMessage( entry.key))
			} else { // 复制第1页到其它页
				def sheet = workbook.createSheet(getMessage( entry.key))
				ExcelUtils.copySheets(sheet, sheet0)
				ExcelUtils.copySheetSettings(sheet, sheet0)
			}
		}
		def style =createStyle(workbook)
		entries.eachWithIndex { entry, i ->
			
			def sheet = workbook.getSheetAt(i)			
			def startIndex = 1
			
			entry.value.eachWithIndex { item, index ->
				Row row = sheet.createRow(startIndex + index)

				[
					index + 1,
					item.userId,					
					item.userName,
					item.contact,
					item.phoneNumber, 
					item.email,
					item.collegeName,
					item.foreignMajor,
					item.bn,
					item.dateCreate.format("yyyy-MM-dd")
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)					
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
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
	def getMessage(int status){
		return messageSource.getMessage("tpt.status.${status}", null, Locale.CHINA)
		
	}

	Workbook exportMtlRgn(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		def entries =report.items.groupBy{it.type}.entrySet().sort{it.key}

//		def sheet0
//		entries.eachWithIndex { entry, i ->
//			if(i == 0) { // 设置第1页的名称
//				sheet0 = workbook.getSheetAt(0)
//				workbook.setSheetName(0, getMessage("paperType", entry.key))
//			} else { // 复制第1页到其它页
//				def sheet = workbook.createSheet(getMessage("paperType", entry.key))
//				ExcelUtils.copySheets(sheet, sheet0)
//				ExcelUtils.copySheetSettings(sheet, sheet0)
//			}
//		}
		def style =createStyle(workbook)
		entries.eachWithIndex { entry, i ->
			
			def sheet = workbook.getSheetAt(entry.key.toInteger()-1)			
			def startIndex = 1
			
			entry.value.eachWithIndex { item, index ->
				Row row = sheet.createRow(startIndex + index)

				[
					index + 1,
					item.userId,					
					item.userName,
					item.email,
					item.phoneNumber,
					item.major,
					item.collegeName,
					item.foreignMajor,
					item.masterCollege,
					item.paperName_en,
					item.paperName,
					item.master_result					
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)					
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
			}
		}
		return workbook
	}
	
	Workbook exportPaperAudit(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		def entries =report.groupBy{it.status}.entrySet().sort{it.key}

		def sheet0
		entries.eachWithIndex { entry, i ->
			if(i == 0) { // 设置第1页的名称
				sheet0 = workbook.getSheetAt(0)
				workbook.setSheetName(0, getMessage( entry.key))
			} else { // 复制第1页到其它页
				def sheet = workbook.createSheet(getMessage( entry.key))
				ExcelUtils.copySheets(sheet, sheet0)
				ExcelUtils.copySheetSettings(sheet, sheet0)
			}
		}
		def style =createStyle(workbook)
		entries.eachWithIndex { entry, i ->
			
			def sheet = workbook.getSheetAt(i)			
			def startIndex = 1
			
			entry.value.eachWithIndex { item, index ->
				Row row = sheet.createRow(startIndex + index)

				[
					index + 1,
					item.userId,					
					item.userName,
					item.mentorName,
					item.mentorEmail, 
					item.auditContent
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)					
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
			}
		}
		return workbook
	}
	byte[] exportPhotos(List users,String baseDir,String pre){
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		users.each { item ->
			def base=baseDir+"/"+item
//			println dir
//			def file = getFile(dir,pre)
			File dir = new File(base)
			if(dir?.exists()){
				File[] files = dir.listFiles();
				def username=""
				for (File file:files) {
					if(file.name.indexOf("photo")!=-1 && file.name.indexOf("bak_")){
						def names=file.name.split("_")
						username=names[1]
					}
				}
				for (File file:files) {					
					if("*".equals(pre) || file.name.indexOf(pre)==0){
						zipFile.putNextEntry(new ZipEntry(item+username+"/"+file.name))
						file.withInputStream { input -> zipFile << input }
						zipFile.closeEntry()
					}
				}
			}
			 else {
				println "picture ${item} not exists."
			}
		}
		zipFile.finish()

		return baos.toByteArray()
	}
	private File getFile(String baseDir,String pre){
		File dir = new File(baseDir)		
		if(dir.exists()){
			File[] files = dir.listFiles();
			for (File file:files) {
//				println file.name
				if(file.name.indexOf(pre)!=-1){
					println file.name
					return file
				}
			}
		}
		return null
	}
	def download(String baseDir){
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		addEntry(baseDir,zipFile)
		zipFile.finish()
		return baos.toByteArray()
	}
	private addEntry(String base,ZipOutputStream zipFile){
		File dir = new File(base)
		if(dir?.exists()){
			File[] files = dir.listFiles();
			for (File file:files) {
				if(file.isFile() && file.name.indexOf("bak_")==-1){
					zipFile.putNextEntry(new ZipEntry(file.name))
					file.withInputStream { input -> zipFile << input }
					zipFile.closeEntry()
				}else if(file.isDirectory()){ //如果申报书子目录
					for (File f:file.listFiles()) {
						if(f.isFile() && f.name.indexOf("bak_")==-1){
							zipFile.putNextEntry(new ZipEntry(file.name+"/"+f.name))
							f.withInputStream { input -> zipFile << input }
							zipFile.closeEntry()
						}
					}
				}
			}
		
		}
	}
}
