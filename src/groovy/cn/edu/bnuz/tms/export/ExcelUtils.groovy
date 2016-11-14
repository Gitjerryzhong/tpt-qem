package cn.edu.bnuz.tms.export

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Footer
import org.apache.poi.ss.usermodel.Header
import org.apache.poi.ss.usermodel.PrintSetup
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress

class ExcelUtils {
	static void copyRow(Sheet worksheet, int sourceRowNum, int destinationRowNum) {
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

	/**
	 * @param newSheet the sheet to create from the copy.
	 * @param sheet the sheet to copy.
	 */
	static void copySheets(Sheet newSheet, Sheet sheet){
		copySheets(newSheet, sheet, true);
	}

	/**
	 * @param newSheet the sheet to create from the copy.
	 * @param sheet the sheet to copy.
	 * @param copyStyle true copy the style.
	 */
	static void copySheets(Sheet newSheet, Sheet sheet, boolean copyStyle){
		int maxColumnNum = 0;
		Map<Integer, CellStyle> styleMap = (copyStyle) ? new HashMap<Integer, CellStyle>() : null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row srcRow = sheet.getRow(i);
			Row destRow = newSheet.createRow(i);
			if (srcRow != null) {
				copyRow(sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++) {
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	/**
	 * @param srcSheet the sheet to copy.
	 * @param destSheet the sheet to create.
	 * @param srcRow the row to copy.
	 * @param destRow the row to create.
	 * @param styleMap -
	 */
	public static void copyRow(Sheet srcSheet, Sheet destSheet, Row srcRow, Row destRow, Map<Integer, CellStyle> styleMap) {
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		destRow.setHeight(srcRow.getHeight());
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
			Cell oldCell = srcRow.getCell(j);
			Cell newCell = destRow.getCell(j);
			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				copyCell(oldCell, newCell, styleMap);
				CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());

				if (mergedRegion != null) {
					CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(), mergedRegion.getLastRow(), mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions)) {
						mergedRegions.add(wrapper);
						destSheet.addMergedRegion(wrapper.range);
					}
				}
			}
		}

	}

	/**
	 * @param oldCell
	 * @param newCell
	 * @param styleMap
	 */
	public static void copyCell(Cell oldCell, Cell newCell, Map<Integer, CellStyle> styleMap) {
		if(styleMap != null) {
			if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){
				newCell.setCellStyle(oldCell.getCellStyle());
			} else{
				int stHashCode = oldCell.getCellStyle().hashCode();
				CellStyle newCellStyle = styleMap.get(stHashCode);
				if(newCellStyle == null){
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					styleMap.put(stHashCode, newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		switch(oldCell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellType(Cell.CELL_TYPE_BLANK);
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
			default:
				break;
		}

	}

	/**
	 * @param sheet the sheet containing the data.
	 * @param rowNum the num of the row to copy.
	 * @param cellNum the num of the cell to copy.
	 * @return the CellRangeAddress created.
	 */
	public static CellRangeAddress getMergedRegion(Sheet sheet, int rowNum, short cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	/**
	 * Check that the merged region has been created in the destination sheet.
	 * @param newMergedRegion the merged region to copy or not in the destination sheet.
	 * @param mergedRegions the list containing all the merged region.
	 * @return true if the merged region is already in the list or not.
	 */
	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {
		return !mergedRegions.contains(newMergedRegion);
	}

	public static void copySheetSettings(Sheet newSheet, Sheet sheetToCopy) {

		newSheet.setAutobreaks(sheetToCopy.getAutobreaks());
		newSheet.setDefaultColumnWidth(sheetToCopy.getDefaultColumnWidth());
		newSheet.setDefaultRowHeight(sheetToCopy.getDefaultRowHeight());
		newSheet.setDefaultRowHeightInPoints(sheetToCopy.getDefaultRowHeightInPoints());
		newSheet.setDisplayGuts(sheetToCopy.getDisplayGuts());
		newSheet.setFitToPage(sheetToCopy.getFitToPage());

		newSheet.setForceFormulaRecalculation(sheetToCopy.getForceFormulaRecalculation());

		PrintSetup sheetToCopyPrintSetup = sheetToCopy.getPrintSetup();
		PrintSetup newSheetPrintSetup = newSheet.getPrintSetup();

		newSheetPrintSetup.setPaperSize(sheetToCopyPrintSetup.getPaperSize());
		newSheetPrintSetup.setScale(sheetToCopyPrintSetup.getScale());
		newSheetPrintSetup.setPageStart(sheetToCopyPrintSetup.getPageStart());
		newSheetPrintSetup.setFitWidth(sheetToCopyPrintSetup.getFitWidth());
		newSheetPrintSetup.setFitHeight(sheetToCopyPrintSetup.getFitHeight());
		newSheetPrintSetup.setLeftToRight(sheetToCopyPrintSetup.getLeftToRight());
		newSheetPrintSetup.setLandscape(sheetToCopyPrintSetup.getLandscape());
		newSheetPrintSetup.setValidSettings(sheetToCopyPrintSetup.getValidSettings());
		newSheetPrintSetup.setNoColor(sheetToCopyPrintSetup.getNoColor());
		newSheetPrintSetup.setDraft(sheetToCopyPrintSetup.getDraft());
		newSheetPrintSetup.setNotes(sheetToCopyPrintSetup.getNotes());
		newSheetPrintSetup.setNoOrientation(sheetToCopyPrintSetup.getNoOrientation());
		newSheetPrintSetup.setUsePage(sheetToCopyPrintSetup.getUsePage());
		newSheetPrintSetup.setHResolution(sheetToCopyPrintSetup.getHResolution());
		newSheetPrintSetup.setVResolution(sheetToCopyPrintSetup.getVResolution());
		newSheetPrintSetup.setHeaderMargin(sheetToCopyPrintSetup.getHeaderMargin());
		newSheetPrintSetup.setFooterMargin(sheetToCopyPrintSetup.getFooterMargin());
		newSheetPrintSetup.setCopies(sheetToCopyPrintSetup.getCopies());

		Header sheetToCopyHeader = sheetToCopy.getHeader();
		Header newSheetHeader = newSheet.getHeader();
		newSheetHeader.setCenter(sheetToCopyHeader.getCenter());
		newSheetHeader.setLeft(sheetToCopyHeader.getLeft());
		newSheetHeader.setRight(sheetToCopyHeader.getRight());

		Footer sheetToCopyFooter = sheetToCopy.getFooter();
		Footer newSheetFooter = newSheet.getFooter();
		newSheetFooter.setCenter(sheetToCopyFooter.getCenter());
		newSheetFooter.setLeft(sheetToCopyFooter.getLeft());
		newSheetFooter.setRight(sheetToCopyFooter.getRight());

		newSheet.setHorizontallyCenter(sheetToCopy.getHorizontallyCenter());
		newSheet.setMargin(Sheet.LeftMargin, sheetToCopy.getMargin(Sheet.LeftMargin));
		newSheet.setMargin(Sheet.RightMargin, sheetToCopy.getMargin(Sheet.RightMargin));
		newSheet.setMargin(Sheet.TopMargin, sheetToCopy.getMargin(Sheet.TopMargin));
		newSheet.setMargin(Sheet.BottomMargin, sheetToCopy.getMargin(Sheet.BottomMargin));

		newSheet.setPrintGridlines(sheetToCopy.isPrintGridlines());
		newSheet.setRowSumsBelow(sheetToCopy.getRowSumsBelow());
		newSheet.setRowSumsRight(sheetToCopy.getRowSumsRight());
		newSheet.setVerticallyCenter(sheetToCopy.getVerticallyCenter());
		newSheet.setDisplayFormulas(sheetToCopy.isDisplayFormulas());
		newSheet.setDisplayGridlines(sheetToCopy.isDisplayGridlines());
		newSheet.setDisplayRowColHeadings(sheetToCopy.isDisplayRowColHeadings());
		newSheet.setDisplayZeros(sheetToCopy.isDisplayZeros());
		newSheet.setPrintGridlines(sheetToCopy.isPrintGridlines());
		newSheet.setRightToLeft(sheetToCopy.isRightToLeft());
		newSheet.setZoom(1, 1);
	}
}

class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper> {

	public CellRangeAddress range;

	/**
	 * @param theRange the CellRangeAddress object to wrap.
	 */
	public CellRangeAddressWrapper(CellRangeAddress theRange) {
		this.range = theRange;
	}

	/**
	 * @param o the object to compare.
	 * @return -1 the current instance is prior to the object in parameter, 0: equal, 1: after...
	 */
	public int compareTo(CellRangeAddressWrapper o) {

		if (range.getFirstColumn() < o.range.getFirstColumn()
		&& range.getFirstRow() < o.range.getFirstRow()) {
			return -1;
		} else if (range.getFirstColumn() == o.range.getFirstColumn()
		&& range.getFirstRow() == o.range.getFirstRow()) {
			return 0;
		} else {
			return 1;
		}
	}
}

