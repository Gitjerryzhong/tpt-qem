package cn.edu.bnuz.tms.card

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

import cn.edu.bnuz.tms.organization.PicturesService
import cn.edu.bnuz.tms.export.ExcelUtils
import cn.edu.bnuz.tms.card.ReissueOrder;


class CardReissueOrderExportService {
	PicturesService picturesService

	byte[] exportPhotos(order) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		ZipOutputStream zipFile = new ZipOutputStream(baos)
		order.items.each { item ->
			def file = picturesService.getFile(item.studentId)
			if(file.exists()) {
				zipFile.putNextEntry(new ZipEntry(file.name))
				file.withInputStream { input -> zipFile << input }
				zipFile.closeEntry()
			} else {
				log.debug("picture ${item.studentId} not exists.")
			}
		}
		zipFile.finish()

		return baos.toByteArray()
	}

	Workbook exportOrder(File templatePath, order) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)

		// 边框样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		Sheet sheet = workbook.getSheetAt(0)
		sheet.getRow(1).getCell(1).setCellValue("$order.orderId")
		sheet.getRow(1).getCell(10).setCellValue(new Date().format("yyyy-MM-dd"))

		def startIndex = 3

		def items = order.items.sort { a, b ->
			a.studentId <=> b.studentId
		}
		
		items.eachWithIndex { item, index ->
			Row row = sheet.createRow(startIndex + index)

			[
				index + 1,
				item.studentId,
				item.name,
				item.sex,
				item.province,
				item.department,
				item.major,
				item.birthday.toString(),
				item.educationLevel,
				item.applyDate.format("yyyy-MM-dd"),
				item.validDate
			].eachWithIndex { value, col ->
				row.createCell(col).with {
					setCellStyle(style)
					setCellValue(value)
				}
			}
		}

		return workbook
	}

	Workbook exportDistribute(File templatePath, order) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)

		// 边框样式
		HSSFCellStyle style=workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		def entries = order.items
				.findAll {it.status == 4}
				.groupBy {it.department}
				.entrySet()
				.sort {it.key}

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
				a.adminClass <=> b.adminClass ?: a.studentId <=> b.studentId
			}

			entry.value.eachWithIndex { item, index ->
				def sheet = workbook.getSheetAt(i)
				sheet.getRow(1).getCell(1).setCellValue("$order.orderId")
				sheet.getRow(1).getCell(3).setCellValue("$entry.key")
				sheet.getRow(1).getCell(6).setCellValue(new Date().format("yyyy-MM-dd"))

				def startIndex = 3

				Row row = sheet.createRow(startIndex + index)

				[
					index + 1,
					item.studentId,
					item.name,
					item.sex,
					item.adminClass,
					item.major,
					""
				].eachWithIndex { value, col ->
					row.createCell(col).with {
						setCellStyle(style)
						setCellValue(value)
					}
				}
			}
		}
		return workbook
	}
}
