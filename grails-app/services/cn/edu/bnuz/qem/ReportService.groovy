package cn.edu.bnuz.qem

import java.io.File;
import groovy.sql.Sql
import org.apache.poi.ss.usermodel.Workbook;

import grails.transaction.Transactional
import cn.edu.bnuz.qem.project.QemTask
import cn.edu.bnuz.tms.security.SecurityService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.CellRangeAddress
import cn.edu.bnuz.tms.organization.Department
@Transactional
class ReportService {
	SecurityService securityService
	def dataSource
	def messageSource
	def reportByCollege() {
		Sql sql = new Sql(dataSource)
		def results = sql.rows '''
select 	IFNULL( d.short_name ,'total') as departmentName,
		qp.project_level as projectLevel,
		qp.status as status,
		CONCAT(min(SUBSTR(qp.begin_year,1,4)),'-',max(SUBSTR(qp.begin_year,1,4))) as beginYear,
		count(*) as counter
from qem_task qp join org_department d on qp.department_id = d.id
group by d.short_name,qp.project_level,qp.status with rollup
'''
	}
	def reportByType() {
		Sql sql = new Sql(dataSource)
		def results = sql.rows '''
select 	IFNULL( pt.parent_type_name ,'total') as departmentName,
		qp.project_level as projectLevel,
		qp.status as status,
		CONCAT(min(SUBSTR(qp.begin_year,1,4)),'-',max(SUBSTR(qp.begin_year,1,4))) as beginYear,
		count(*) as counter
from qem_task qp join qem_type d on qp.qem_type_id = d.id join qem_parent_type pt on d.parent_type_id=pt.id
group by pt.parent_type_name,qp.project_level,qp.status with rollup
'''
	}
	Workbook exportReport(File templatePath, report) {
		// 打开模板
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templatePath))
		Workbook workbook = new HSSFWorkbook(fs)		
		def entry =report
		// 设置第1页的名称
		def sheet0 = workbook.getSheetAt(0)	
		def style =createStyle(workbook)
		def endIndex=1
		def startIndex = 2
		entry.eachWithIndex { item, index ->
			if (item.status || (!item.status && !item.projectLevel) ||item.departmentName=="total" ){
				endIndex = startIndex + index
				Row row = sheet0.createRow(endIndex)
				[
					endIndex - 2+1,
					item.departmentName!="total"?item.departmentName:messageSource.getMessage("qem.report.total", null, Locale.CHINA),				
					item.projectLevel?messageSource.getMessage("qem.level.${item.projectLevel}", null, Locale.CHINA):"",
					((!item.status && !item.projectLevel) ||item.departmentName=="total")?item.beginYear:"",
					item.status?messageSource.getMessage("qem.status.${item.status}", null, Locale.CHINA):"",
					item.counter,
					"",
					""
				].eachWithIndex { value, col ->
					Cell cell = row.createCell(col)
					cell.setCellStyle(style)
					cell.setCellValue(value)
				}
			}else{
				startIndex-- //避免跳行
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
