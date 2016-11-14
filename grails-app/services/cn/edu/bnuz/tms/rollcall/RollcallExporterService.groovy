package cn.edu.bnuz.tms.rollcall

import java.text.SimpleDateFormat

import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.context.i18n.LocaleContextHolder

import cn.edu.bnuz.tms.teaching.Term


class RollcallExporterService {
	def messageSource

	private message(String key, List args = null) {
		messageSource.getMessage(key, args == null ? null : args.toArray(), LocaleContextHolder.getLocale())
	}
	
	/**
	 * 导出期末考勤表
	 * @param templatePath 模板路径
	 * @param term 学期
	 * @param statis 统计数据
	 * @return workbook
	 */
	def exportRollcallForm(File templatePath, Term term, Map statis) {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		Sheet sheet = workbook.getSheetAt(0)

		// 日期
		def dateFormat = new SimpleDateFormat('yyyy/MM/dd');
		sheet.getRow(0).getCell(27).setCellValue(dateFormat.format(new Date()))

		// 学年、学期
		def year = (int)term.id/10
		sheet.getRow(1).getCell(1).setCellValue("${year}-${year+1}")
		sheet.getRow(1).getCell(3).setCellValue(term.id % 10)

		// 课程、安排
		def arrangement = statis.arrangement
		sheet.getRow(1).getCell(6).setCellValue(arrangement.courseClassId)
		sheet.getRow(1).getCell(15).setCellValue(arrangement.courseClassName)
		sheet.getRow(2).getCell(1).setCellValue(arrangement.teacher)
		sheet.getRow(2).getCell(3).setCellValue(arrangement.department)
		def time = message("tms.date.dayOfWeek." + arrangement.dayOfWeek) + " " +
				   message("tms.arrangement.sections", [arrangement.startSection, arrangement.startSection + arrangement.totalSection - 1	]) +
			       (arrangement.oddEven == 0 ? "" : message("tms.arrangement.oddEven." + arrangement.oddEven))
		sheet.getRow(2).getCell(6).setCellValue(time)
		sheet.getRow(2).getCell(25).setCellValue(arrangement.room)

		workbook.setSheetName(0, arrangement.arrangementId)

		// 复制行
		def count = statis.students.size()
		(count - 1).times {
			def rowNum = 7 + it
			copyRow(sheet, 6, rowNum)
			Row row = sheet.getRow(rowNum)
			row.getCell(0).setCellValue(rowNum - 6)
		}

		def map = [:] // studentId -> row
		// 学生单名
		statis.students.eachWithIndex {student, index ->
			Row row = sheet.getRow(6 + index)
			row.getCell(0).setCellValue(index + 1)
			row.getCell(1).setCellValue(student.id)
			row.getCell(2).setCellValue(student.name)
			row.getCell(3).setCellValue(student.major)
			map[student.id] = row
		}

		// 考勤
		statis.rollcallItems.each {
			Row row = map[it.id]
			row.getCell(it.week + 3).setCellValue(RC_SYMBOLS[it.type])
		}

		// 请假
		statis.leaveItems.each {
			Row row = map[it.id]
			row.getCell(it.week + 3).setCellValue(LV_SYMBOLS[it.type])
		}

		return workbook
	}

	/**
	 * 导出学生考勤统计，
	 * @param templatePath 模板路径
	 * @param students 学生考勤统计
	 * @return
	 */
	def exportStudentsStatis(File templatePath, Term term, List students, RollcallStatisService rollcallStatisService) {
		
		students.each {student ->			
			// 查询明细
			List rollcallItems = rollcallStatisService.studentRollcallItems(student.id, term)
			rollcallItems = rollcallItems.findAll {item ->
				item.type > 0
			}
			
			def totalHours = 0;
			def factor1 = [0, 1, 0.5, 1, 0, 1]
			def factor2 = [0, 1, 0,   1, 0, 1] // 按次还是按节
			rollcallItems.each {item ->
				totalHours += factor1[item.type] * (factor2[item.type] ? item.totalSection : 1)
			}
			
			rollcallItems.sort {a, b ->
				a.week <=> b.week ?:
				a.dayOfWeek <=> b.dayOfWeek ?:
				a.startSection <=> b.startSection
			}
			
			student.rollcallItems = rollcallItems
			
			// 计算折合旷课次数
			student.totalHours = totalHours
		}

		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		// 边框样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		Sheet sheet0 = workbook.getSheetAt(0)
		Sheet sheet1 = workbook.getSheetAt(1)

		def rowIndex0 = 1
		def rowIndex1 = 1
		
		students.each {student ->
			Row row0 = sheet0.createRow(rowIndex0++)
			[
				student.id,
				student.name,
				student.adminClass,
				student.absent,
				student.late,
				student.early,
				student.leave,
				student.total,
				student.totalHours
			].eachWithIndex { value, col0 ->
				row0.createCell(col0).with {
					setCellStyle(style)
					setCellValue(value)
				}
			}

			student.rollcallItems.each {item ->
				Row row1 = sheet1.createRow(rowIndex1++)
				[
					student.id,
					student.name,
					student.adminClass,
					item.week,
					message("tms.date.dayOfWeek.${item.dayOfWeek}") +  " " + 
					message("tms.arrangement.sections", [item.startSection, item.startSection + item.totalSection - 1]),
					item.totalSection,
					item.courseClass,
					item.teacher,
					message("tms.rollcall.type.${item.type}")				
				].eachWithIndex { value, col1 ->
					row1.createCell(col1).with {
						setCellStyle(style)
						setCellValue(value)
					}
				}
			}

		}
		return workbook
	}
	
	/**
	 * 导出课程班考勤统计，
	 * @param templatePath 模板路径
	 * @param students 学生考勤统计
	 * @return
	 */
	def exportCourseClassStatis(File templatePath, String courseClassId, List students, RollcallStatisService rollcallStatisService) {
		
		students.each {student ->
			// 查询明细
			List rollcallItems = rollcallStatisService.studentRollcallItems(student.id, courseClassId)
			rollcallItems = rollcallItems.findAll {item ->
				item.type > 0
			}
			
			def totalHours = 0;
			def factor1 = [0, 1, 0.5, 1, 0, 1]
			def factor2 = [0, 1, 0,   1, 0, 1] // 按次还是按节
			rollcallItems.each {item ->
				totalHours += factor1[item.type] * (factor2[item.type] ? item.totalSection : 1)
			}
			
			rollcallItems.sort {a, b ->
				a.week <=> b.week ?:
				a.dayOfWeek <=> b.dayOfWeek ?:
				a.startSection <=> b.startSection
			}
			
			student.rollcallItems = rollcallItems
			
			// 计算折合旷课次数
			student.totalHours = totalHours
		}

		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)
		
		// 边框样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		
		Sheet sheet0 = workbook.getSheetAt(0)
		Sheet sheet1 = workbook.getSheetAt(1)

		def rowIndex0 = 1
		def rowIndex1 = 1
		
		students.each {student ->
			Row row0 = sheet0.createRow(rowIndex0++)
			[
				student.id,
				student.name,
				student.adminClass,
				student.absent,
				student.late,
				student.early,
				student.leave,
				student.total,
				student.totalHours
			].eachWithIndex { value, col0 ->
				row0.createCell(col0).with {
					setCellStyle(style)
					setCellValue(value)
				}
			}

			student.rollcallItems.each {item ->
				Row row1 = sheet1.createRow(rowIndex1++)
				[
					student.id,
					student.name,
					student.adminClass,
					item.week,
					message("tms.date.dayOfWeek.${item.dayOfWeek}") +  " " +
					message("tms.arrangement.sections", [item.startSection, item.startSection + item.totalSection - 1]),
					item.totalSection,
					item.courseClass,
					item.teacher,
					message("tms.rollcall.type.${item.type}")
				].eachWithIndex { value, col1 ->
					row1.createCell(col1).with {
						setCellStyle(style)
						setCellValue(value)
					}
				}
			}

		}
		return workbook
	}

	private static final RC_SYMBOLS = [
		'',
		'\u25CB',
		'\u0424',
		'\u00D7',
		'',
		'\u0424\u00D7'
	]
	
	private static final LV_SYMBOLS = [
		'',
		'\u25B3',
		'\u221A',
		'\u25CE'
	]

	private static void copyRow(Sheet worksheet, int sourceRowNum, int destinationRowNum) {
		// Get the source / new row
		Row newRow = worksheet.getRow(destinationRowNum);
		Row sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		newRow.height = sourceRow.height


		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			Cell oldCell = sourceRow.getCell(i);
			Cell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Use old cell style
			newCell.setCellStyle(oldCell.getCellStyle());

			// If there is a cell comment, copy
			if (newCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					newCell.setCellValue(oldCell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					newCell.setCellErrorValue(oldCell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					newCell.setCellFormula(oldCell.getCellFormula());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					newCell.setCellValue(oldCell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					newCell.setCellValue(oldCell.getRichStringCellValue());
					break;
			}
		}
	}

}
