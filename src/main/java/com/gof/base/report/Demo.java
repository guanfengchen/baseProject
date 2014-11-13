package com.gof.base.report;

import com.gof.base.mvc.controller.report.Bean;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * @author gfchen
 * @since 6.2
 */

public class Demo {
    public void createDemoReport(String templatePatch,String reportPatch) throws IOException {
        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templatePatch));
        updateFirstPage(pptx.getSlides()[0]);
        updateSecondPage(pptx.getSlides()[1]);
        updateThirdPage(pptx.getSlides()[2]);
        // save the result
        FileOutputStream out = new FileOutputStream(reportPatch);
        pptx.write(out);
        out.close();
    }

    public void updateThirdPage(XSLFSlide slide) throws IOException {
        createParChart(slide);


    }

    public void updateSecondPage(XSLFSlide slide) throws IOException {
        for(XSLFShape shape : slide.getShapes()){
            if(shape instanceof XSLFTable){
                XSLFTable table = (XSLFTable)shape;
                for(int i = 0; i< 15; i++){
                    XSLFTableRow addRow = table.addRow();
                    addRow.setHeight(8);
                    for(int j=0; j<8;j++){
                        XSLFTableCell addClee = addRow.addCell();
                        if(j == 0 ){
                            addClee.addNewTextParagraph().addNewTextRun().setText(String.valueOf(j+1));
                        }else if(j==1){
                            addClee.addNewTextParagraph().addNewTextRun().setText("2014-11-14");
                        }else{
                            addClee.addNewTextParagraph().addNewTextRun().setText("23");
                        }
                        addClee.getTextParagraphs().get(0).getTextRuns().get(0).setFontSize(8);
                        addClee.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    }
                }

            }
        }
        prefillPieChart(slide);
        createColumnChart(slide);


    }

    private void prefillPieChart(XSLFSlide slide) throws IOException {
        // find pie chart in the slide
        XSLFChart chart = null;
        for(POIXMLDocumentPart part : slide.getRelations()){
            if(part instanceof XSLFChart){
                chart = (XSLFChart) part;
                break;
            }
        }

        if(chart == null) throw new IllegalStateException("chart not found in the template");

        // embedded Excel workbook that holds the chart data
        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();


        CTPieChart pieChart = plotArea.getPieChartArray(0);
        //Pie Chart Series
        CTPieSer ser = pieChart.getSerArray(0);

        // Series Text
        CTSerTx tx = ser.getTx();
        tx.getStrRef().getStrCache().getPtArray(0).setV("2014/5/1 再次进店比率");
        sheet.createRow(0).createCell(1).setCellValue("2014/5/1 再次进店比率");
        String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);


        // Category Axis Data
        CTAxDataSource cat = ser.getCat();
        CTStrData strData = cat.getStrRef().getStrCache();

        // Values
        CTNumDataSource val = ser.getVal();
        CTNumData numData = val.getNumRef().getNumCache();

        strData.setPtArray(null);  // unset old axis text
        numData.setPtArray(null);  // unset old values


        // set model
        int idx = 0;
        int rownum = 1;
        List<Bean> beanList = Bean.mockBeanList();
        for(Bean bean : beanList){
            CTNumVal numVal = numData.addNewPt();
            numVal.setIdx(idx);
            numVal.setV(bean.getValue());

            CTStrVal sVal = strData.addNewPt();
            sVal.setIdx(idx);
            sVal.setV(bean.getName());

            idx++;
            XSSFRow row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(bean.getName());
            row.createCell(1).setCellValue(Double.valueOf(bean.getValue()));
        }
        numData.getPtCount().setVal(idx);
        strData.getPtCount().setVal(idx);

        String numDataRange = new CellRangeAddress(1, rownum-1, 1, 1).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
        cat.getStrRef().setF(axisDataRange);

        // updated the embedded workbook with the data
        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
        wb.write(xlsOut);
        xlsOut.close();

    }

    private void createParChart(XSLFSlide slide) throws IOException {


        // find chart in the slide
        XSLFChart chart = null;
        for(POIXMLDocumentPart part : slide.getRelations()){
            if(part instanceof XSLFChart){
                chart = (XSLFChart) part;
                break;
            }
        }

        if(chart == null) throw new IllegalStateException("chart not found in the template");

        // embedded Excel workbook that holds the chart data
        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();
        CTLegend ctLegend = ctChart.getLegend();

        CTBarChart barChart = plotArea.getBarChartArray()[0];
        //Pie Chart Series
       List<CTBarSer> serList = barChart.getSerList();
        int i = 1;
        int rownum2 = 1;
        List<Bean> beanList = Bean.mockBeanList();
        for(Bean bean : beanList){
            sheet.createRow(rownum2++);
        }
        for(CTBarSer ser : serList) {
//            System.out.println(ser.getTx().getStrRef().getStrCache().getPtArray(0).getV());

            ser.getTx().getStrRef().getStrCache().getPtArray(0).setV("进店"+i+"次");
            if(i == 1){
                sheet.createRow(0).createCell(i).setCellValue("进店"+i+"次");
            }else{
                sheet.getRow(0).createCell(i).setCellValue("进店"+i+"次");
            }
            String titleRef = new CellReference(sheet.getSheetName(), 0, i, true, true).formatAsString();
            ser.getTx().getStrRef().setF(titleRef);
            // Category Axis Data
            CTAxDataSource cat = ser.getCat();
            CTStrData strData = cat.getStrRef().getStrCache();

            // Values
            CTNumDataSource val = ser.getVal();
            CTNumData numData = val.getNumRef().getNumCache();

            strData.setPtArray(null);  // unset old axis text
            numData.setPtArray(null);  // unset old values

            // set model
            int idx = 0;

            int rownum = 1;
            for(Bean bean : beanList){
                CTNumVal numVal = numData.addNewPt();
                numVal.setIdx(idx);
                Random r=new Random();
                int number = r.nextInt(200);
                numVal.setV(String.valueOf(number));
//
                CTStrVal sVal = strData.addNewPt();
                sVal.setIdx(idx);
                sVal.setV(bean.getName());
//
                idx++;
                XSSFRow row = null;
//                if(sheet.getRow(rownum++) == null){
                    row = sheet.getRow(rownum++);
//                }else{
//                    row = sheet.getRow(rownum++);
//                }
                row.createCell(0).setCellValue(bean.getName());
                row.createCell(i).setCellValue(Double.valueOf(number));
            }
            numData.getPtCount().setVal(idx);
            strData.getPtCount().setVal(idx);

            String numDataRange = new CellRangeAddress(1, rownum-1, i, i).formatAsString(sheet.getSheetName(), true);
            val.getNumRef().setF(numDataRange);
            String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
            cat.getStrRef().setF(axisDataRange);

            i++;

        }

        // updated the embedded workbook with the data
        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
        wb.write(xlsOut);
        xlsOut.close();

    }

    private void createColumnChart(XSLFSlide slide) throws IOException {


        // find chart in the slide
        XSLFChart chart = null;
        for(POIXMLDocumentPart part : slide.getRelations()){
            if(part instanceof XSLFChart){
                chart = (XSLFChart) part;
            }
        }

        if(chart == null) throw new IllegalStateException("chart not found in the template");

        // embedded Excel workbook that holds the chart data
        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();


        CTLineChart barChart = plotArea.getLineChartList().get(0);
        //Pie Chart Series
       List<CTLineSer> serList = barChart.getSerList();
        int i = 1;
        int rownum2 = 1;
        List<Bean> beanList = Bean.mockBeanList();
        for(Bean bean : beanList){
            sheet.createRow(rownum2++);
        }
        for(CTLineSer ser : serList) {
//            System.out.println(ser.getTx().getStrRef().getStrCache().getPtArray(0).getV());

            ser.getTx().getStrRef().getStrCache().getPtArray(0).setV("宝马"+i+"系列");
            if(i == 1){
                sheet.createRow(0).createCell(i).setCellValue("宝马"+i+"系列");
            }else{
                sheet.getRow(0).createCell(i).setCellValue("宝马"+i+"系列");
            }
            String titleRef = new CellReference(sheet.getSheetName(), 0, i, true, true).formatAsString();
            ser.getTx().getStrRef().setF(titleRef);
            // Category Axis Data
            CTAxDataSource cat = ser.getCat();
            CTStrData strData = cat.getStrRef().getStrCache();

            // Values
            CTNumDataSource val = ser.getVal();
            CTNumData numData = val.getNumRef().getNumCache();

            strData.setPtArray(null);  // unset old axis text
            numData.setPtArray(null);  // unset old values

            // set model
            int idx = 0;

            int rownum = 1;
            for(Bean bean : beanList){
                CTNumVal numVal = numData.addNewPt();
                numVal.setIdx(idx);
                Random r=new Random();
                int number = r.nextInt(100);
                numVal.setV(String.valueOf(number));
//
                CTStrVal sVal = strData.addNewPt();
                sVal.setIdx(idx);
                sVal.setV("2014-11-1"+idx);
//
                idx++;
                XSSFRow row = null;
//                if(sheet.getRow(rownum++) == null){
                    row = sheet.getRow(rownum++);
//                }else{
//                    row = sheet.getRow(rownum++);
//                }
                row.createCell(0).setCellValue("2014-11-1"+idx);
                row.createCell(i).setCellValue(Double.valueOf(number));
            }

            numData.getPtCount().setVal(idx);
            strData.getPtCount().setVal(idx);

            String numDataRange = new CellRangeAddress(1, rownum-1, i, i).formatAsString(sheet.getSheetName(), true);
            val.getNumRef().setF(numDataRange);
            String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
            cat.getStrRef().setF(axisDataRange);

            i++;

        }

        // updated the embedded workbook with the data
        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
        wb.write(xlsOut);
        xlsOut.close();

    }

    public void updateFirstPage(XSLFSlide slide) throws IOException {
        // find text box in the slide
        for(XSLFShape shape : slide.getShapes()){
            if(shape instanceof XSLFTextShape) {
                XSLFTextShape tsh = (XSLFTextShape)shape;
                for(XSLFTextParagraph p : tsh){
                    for(XSLFTextRun r : p){
                        if("${start date}".equalsIgnoreCase(r.getText())){
                            r.setText("2014/5 上半月");
                        }
                        if("${number}".equalsIgnoreCase(r.getText())){
                            r.setText("167");
                        }
                    }
                }
            }
        }

        // find chart in the slide
        XSLFChart chart = null;
        for(POIXMLDocumentPart part : slide.getRelations()){
            if(part instanceof XSLFChart){
                chart = (XSLFChart) part;
                break;
            }
        }

        // embedded Excel workbook that holds the chart data
        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();
        CTLegend ctLegend = ctChart.getLegend();

        CTBarChart barChart = plotArea.getBarChartList().get(0);
        List<CTBarSer> serList = barChart.getSerList();

        int i = 1;
        for(CTBarSer ser : serList) {
            ser.getTx().getStrRef().getStrCache().getPtArray(0).setV("人流增长率");
            sheet.createRow(0).createCell(i).setCellValue("人流增长率");
            String titleRef = new CellReference(sheet.getSheetName(), 0, i, true, true).formatAsString();
            ser.getTx().getStrRef().setF(titleRef);
            // Category Axis Data
            CTAxDataSource cat = ser.getCat();
            CTStrData strData = cat.getStrRef().getStrCache();

            // Values
            CTNumDataSource val = ser.getVal();
            CTNumData numData = val.getNumRef().getNumCache();

            // unset old axis text
            strData.setPtArray(null);

            // unset old values
            numData.setPtArray(null);

            // set model
            int idx = 0;

            int rownum = 1;
            double[] doubleArray = new double[]{0.34,0.56,0.34,0.78,0.98,0.56};
            for(int j = 0; j < doubleArray.length; j++){
                CTNumVal numVal = numData.addNewPt();
                numVal.setIdx(idx);
                numVal.setV(String.valueOf(doubleArray[j]));

                CTStrVal sVal = strData.addNewPt();
                sVal.setIdx(idx);
                sVal.setV("增长数");
//
                idx++;
                XSSFRow row = null;
                row = sheet.createRow(rownum++);
                row.createCell(0).setCellValue("增长数");
                row.createCell(i).setCellValue(doubleArray[j]);
            }
            numData.getPtCount().setVal(idx);
            strData.getPtCount().setVal(idx);

            String numDataRange = new CellRangeAddress(1, rownum-1, i, i).formatAsString(sheet.getSheetName(), true);
            val.getNumRef().setF(numDataRange);
            String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
            cat.getStrRef().setF(axisDataRange);

            i++;
        }

        // updated the embedded workbook with the data
        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
        wb.write(xlsOut);
        xlsOut.close();
    }
}
