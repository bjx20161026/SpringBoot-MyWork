package org.springboot.esbcollecthelper.util.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springboot.esbcollecthelper.util.common.Common;
import org.springboot.esbcollecthelper.util.common.PostfixTool;


public class ExcelReader {
	Logger logger = Logger.getLogger(ExcelReader.class);
	
	public String heads;//指定返回的List<Map<String,Object>> 的表头，用","进行分隔
	public int startLine = 0;//文件的起始行
	public int startColumn = 0;//文件的起始列
	public int headLine = -1;//作为文件返回表头的行,当heads为空，而且headLind>-1是启用
	public String sheetName;//文件的sheet名，当sheetName==null时，程序只解析文件的第一个sheet
	public String simpleDateFormat = "yyyy-MM-dd HH:mm:ss";//格式化时间对象转成字符串
	
	public List<Map<String, String>> list;
	public XSSFWorkbook xssfWorkbook;
	public XSSFSheet xssfSheet;
	public XSSFRow xssfRow;
	public HSSFWorkbook hssfWorkbook;
	public HSSFSheet hssfSheet;
	public HSSFRow hssfRow;
	public String value;
	public HashMap<String, String> map;
	
	/**
	 * read the Excel file
	 * 文件中的其他对象也都转成了String类型
	 * @param path
	 *            the path of the Excel file
	 * @return List<Map<String, String>>
	 * @throws IOException
	 */
	public List<Map<String, String>> readExcel(String path) throws IOException {
		if (path == null || Common.EMPTY.equals(path)) {
			return null;
		} else {
			String postfix = PostfixTool.getPostfix(path);
			if (!Common.EMPTY.equals(postfix)) {
				if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					return readXls(path);
				} else if (Common.OFFICE_EXCEL_2007_POSTFIX.equals(postfix)) {
					return readXlsx(path);
				}
			} else {
				logger.info(path + Common.NOT_EXCEL_FILE);
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2007 or later
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return list
	 * @throws IOException 
	 * @throws IOException
	 */
	private List<Map<String, String>> readXlsx(String path) throws IOException {
		logger.info(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		list = new ArrayList<Map<String, String>>();
		xssfWorkbook = new XSSFWorkbook(is);
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if(sheetName!=null&&!xssfSheet.getSheetName().equals(sheetName)||xssfSheet == null){
				continue;
			}
			for (int rowNum = startLine; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				xssfRow = xssfSheet.getRow(rowNum);
				if(heads == null && headLine != -1 && rowNum == headLine){
					heads = "";
					try {
					for (int i = startColumn; (value = getValue(xssfRow.getCell(i))) != null; i++) {
						heads += value+",";
					}
					heads = heads.substring(0,heads.length()-1);
					}catch (NullPointerException e) {
						logger.error(e.getMessage());
					}
					continue;
				}
				String[] head = heads.split(",");
				map = new HashMap<String, String>();
				if (xssfRow != null) {
					map = new HashMap<String, String>();
					for (int i = startColumn; i < head.length+startColumn; i++) {
						map.put(head[i-startColumn], getValue(xssfRow.getCell(i)));
					}
					list.add(map);
				}
			}		
			break;
		}
		xssfWorkbook.close();
		return list;
	}

	/**
	 * Read the Excel 2003
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return list
	 * @throws IOException 
	 * @throws IOException
	 */
	private List<Map<String, String>> readXls(String path) throws IOException {
		logger.info(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		list = new ArrayList<Map<String, String>>();
		hssfWorkbook = new HSSFWorkbook(is);
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(sheetName!=null&&!hssfSheet.getSheetName().equals(sheetName)||hssfSheet == null){
				continue;
			}
			for (int rowNum = startLine; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				hssfRow = hssfSheet.getRow(rowNum);
				if(heads == null && headLine != -1 && rowNum == headLine){
					heads = "";
					try {
					for (int i = startColumn; (value = getValue(hssfRow.getCell(i))) != null; i++) {
						heads += value+",";
					}
					heads = heads.substring(0,heads.length()-1);
					}catch (NullPointerException e) {
						logger.error(e.getMessage());
					}
					continue;
				}
				String[] head = heads.split(",");
				map = new HashMap<String, String>();
				if (hssfRow != null) {
					map = new HashMap<String, String>();
					for (int i = startColumn; i < head.length+startColumn; i++) {
						map.put(head[i-startColumn], getValue(hssfRow.getCell(i)));
					}
					list.add(map);
				}
			}		
			break;
		}
		hssfWorkbook.close();
		return list;
	}
	
	protected String getValue(XSSFCell xssfRow) {
		try {
			if (xssfRow.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfRow.getBooleanCellValue());
			} else if (xssfRow.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(xssfRow)) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(DateUtil.getJavaDate(xssfRow.getNumericCellValue()));
				} else {
					DecimalFormat df = new DecimalFormat("0");
					return String.valueOf(df.format(xssfRow.getNumericCellValue()));
				}
			} else {
				return String.valueOf(xssfRow.getStringCellValue());
			}
		} catch (NullPointerException e) {
			return null;
		}
	}

	protected String getValue(HSSFCell hssfCell) {
		try {
			if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(hssfCell.getBooleanCellValue());
			} else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(hssfCell)) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(DateUtil.getJavaDate(hssfCell.getNumericCellValue()));
				} else {
					DecimalFormat df = new DecimalFormat("0");
					return String.valueOf(df.format(hssfCell.getNumericCellValue()));
				}
			} else {
				return String.valueOf(hssfCell.getStringCellValue());
			}
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	
	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public int getHeadLine() {
		return headLine;
	}

	public void setHeadLine(int headLine) {
		this.headLine = headLine;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSimpleDateFormat() {
		return simpleDateFormat;
	}

	public void setSimpleDateFormat(String simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}
	
	public static void main(String[] args) throws IOException{
		ExcelReader excelReader = new ExcelReader();
//		excelReader.setHeads("userName,passWord,hostIp,path,fileName,schema,tableName,timeStamp,fileType,seperator,charset,startLine,period,id,headNames,paras,columns,columnType,specialFormat");
		excelReader.setHeadLine(1);
		excelReader.setStartLine(1);
		List<Map<String,String>> lista = excelReader.readExcel("F:/BAK/temp/新建 FTP采集模板 - 样例.xlsx");
		System.out.println(excelReader.getHeads());
		for(Map map:lista){
			for(Object object:map.keySet()){
				if(map.get(object)!=null)
				System.out.println(object+"--->>>"+map.get(object));
			}
		}
	}
}
