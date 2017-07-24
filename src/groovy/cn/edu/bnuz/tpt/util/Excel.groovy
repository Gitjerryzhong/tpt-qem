package cn.edu.bnuz.tpt.util

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
import java.io.File

class Excel {
	def title
	Workbook exportReport(File templatePath, report,def type) {
		// ��ģ��
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		def entry =report
		def style =createStyle(workbook)
		def entries =report
		def sheet0
		// ���õ�1ҳ������
		sheet0 = workbook.getSheetAt(0)
		sheet0.getRow(0).getCell(0).setCellValue(title)
		def endIndex=1
		def startIndex = 2
		entry.eachWithIndex { item, index ->
			endIndex = startIndex + index
			Row row = sheet0.createRow(endIndex)
			[
				index + 1,
				item.projectName,
				item.coTypeName,
				item.beginYear,
				(type==2)?item.effeYearStr:"",
				item.effective,
				item.majorName,
				item.departmentName,
				item.collegeNameCn,
				item.collegeNameEn,
				item.coProTypeName,
				item.coDegreeOrMajor,
				item.memo
			].eachWithIndex { value, col ->
				Cell cell = row.createCell(col)
				cell.setCellStyle(style)
				cell.setCellValue(value)
			}
		}
		return workbook
	}
	
	private HSSFCellStyle createStyle(Workbook workbook) {
		// �߿���ʽ
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
		// �߿���ʽ
		HSSFCellStyle style = workbook.createCellStyle()
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER)
		return style
	}
	private HSSFCellStyle createStaticStyle2(Workbook workbook) {
		// �߿���ʽ
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