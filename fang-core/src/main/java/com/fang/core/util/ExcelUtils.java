
package com.fang.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

public class ExcelUtils<T> {

    protected static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 导出Excel方法
     *
     * @param dataList    数据集合
     * @param path        文件保存路径
     * @param headers     表格标题行
     * @param cols        dataList中的数据字段排序
     * @param dateFarmat  日期数据的显示格式
     * @param imageHeight 单元格中图片的高
     * @param imageWidth  单元格中图片的宽
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String[] headers, String[] cols, String dateFarmat,
                                           float imageHeight, float imageWidth) {
        return createExcelByMap(dataList, path, null, headers, cols,
                dateFarmat, imageHeight, imageWidth);
    }

    /**
     * 导出Excel方法
     *
     * @param dataList    数据集合
     * @param path        文件保存路径
     * @param title       sheet名称
     * @param headers     表格标题行
     * @param cols        dataList中的数据字段排序
     * @param imageHeight 单元格中图片的高
     * @param imageWidth  单元格中图片的宽
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String title, String[] headers, String[] cols,
                                           float imageHeight, float imageWidth) {
        return createExcelByMap(dataList, path, title, headers, cols,
                "yyyy-MM-dd HH:mm:ss", imageHeight, imageWidth);
    }

    /**
     * 导出Excel方法
     *
     * @param dataList   数据集合
     * @param path       文件保存路径
     * @param title      sheet名称
     * @param headers    表格标题行
     * @param cols       dataList中的数据字段排序
     * @param dateFarmat 日期数据的显示格式
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String title, String[] headers, String[] cols,
                                           String dateFarmat) {
        return createExcelByMap(dataList, path, title, headers, cols,
                dateFarmat, 60, 80);
    }

    /**
     * 导出Excel方法
     *
     * @param dataList 数据集合
     * @param path     文件保存路径
     * @param title    sheet名称
     * @param headers  表格标题行
     * @param cols     dataList中的数据字段排序
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String title, String[] headers, String[] cols) {
        return createExcelByMap(dataList, path, title, headers, cols,
                "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 导出Excel方法
     *
     * @param dataList 数据集合
     * @param path     文件保存路径
     * @param headers  表格标题行
     * @param cols     dataList中的数据字段排序
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String[] headers, String[] cols) {
        return createExcelByMap(dataList, path, null, headers, cols);
    }

    /**
     * 导出Excel方法
     *
     * @param dataList    数据集合
     * @param path        文件保存路径
     * @param title       sheet名称
     * @param headers     表格标题行
     * @param cols        dataList中的数据字段排序
     * @param dateFarmat  日期数据的显示格式
     * @param imageHeight 单元格中图片的高
     * @param imageWidth  单元格中图片的宽
     * @return
     */
    public static boolean createExcelByMap(List<Map<String, Object>> dataList,
                                           String path, String title, String[] headers, String[] cols,
                                           String dateFarmat, float imageHeight, float imageWidth) {

        boolean b = false;
        //	if (dataList == null || dataList.size() == 0)
        //		return false;
        //判断要生成的文件格式
        boolean is97 = "xlsx".equals(path.substring(path.lastIndexOf(".") + 1));
        if (is97) {
            // 声明一个工作薄
            XSSFWorkbook workbook = new XSSFWorkbook();
            // 生成一个表格
            XSSFSheet sheet = null;
            if (title != null && title.trim().length() > 0)
                sheet = workbook.createSheet(title);
            else
                sheet = workbook.createSheet();
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(15);
            // 声明一个画图的顶级管理器
            XSSFDrawing patriarch = sheet.createDrawingPatriarch();
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = row.createCell(i);
                // cell.setCellStyle(style);
                XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            // 遍历并写入数据
            for (int i = 0, size = dataList.size(); i < size; i++) {
                // 新建一行
                row = sheet.createRow(i + 1);
                Map<String, Object> dataMap = dataList.get(i);
                int index = 0;
                for (String key : cols) {
                    Object value = dataMap.get(key);
                    // 新建一列并设置样式
                    XSSFCell cell = row.createCell(index);
                    // cell.setCellStyle(style);
                    // 判断数据类型并写入数据
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    } else if (value instanceof Float) {
                        float fValue = (Float) value;
                        cell.setCellValue(fValue);
                    } else if (value instanceof Double) {
                        double dValue = (Double) value;
                        cell.setCellValue(dValue);
                    } else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    } else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        cell.setCellValue(bValue);
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFarmat);
                        cell.setCellValue(new HSSFRichTextString(sdf.format(date)));
                    } else if (value instanceof byte[]) { // 图片或者文件数据，此处认为
                        // byte[]为图片数据
                        // 设置行高和列宽
                        row.setHeightInPoints(imageHeight);
                        sheet.setColumnWidth(index, (short) (35.7 * imageWidth));
                        byte[] bsValue = (byte[]) value;
                        XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023,
                                255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        cell.setCellValue(new XSSFRichTextString(value == null ? ""
                                : value.toString()));
                    }
                    index++;
                }
            }
            OutputStream out = null;
            try {
                out = new FileOutputStream(path);
                workbook.write(out);
                b = true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException("保存EXCLE出错！", e);
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error("error", e);
                        throw new RuntimeException("保存EXCLE出错！", e);
                    }
            }
        } else {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = null;
            if (title != null && title.trim().length() > 0)
                sheet = workbook.createSheet(title);
            else
                sheet = workbook.createSheet();
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(15);
            // // 生成一个样式
            // HSSFCellStyle style = workbook.createCellStyle();
            // // 设置这些样式
            // style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            // style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            // style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            // style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            // style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // // 生成一个字体
            // HSSFFont font = workbook.createFont();
            // font.setColor(HSSFColor.VIOLET.index);
            // font.setFontHeightInPoints((short) 12);
            // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // // 把字体应用到当前的样式
            // style.setFont(font);
            // 声明一个画图的顶级管理器
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // // 定义注释的大小和位置,详见文档
            // HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
            // 0, 0, 0, (short) 4, 2, (short) 6, 5));
            // // 设置注释内容
            // comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
            // // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
            // comment.setAuthor("LK0382");
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                // cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            // 遍历并写入数据
            for (int i = 0, size = dataList.size(); i < size; i++) {
                // 新建一行
                row = sheet.createRow(i + 1);
                Map<String, Object> dataMap = dataList.get(i);
                int index = 0;
                for (String key : cols) {
                    Object value = dataMap.get(key);
                    // 新建一列并设置样式
                    HSSFCell cell = row.createCell(index);
                    // cell.setCellStyle(style);
                    // 判断数据类型并写入数据
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        cell.setCellValue(intValue);
                    } else if (value instanceof Float) {
                        float fValue = (Float) value;
                        cell.setCellValue(fValue);
                    } else if (value instanceof Double) {
                        double dValue = (Double) value;
                        cell.setCellValue(dValue);
                    } else if (value instanceof Long) {
                        long longValue = (Long) value;
                        cell.setCellValue(longValue);
                    } else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        cell.setCellValue(bValue);
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(dateFarmat);
                        cell.setCellValue(new HSSFRichTextString(sdf.format(date)));
                    } else if (value instanceof byte[]) { // 图片或者文件数据，此处认为
                        // byte[]为图片数据
                        // 设置行高和列宽
                        row.setHeightInPoints(imageHeight);
                        sheet.setColumnWidth(index, (short) (35.7 * imageWidth));
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,
                                255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        cell.setCellValue(new HSSFRichTextString(value == null ? ""
                                : value.toString()));
                    }
                    index++;
                }
            }
            OutputStream out = null;
            try {
                out = new FileOutputStream(path);
                workbook.write(out);
                b = true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException("保存EXCLE出错！", e);
            } finally {
                if (out != null)
                    try {
                        out.close();
                    } catch (IOException e) {
                        logger.error("error", e);
                        throw new RuntimeException("保存EXCLE出错！", e);
                    }
            }
        }
        return b;
    }

    /**
     * 读取excel信息
     *
     * @param fileName 文件全路径
     * @param colsInfo 表中每列对应的字段名称和类型
     * @param startRow 从第几行开始读取
     * @param endSheet 读到第endSheet为止(包含该sheet)
     * @return
     */
    public static List<Map<String, Object>> readExcel(String fileName, LinkedHashMap<String, Class<?>> colsInfo, int startRow, int endSheet) {
        List<Map<String, Object>> result = null;
        InputStream input = null;
        try {
            input = new FileInputStream(fileName); // 建立输入流
            Workbook wb = null;
            // 根据文件格式(2003或者2007)来初始化
            if (fileName.endsWith("xlsx")) wb = new XSSFWorkbook(input);
            else wb = new HSSFWorkbook(input);
            int sheetNumber = wb.getNumberOfSheets();
            if (sheetNumber < 1) return result;
            if (endSheet < sheetNumber) {
                sheetNumber = endSheet;
            }
            Map<String, Object> map = null;
            // 循环sheet
            for (int numSheet = 0; numSheet < sheetNumber; numSheet++) {
                Sheet sheet = wb.getSheetAt(numSheet); // 获得表单
                int rowNum = sheet.getLastRowNum();
                if (rowNum < startRow) return result;
                result = new ArrayList<Map<String, Object>>();
                // 循环行
                for (int numRow = startRow; numRow < rowNum + 1; numRow++) {
//					System.out.println("当前行数：" + numRow);
                    Row row = sheet.getRow(numRow); // 获得行数据
                    map = new HashMap<String, Object>();
                    Set<String> keySet = colsInfo.keySet();
                    int i = 0;
                    //是否有效行  有些行为全空的(原来是有数据  然后选中后delete  在读取时还是会读进来)
                    boolean isValidRow = false;
                    // 循环列
                    for (String key : keySet) {
                        Object cellValue = getValueForCell(row.getCell(i), colsInfo.get(key));
                        if (cellValue != null && !"".equals(cellValue.toString().trim())) {
                            isValidRow = true;
                        }
                        map.put(key, cellValue);
                        i++;
                    }
                    if (isValidRow)
                        result.add(map);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
        }
        return result;
    }

    public static Map<String, Object> saveExcel(Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
        List<String> colsList = (List<String>) map.get("cols");
        List<String> headerList = (List<String>) map.get("headers");
        String header[] = {};
        header = headerList.toArray(header);
        String cols[] = {};
        cols = colsList.toArray(cols);

        String fileName = System.currentTimeMillis() + ".xls";
        String path = PropertiesUtil.getParam("upload.path");
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        ExcelUtils.createExcelByMap(data, path + fileName, "", header, cols);
        String fileFulUrl = PropertiesUtil.getParam("upload.host") + fileName;
        returnMap.put("url", fileFulUrl);
        returnMap.put("uploadHost", PropertiesUtil.getParam("upload.host"));
        return returnMap;
    }

    private static Object getValueForCell(Cell cell, Class<?> type) {
        Object value = null;
        if (cell == null) return value;
        // 根据cell中的类型来输出数据
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("#.####");
                value = cell.getNumericCellValue();
                value = df.format(value);
                break;
            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                value = cell.getStringCellValue();
                break;
        }
        String valueStr = value.toString();
        if (type == String.class) return valueStr;
        if (type == int.class || type == Integer.class) return Integer.parseInt(valueStr);
        if (type == long.class || type == Long.class) return Long.parseLong(valueStr);
        if (type == float.class || type == Float.class) return Float.parseFloat(valueStr);
        if (type == double.class || type == Double.class) return Double.parseDouble(valueStr);
        if (type == Date.class) return new Date(Long.parseLong(valueStr));
        return value;
    }

}
