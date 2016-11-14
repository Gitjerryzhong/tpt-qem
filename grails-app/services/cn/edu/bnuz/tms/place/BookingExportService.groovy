package cn.edu.bnuz.tms.place

import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFFont
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook

import cn.edu.bnuz.tms.export.ExcelUtils
import cn.edu.bnuz.tms.place.BookingForm;

class BookingExportService {
	def messageSource
	
	Workbook exportReport(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		def styles = [
			apply: [
				normal: createStyle(workbook, false, false),
				small: createStyle(workbook, false, true)
			],
			revoke: [
				normal: createStyle(workbook, true, false),
				small: createStyle(workbook, true, true)
			]
		]
		
		def entries = report.items.groupBy {it.openGroup}.entrySet().sort{it.key}

		def sheet0
		entries.eachWithIndex { entry, i ->
			if(i == 0) { // 设置第1页的名称
				sheet0 = workbook.getSheetAt(0)
				workbook.setSheetName(0, entry.key)
			} else { // 复制第1页到其它页
				def sheet = workbook.createSheet(entry.key)
				ExcelUtils.copySheets(sheet, sheet0)
				ExcelUtils.copySheetSettings(sheet, sheet0)
			}
		}

		entries.eachWithIndex { entry, i ->
			def items = entry.value.sort { a, b ->
				(a.startWeek <=> b.startWeek) ?: 
				(a.endWeek <=> b.endWeek) ?: 
				(a.dayOfWeek <=> b.dayOfWeek) ?: 
				(a.room <=> b.room) ?:
				(a.sectionType <=> b.sectionType)
			}

			def sheet = workbook.getSheetAt(i)
			sheet.getRow(1).getCell(1).setCellValue("$report.reportId")
			sheet.getRow(1).getCell(4).setCellValue("$entry.key")
			sheet.getRow(1).getCell(6).setCellValue(new Date().format("yyyy-MM-dd"))
			def startIndex = 3
			
			entry.value.eachWithIndex { item, index ->
				Row row = sheet.createRow(startIndex + index)

				[
					index + 1,
					item.startWeek == item.endWeek 
						? messageSource.getMessage("tms.date.week", [item.startWeek].toArray(), Locale.CHINA)
						: messageSource.getMessage("tms.date.weeks", [item.startWeek, item.endWeek].toArray(), Locale.CHINA),
					messageSource.getMessage("tms.date.dayOfWeek.${item.dayOfWeek}", null, Locale.CHINA),
					messageSource.getMessage("tms.booking.section.${item.sectionType}", null, Locale.CHINA),
					item.room,
					item.department,
					item.userName + " " + item.userPhone + " " + item.checkerPhone + "\n" + item.reason, 
					item.formId,
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)
					
					def style, temp
					if(item.status == BookingForm.STATUS_REVOKED) {
						temp = styles.revoke
					} else {
						temp = styles.apply
					}
					
					if(col == 6 ) {
						style = temp.small
					} else {
						style = temp.normal
					}
					
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
			}
		}
		return workbook
	}
	
	private HSSFCellStyle createStyle(Workbook workbook, boolean revoked, boolean small) {
		// 边框样式
		HSSFCellStyle style = workbook.createCellStyle()
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN)
		style.setBorderTop(HSSFCellStyle.BORDER_THIN)
		style.setBorderRight(HSSFCellStyle.BORDER_THIN)
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN)
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER)

		
		if(revoked) {
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index)
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
		}
		
		HSSFFont f = workbook.createFont()
		if(small) {
			f.setFontHeightInPoints((short)10)
		} else {
			f.setFontHeightInPoints((short)12)
		}
		f.setFontName("SimSun")
		style.setFont(f)
		return style
	}
	
	private exportItems(File templatePath, report, info) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		workbook.setSheetName(0, info)
		def style = createStyle(workbook)
		def sheet = workbook.getSheetAt(0)
		def startIndex = 1
		report.eachWithIndex { item, index ->
			Row row = sheet.createRow(startIndex + index)

			[
				item.formId,
				item.term,
				
				item.startWeek == item.endWeek
					? messageSource.getMessage("tms.date.week", [item.startWeek].toArray(), Locale.CHINA)
					: messageSource.getMessage("tms.date.weeks", [item.startWeek, item.endWeek].toArray(), Locale.CHINA),
				messageSource.getMessage("tms.date.dayOfWeek.${item.dayOfWeek}", null, Locale.CHINA),
				messageSource.getMessage("tms.booking.section.${item.sectionType}", null, Locale.CHINA),
				item.room,
				messageSource.getMessage("tms.booking.status.${item.status}", null, Locale.CHINA),
				item.type,
				item.department,
				item.userName,
				item.dateCreated.format("yyyy-MM-dd HH:mm"),
				item.reason
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
}
