package com.framework.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PoiUtil {

	public static void testOutputExcel(HttpServletResponse response) throws IOException{
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=测试1.xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		OutputStream os = response.getOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("test1");
		HSSFRow row = sheet.createRow(1);
		HSSFCell cell = row.createCell(1);
		cell.setCellValue("hello excel");
		workbook.write(os);
	}
	
	/**
	 * poi格式
	 * @param strings 
	 * @throws IOException 
	 */
	public static void excelStyle(HttpServletResponse response, List<String[]> list, String[] strings) throws IOException{
		
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="+new String("学生信息表.xls".getBytes(),"ISO-8859-1"));// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		
		//创建excel
		HSSFWorkbook workbook = new HSSFWorkbook();
		//设置样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		//设置样式二
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);   //水平居中
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
		
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);//字体
		font.setColor(HSSFColor.BLACK.index); //字体颜色
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontHeightInPoints((short)16);
		style.setFont(font);
		//设置背景色
		//设置前景色为绿色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("test1");
		//合并行
		CellRangeAddress address = new CellRangeAddress(1, list.size(), strings.length-1, strings.length-1);
		sheet.addMergedRegion(address);
		
		//表头
		HSSFRow row = sheet.createRow(0);
		if(strings.length>0){
			for (int i = 0; i < strings.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(strings[i]);
				cell.setCellStyle(style);         //添加样式
				sheet.setColumnWidth(i, 20*256);  //设置列的宽度 1/256 1个字节的长度
			}
		}
		
		//数据
		if(list.size()>0){
			for(int i = 0; i<list.size(); i++){
				HSSFRow arrRow = sheet.createRow(i+1);
				String[] str = list.get(i);
				if(str.length>0){
					if(i==0){
						for(int j = 0; j<str.length; j++){
							if(j==str.length-1){
								HSSFCell cell = arrRow.createCell(j);
								cell.setCellValue(str[j]);
								cell.setCellStyle(style2);
							}else{
								arrRow.createCell(j).setCellValue(str[j]);
							}
						}
					}else{
						for(int j = 0; j<str.length-1; j++){
							arrRow.createCell(j).setCellValue(str[j]);
						}
					}
				}
			}
		}
		
		
		//导出
		workbook.write(response.getOutputStream());
	}

	/**
	 * 根据模版导出excel
	 * @param response
	 * @param list
	 * @param file
	 * @throws IOException 
	 */
	public static void getExcelFromTemaple(HttpServletResponse response,
			List<String[]> list, String file) throws IOException {
		
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="+new String("学生信息表.xls".getBytes(),"ISO-8859-1"));// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型
		
		//获取模版
		FileInputStream in = new FileInputStream(new File(file));
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(int i = 0; i<list.size(); i++){
			String[] str = list.get(i);
			//创建行
			HSSFRow row = sheet.createRow(i+1);
			for (int j = 0; j < str.length; j++) {
				row.createCell(j).setCellValue(str[j]);
			}
		}
		workbook.write(response.getOutputStream());
	}

	/**
	 * 导出word
	 * @param map
	 * @param wordPath
	 * @param wordName
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void exportWrod(HashMap<String, String> map, String wordPath,
			String wordName, HttpServletResponse response) throws UnsupportedEncodingException {
		FileInputStream stream = null;
		
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="+new String(wordName.getBytes(),"ISO-8859-1"));// 设定输出文件头
		response.setContentType("application/msword");// 定义输出类型
		
		try {
			stream = new FileInputStream(wordPath);
			HWPFDocument doc = new HWPFDocument(stream);
			Range range = doc.getRange();
			for (Entry<String, String> entry : map.entrySet()) {
				range.replaceText("${"+entry.getKey()+"}", entry.getValue());
			}
			doc.write(response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(stream!=null){
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * 导出word docx
	 * @param map
	 * @param wordPath
	 * @param wordName
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void exportWrodDocx(HashMap<String, String> map,
			String wordPath, String wordName, HttpServletResponse response) throws UnsupportedEncodingException {
FileInputStream stream = null;
		
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="+new String(wordName.getBytes(),"ISO-8859-1"));// 设定输出文件头
		response.setContentType("application/msword");// 定义输出类型
		
		List<XWPFTableCell> tableCells = new ArrayList<XWPFTableCell>();
		try {
			stream = new FileInputStream(wordPath);
			XWPFDocument doc = new XWPFDocument(stream);
			Iterator it = doc.getTablesIterator();
			while(it.hasNext()){
				XWPFTable table = (XWPFTable) it.next();
				int rows = table.getNumberOfRows();
				for(int i = 0; i<rows; i++){
					XWPFTableRow row = table.getRow(i);
					tableCells.addAll(row.getTableCells());
				}
			}
			
			//
			for(XWPFTableCell cell : tableCells){
				for(Entry<String, String> entry : map.entrySet()){
					if(cell.getText().equals("${"+entry.getKey()+"}")){
						cell.removeParagraph(0);
				           //写入新内容
				        cell.setText(entry.getValue());
					}
				}
			}
			
			doc.write(response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(stream!=null){
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
