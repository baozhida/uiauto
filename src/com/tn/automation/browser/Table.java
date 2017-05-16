package com.tn.automation.browser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.tn.automation.browser.TableColText;

public class Table {
	private int rowCount = 0;
	private int colCount = 0;
	private WebElement element;
	private List<WebElement> tableRows;
	private Map<Integer, List<WebElement>> tableCell;

	private boolean isHeaderExist;

	public Table(WebElement element) {

		tableCell = new HashMap<Integer, List<WebElement>>();
		this.element = element;

		tableRows = this.element.findElements(By.xpath("./tr | ./*/tr"));
		if (tableRows.size() != 0) {
			List<WebElement> tableHeader = tableRows.get(0).findElements(By.xpath("./th"));
			isHeaderExist = tableHeader.size() != 0;

			if (!isHeaderExist) {
				rowCount = tableRows.size();
				if (tableRows.size() > 0) {
					List<WebElement> tableCol1 = tableRows.get(0).findElements(By.xpath("./td"));
					tableCell.put(1, tableCol1);
					colCount = tableCol1.size();
				} else {
					colCount = 0;
				}
			} else {
				tableCell.put(0, tableHeader);
				colCount = tableHeader.size();
				rowCount = tableRows.size() - 1;
			}
		} else {
			isHeaderExist = false;
			rowCount = 0;
			colCount = 0;
		}

	}

	public int getColCount() {
		return colCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	private List<WebElement> getRow(int row) {
		List<WebElement> rowData = tableCell.get(row);

		if (rowData == null) {

			rowData = tableRows.get(isHeaderExist ? row : row - 1).findElements(By.xpath("./td"));
			tableCell.put(row, rowData);
		}

		return rowData;
	}

	public WebElement getCell(int col, int row) {
		List<WebElement> rowData = getRow(row);

		return rowData.get(col - 1);
	}

	/**
	 * 获取表格中指定TD单元格元素
	 * 
	 * @param col
	 *            列号
	 * @param row
	 *            行定位信息，指定列内容匹配的行作为选定行 行定位格式 列号:预期单元格文本:匹配模式(E:ExactMatch,
	 *            P:PartialMatch(Default), R:RegexMatch) exp. 3:商品类型 2:商品名:E
	 * @return TD单元格元素
	 */
	public WebElement getCell(int col, TableColText... rows) {
		for (int i = 1; i <= rowCount; i++) {
			List<WebElement> rowData = getRow(i);
			if (isTextMatched(rowData, rows)) {
				return rowData.get(col - 1);
			}
		}

		return null;
	}

	private boolean isTextMatched(List<WebElement> rowData, TableColText... rows) {
		for (TableColText colText : rows) {
			WebElement cell = rowData.get(colText.getColIndex() - 1);
			String text = cell.getText();
			boolean isMatched = false;
			switch (colText.getMode()) {
			case ExactMatch:
				isMatched = text.equals(colText.getText());
				break;
			case RegexMatch:
				isMatched = text.matches(colText.getText());
				break;
			case PartialMatch:
				isMatched = text.contains(colText.getText());
				break;
			}

			if (!isMatched) {
				return false;
			}
		}

		return true;
	}

	public WebElement findElementAtCell(String xpath, int col, TableColText... rows) {
		WebElement cell = getCell(col, rows);

		return cell.findElement(By.xpath(xpath));
	}

	public WebElement findElementAtCell(String xpath, int col, int row) {
		WebElement cell = getCell(col, row);

		return cell.findElement(By.xpath(xpath));
	}
}
