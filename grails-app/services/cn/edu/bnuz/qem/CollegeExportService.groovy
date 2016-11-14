package cn.edu.bnuz.qem

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.CellRangeAddress
import cn.edu.bnuz.tms.organization.Department
import cn.edu.bnuz.tms.organization.Teacher
import cn.edu.bnuz.tms.export.ExcelUtils
import cn.edu.bnuz.tms.security.SecurityService;
import cn.edu.bnuz.tms.system.Role

class CollegeExportService {
	SecurityService securityService
	def messageSource
    Workbook exportReport(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		def entries =report.items.groupBy{it.collegeStatus}.entrySet().sort{it.key}
		def teacher=Teacher.get(securityService.userId)	
		def department =Department.get(securityService.departmentId).name
		def sheet0
		entries.eachWithIndex { entry, i ->
			if(i == 0) { // 设置第1页的名称
				sheet0 = workbook.getSheetAt(0)
				workbook.setSheetName(0, getMessage( entry.key))
//				sheet0.getRow(1).getCell(0).setCellValue(
//					messageSource.getMessage("qem.college.summary.title1", null, Locale.CHINA)+
//					this.currentYear+
//					messageSource.getMessage("qem.college.summary.title2", null, Locale.CHINA))+
//					"("+getMessage( entry.key)+")"
//				def title2=["${department}",	"${securityService.userName}","${teacher.email}","${teacher.officePhone}"].toArray()
				def title2=messageSource.getMessage("qem.college.summary.title3", ["${department}",	"${securityService.userName}","${teacher.email}","${teacher.officePhone}"].toArray(), Locale.CHINA)
				sheet0.getRow(2).getCell(0).setCellValue(title2)				
//				println title2				
			} else { // 复制第1页到其它页
				def sheet = workbook.createSheet(getMessage( entry.key))
				ExcelUtils.copySheets(sheet, sheet0)
				ExcelUtils.copySheetSettings(sheet, sheet0)				
			}
		}
		def style =createStyle(workbook)
		def endIndex=1
		entries.eachWithIndex { entry, i ->
			
			def sheet = workbook.getSheetAt(i)	
			sheet.getRow(1).getCell(0).setCellValue(
				messageSource.getMessage("qem.college.summary.title1", null, Locale.CHINA)+
				this.currentYear+
				messageSource.getMessage("qem.college.summary.title2", null, Locale.CHINA)+
				"("+getMessage( entry.key)+")")
			def startIndex = 4						
			entry.value.eachWithIndex { item, index ->
				endIndex = startIndex + index
				Row row = sheet.createRow(endIndex)

				[
					index + 1,
					item.departmentName,					
					item.qemTypeName,
					item.projectName,
					item.userName, 
					item.position+(item.currentTitle?('/'+item.currentTitle):""),
					item.phoneNum,
					item.collegeAudit,
					" "
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)					
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
			}
			
			//合并单元格	
			def count=entry.value.size()
			def staticStyle =createStaticStyle(workbook)
			def static3=messageSource.getMessage("qem.college.summary.title5", null, Locale.CHINA)
			if(count<=10)	//如果总数不超过10，页尾信息从15行开始写		
				endIndex=15
			else 
				endIndex++
				
			Row row = sheet.createRow(endIndex)
			row.setHeight((short)(35*15.625))
			Cell cell = row.createCell(0)
			cell.setCellStyle(createStaticStyle2(workbook))
			cell.setCellValue(static3)
			sheet.addMergedRegion(new CellRangeAddress(endIndex,endIndex,0,7))
			endIndex++
			def static4=messageSource.getMessage("qem.college.summary.title4", ["${count}"].toArray(), Locale.CHINA)
			row = sheet.createRow(endIndex)
			row.setHeight((short)(35*15.625))
			cell = row.createCell(0)
			cell.setCellStyle(staticStyle)
			cell.setCellValue(static4)
			sheet.addMergedRegion(new CellRangeAddress(endIndex,endIndex,0,7))
		}		
		return workbook
	}
	Workbook exportReport_T(File templatePath, report) {
		return exportReport_T_Org(templatePath,report,0)
	}
	Workbook exportReport_T_Org(File templatePath, report,def type) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)		
		def entry =report
//		def sheet0 = workbook.getSheetAt(0)	
//		workbook.setSheetName(0, messageSource.getMessage("qem.task.union", null, Locale.CHINA))
		def style =createStyle(workbook)
		def entries 
		if(!type)entries=report.groupBy{it.departmentName}.entrySet().sort{it.key}
		else entries=report.groupBy{it.type}.entrySet().sort{it.key}
		def sheet0
		// 设置第1页的名称
		sheet0 = workbook.getSheetAt(0)
		workbook.setSheetName(0, messageSource.getMessage("qem.task.union", null, Locale.CHINA))
		entries.eachWithIndex { item, i ->
			 // 复制第1页到其它页
				def sheet = workbook.createSheet( item.key)
				ExcelUtils.copySheets(sheet, sheet0)
				ExcelUtils.copySheetSettings(sheet, sheet0)
		}
		def endIndex=1
		def startIndex = 2
		entry.eachWithIndex { item, index ->
			endIndex = startIndex + index
			Row row = sheet0.createRow(endIndex)
			def memo =""
//			只有系统管理员才能导出备注
			memo = item.memo
//			如果标志有已中期，则在备注中备注“已中期”
			if(item.hasMid)	memo+=","+messageSource.getMessage("qem.status.${31}", null, Locale.CHINA)
			[
				index + 1,
				item.departmentName,
				item.sn,
				item.type,
				item.projectName,
				item.userName,
				messageSource.getMessage("qem.level.${item.projectLevel}", null, Locale.CHINA),
				item.beginYear,
				item.expectedMid,
				item.expectedEnd,
				messageSource.getMessage("qem.status.${item.status}", null, Locale.CHINA),
				(item.status==20)?item.endDate:"",
				memo,
			].eachWithIndex { value, col ->
				Cell cell = row.createCell(col)
				cell.setCellStyle(style)
				cell.setCellValue(value)
			}
		}
		if(securityService.hasRole(Role.QEM_ADMIN)){
			endIndex=1
			entries.eachWithIndex { items, i ->
				
				def sheet = workbook.getSheetAt(i+1)
				startIndex = 2
				items.value.eachWithIndex { item, index ->
					endIndex = startIndex + index
					Row row = sheet.createRow(endIndex)
					def memo =""
	//			只有系统管理员才能导出备注
					memo = item.memo
	//			如果标志有已中期，则在备注中备注“已中期”
					if(item.hasMid)	memo+=","+messageSource.getMessage("qem.status.${31}", null, Locale.CHINA)
					
					[
						index + 1,
						item.departmentName,
						item.sn,
						item.type,
						item.projectName,
						item.userName,
						messageSource.getMessage("qem.level.${item.projectLevel}", null, Locale.CHINA),
						item.beginYear,
						item.expectedMid,
						item.expectedEnd,
						messageSource.getMessage("qem.status.${item.status}", null, Locale.CHINA),
						(item.status==20)?item.endDate:"",
						memo,
					].eachWithIndex { value, col ->
						Cell cell = row.createCell(col)
						cell.setCellStyle(style)
						cell.setCellValue(value)
					}
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
	private HSSFCellStyle createStaticStyle(Workbook workbook) {
		// 边框样式
		HSSFCellStyle style = workbook.createCellStyle()	
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER)
		return style
	}
	private HSSFCellStyle createStaticStyle2(Workbook workbook) {
		// 边框样式
		HSSFCellStyle style = workbook.createCellStyle()		
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT)
		return style
	}
	def getMessage(int status){
		return messageSource.getMessage("qem.collegestatus.${status}", null, Locale.CHINA)
		
	}
	private String getCurrentYear(){
		return new Date().format("yyyy")
	}
}
