package com.gof.base.mvc.controller.report;

import com.gof.base.chart.BarChart;
import com.gof.base.chart.PieChart;
import com.gof.base.chart.TimeSeriesChart;
import com.gof.base.report.Demo;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hslf.model.*;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author gfchen
 * @since 6.2
 */

@Controller
public class PowerPointController {
    @RequestMapping(value="report/ppt.htm")
    public String createPPT() {
        BarChart BarChart = new BarChart();
        BarChart.create();
        PieChart pieChart = new PieChart();
        pieChart.create();
        TimeSeriesChart timeSeriesChart = new TimeSeriesChart();
        timeSeriesChart.create();
        createPPTFile();
        return "report/ppt";
    }

    @RequestMapping(value="report/creatPPT.htm")
    public String editPPT() {
        return "report/createPPT";
    }

    @RequestMapping(value="report/PPT.htm")
    public String editPPT(String templatePatch,String reportPatch) throws InvalidFormatException, IOException, XmlException {
//        String templatePatch = request.getParameter("templatePatch");
//        String reportPatch = request.getParameter("reportPatch");
//        System.out.println("=====templatePatch========"+templatePatch);
//        System.out.println("=====reportPatch========"+reportPatch);
//        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templatePatch));
//        XSLFSlide slide = pptx.getSlides()[0];
//
//        // find chart in the slide
//        XSLFChart chart = null;
//        for(POIXMLDocumentPart part : slide.getRelations()){
//            if(part instanceof XSLFChart){
//                chart = (XSLFChart) part;
//                break;
//            }
//        }
//
//        if(chart == null) throw new IllegalStateException("chart not found in the template");
//
//        // embedded Excel workbook that holds the chart data
//        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet();
//
//        CTChart ctChart = chart.getCTChart();
//        CTPlotArea plotArea = ctChart.getPlotArea();
//        System.out.println("=====plotArea========"+plotArea.toString());
//
//        CTPieChart pieChart = plotArea.getPieChartArray(0);
//        //Pie Chart Series
//        CTPieSer ser = pieChart.getSerArray(0);
//
//        // Series Text
//        CTSerTx tx = ser.getTx();
//        tx.getStrRef().getStrCache().getPtArray(0).setV("2014销售报表");
//        sheet.createRow(0).createCell(1).setCellValue("2014销售报表");
//        String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
//        tx.getStrRef().setF(titleRef);
//
//
//        // Category Axis Data
//        CTAxDataSource cat = ser.getCat();
//        CTStrData strData = cat.getStrRef().getStrCache();
//
//        // Values
//        CTNumDataSource val = ser.getVal();
//        CTNumData numData = val.getNumRef().getNumCache();
//
//        strData.setPtArray(null);  // unset old axis text
//        numData.setPtArray(null);  // unset old values
//
//
//        // set model
//        int idx = 0;
//        int rownum = 1;
//        List<Bean> beanList = Bean.mockBeanList();
//        for(Bean bean : beanList){
//            CTNumVal numVal = numData.addNewPt();
//            numVal.setIdx(idx);
//            numVal.setV(bean.getValue());
//
//            CTStrVal sVal = strData.addNewPt();
//            sVal.setIdx(idx);
//            sVal.setV(bean.getName());
//
//            idx++;
//            XSSFRow row = sheet.createRow(rownum++);
//            row.createCell(0).setCellValue(bean.getName());
//            row.createCell(1).setCellValue(Double.valueOf(bean.getValue()));
//        }
//        numData.getPtCount().setVal(idx);
//        strData.getPtCount().setVal(idx);
//
//        String numDataRange = new CellRangeAddress(1, rownum-1, 1, 1).formatAsString(sheet.getSheetName(), true);
//        val.getNumRef().setF(numDataRange);
//        String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
//        cat.getStrRef().setF(axisDataRange);
//
//        // updated the embedded workbook with the data
//        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
//        wb.write(xlsOut);
//        xlsOut.close();
//        System.out.println("=====xlsOut closed========");
//        // save the result
//        FileOutputStream out = new FileOutputStream(reportPatch);
//        pptx.write(out);
//        out.close();
//        System.out.println("=====pptx write closed========");



        //Bar  chart
//        barChart(templatePatch,reportPatch);

        Demo demo = new Demo();
        demo.createDemoReport(templatePatch,reportPatch);
        return "report/ppt";
    }

    private void barChart(String templatePatch,String reportPatch) throws IOException {

//        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templatePatch));
//        XSLFSlide slide = pptx.getSlides()[1];
//
//        // find chart in the slide
//        XSLFChart chart = null;
//        for(POIXMLDocumentPart part : slide.getRelations()){
//            if(part instanceof XSLFChart){
//                chart = (XSLFChart) part;
//                break;
//            }
//        }
//
//        if(chart == null) throw new IllegalStateException("chart not found in the template");
//
//        // embedded Excel workbook that holds the chart data
//        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
//        XSSFWorkbook wb = new XSSFWorkbook();
//        XSSFSheet sheet = wb.createSheet();
//
//        CTChart ctChart = chart.getCTChart();
//        CTPlotArea plotArea = ctChart.getPlotArea();
//        CTLegend ctLegend = ctChart.getLegend();
//
//        CTBarChart barChart = plotArea.getBarChartArray()[0];
//        //Pie Chart Series
//       List<CTBarSer> serList = barChart.getSerList();
//        int i = 1;
//        int rownum2 = 1;
//        List<Bean> beanList = Bean.mockBeanList();
//        for(Bean bean : beanList){
//            sheet.createRow(rownum2++);
//        }
//        for(CTBarSer ser : serList) {
////            System.out.println(ser.getTx().getStrRef().getStrCache().getPtArray(0).getV());
//
//            ser.getTx().getStrRef().getStrCache().getPtArray(0).setV("宝马"+i+"系列");
//            if(i == 1){
//                sheet.createRow(0).createCell(i).setCellValue("宝马"+i+"系列");
//            }else{
//                sheet.getRow(0).createCell(i).setCellValue("宝马"+i+"系列");
//            }
//            String titleRef = new CellReference(sheet.getSheetName(), 0, i, true, true).formatAsString();
//            ser.getTx().getStrRef().setF(titleRef);
//            // Category Axis Data
//            CTAxDataSource cat = ser.getCat();
//            CTStrData strData = cat.getStrRef().getStrCache();
//
//            // Values
//            CTNumDataSource val = ser.getVal();
//            CTNumData numData = val.getNumRef().getNumCache();
//
//            strData.setPtArray(null);  // unset old axis text
//            numData.setPtArray(null);  // unset old values
//
//            // set model
//            int idx = 0;
//
//            int rownum = 1;
//            for(Bean bean : beanList){
//                CTNumVal numVal = numData.addNewPt();
//                numVal.setIdx(idx);
////                Random random = new Random(100);
//                int number = Integer.valueOf(bean.getValue())+i*10;
//                numVal.setV(String.valueOf(number));
////
//                CTStrVal sVal = strData.addNewPt();
//                sVal.setIdx(idx);
//                sVal.setV(bean.getName());
////
//                idx++;
//                XSSFRow row = null;
////                if(sheet.getRow(rownum++) == null){
//                    row = sheet.getRow(rownum++);
////                }else{
////                    row = sheet.getRow(rownum++);
////                }
//                row.createCell(0).setCellValue(bean.getName());
//                row.createCell(i).setCellValue(Double.valueOf(number));
//            }
//            numData.getPtCount().setVal(idx);
//            strData.getPtCount().setVal(idx);
//
//            String numDataRange = new CellRangeAddress(1, rownum-1, i, i).formatAsString(sheet.getSheetName(), true);
//            val.getNumRef().setF(numDataRange);
//            String axisDataRange = new CellRangeAddress(1, rownum-1, 0, 0).formatAsString(sheet.getSheetName(), true);
//            cat.getStrRef().setF(axisDataRange);
//
//            i++;
//
//        }
//
//        // updated the embedded workbook with the data
//        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
//        wb.write(xlsOut);
//        xlsOut.close();
//
//        // save the result
//        FileOutputStream out = new FileOutputStream(reportPatch);
//        pptx.write(out);
//        out.close();



//        // Series Text
//        CTSerTx tx = ser.getTx();
//        tx.getStrRef().getStrCache().getPtArray(0).setV("宝马2014销售报表");
//        sheet.createRow(0).createCell(1).setCellValue("宝马2014销售报表");
//        String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
//        tx.getStrRef().setF(titleRef);
//
//
//        // Category Axis Data
//        CTAxDataSource cat = ser.getCat();
//        CTStrData strData = cat.getStrRef().getStrCache();
    }


    @RequestMapping(value="report/upload.htm")
    public void uploca(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imageName = request.getParameter("imagename");
        String imageData = request.getParameter("imagedata");
//        System.out.println("==================="+imageName);
//        System.out.println("==================="+imageData);
        imageData = imageData.substring(30);
        imageData = URLDecoder.decode(imageData, "UTF-8");
        //System.out.println(imageData);
        byte[] data = decode(imageData);
//        int len = data.length;
//        int len2 = imageData.length();
        saveImageToDisk(data,imageName);

        String imageName2 = request.getParameter("imagename2");
        String imageData2 = request.getParameter("imagedata2");
        System.out.println("==================="+imageName2);
        System.out.println("==================="+imageData2);
        imageData2 = imageData2.substring(30);
        imageData2 = URLDecoder.decode(imageData2, "UTF-8");
        byte[] data2 = decode(imageData2);
        saveImageToDisk(data2,imageName2);

        BarChart BarChart = new BarChart();
        BarChart.create();
        PieChart pieChart = new PieChart();
        pieChart.create();
        TimeSeriesChart timeSeriesChart = new TimeSeriesChart();
        timeSeriesChart.create();
        createPPTFile();
    }

    private boolean saveImageToDisk(byte[] data,String imageName) throws IOException{
        int len = data.length;
        //
        // 写入到文件
        FileOutputStream outputStream = new FileOutputStream(new File("d://"+imageName));

        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        //
        return true;
    }
    private byte[] decode(String imageData) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(imageData);
        for(int i=0;i<data.length;++i)
        {
            if(data[i]<0)
            {
                //调整异常数据
                data[i]+=256;
            }
        }
        //
        return data;
    }

    @RequestMapping(value="report/sampleChart.htm")
    public String sampleChart() {
        return "chart/sampleChart";
    }

    private void createPPTFile(){

        //table data
        String[][] data = {
                {"宝马车系", "进店人数"},
                {"宝马1", "11,559"},
                {"宝马2", "300"},
                {"宝马3", "10,000"},
                {"宝马4", "10,200,038"}
        };

        SlideShow ppt = new SlideShow();

        Slide slide = ppt.createSlide();
        //create a table of 5 rows and 2 columns
        Table table = new Table(5, 2);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                TableCell cell = table.getCell(i, j);
                cell.setText(data[i][j]);

                RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
                rt.setFontName(PPFont.TIMES_NEW_ROMAN.getFontName());
                rt.setFontSize(10);

                cell.setVerticalAlignment(TextBox.AnchorMiddle);
                cell.setHorizontalAlignment(TextBox.AlignCenter);
                float[] HSB = Color.RGBtoHSB(91, 155, 213, new float[]{0, 0, 0});
                cell.setFillColor(Color.getHSBColor(HSB[0], HSB[1], HSB[2]));
            }

        }

        //set table borders
        Line border = table.createBorder();
        border.setLineColor(Color.black);
        border.setLineWidth(1.0);
        table.setAllBorders(border);

        //set width of the 1st column
        table.setColumnWidth(0, 150);
        //set width of the 2nd column
        table.setColumnWidth(1, 75);
        table.setRowHeight(0,10);
        table.setRowHeight(1,20);
        table.setRowHeight(2,30);
        table.setRowHeight(3,40);
        table.setRowHeight(4,50);


        slide.addShape(table);
        table.moveTo(20, 20);


        try {
            int idx = ppt.addPicture(new File("d:\\PieChart.jpg"), Picture.JPEG);
            Picture pict = new Picture(idx);

            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(300, 20, 300, 200));
            slide.addShape(pict);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            int idx2 = ppt.addPicture(new File("d:\\BarChart.jpg"), Picture.JPEG);
            Picture pict2 = new Picture(idx2);

            //set image position in the slide
            pict2.setAnchor(new java.awt.Rectangle(20, 230, 600, 300));
            slide.addShape(pict2);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }




        Slide slide2 = ppt.createSlide();
        //create a table of 5 rows and 2 columns
        Table table2 = new Table(5, 2);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                TableCell cell = table2.getCell(i, j);
                cell.setText(data[i][j]);

                RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
                rt.setFontName(PPFont.TIMES_NEW_ROMAN.getFontName());
                rt.setFontSize(10);

                cell.setVerticalAlignment(TextBox.AnchorMiddle);
                cell.setHorizontalAlignment(TextBox.AlignCenter);
                float[] HSB = Color.RGBtoHSB(91, 155, 213, new float[]{0, 0, 0});
                cell.setFillColor(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
            }

        }

        //set table borders
        Line border2 = table2.createBorder();
        border2.setLineColor(Color.black);
        border2.setLineWidth(1.0);
        table2.setAllBorders(border2);

        //set width of the 1st column
        table2.setColumnWidth(0, 150);
        //set width of the 2nd column
        table2.setColumnWidth(1, 75);
        table2.setRowHeight(0,20);
        table2.setRowHeight(1,20);
        table2.setRowHeight(2,20);
        table2.setRowHeight(3,20);
        table2.setRowHeight(4,20);


        slide2.addShape(table2);
        table2.moveTo(20, 20);


        try {
            int idx = ppt.addPicture(new File("d:\\PieChart.jpg"), Picture.JPEG);
            Picture pict = new Picture(idx);

            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(300, 20, 300, 200));
            slide2.addShape(pict);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            int idx = ppt.addPicture(new File("d:\\TimeSeriesChart.jpg"), Picture.JPEG);
            Picture pict = new Picture(idx);

            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(20, 230, 600, 300));
            slide2.addShape(pict);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Slide slide3 = ppt.createSlide();
        try {
            int idx = ppt.addPicture(new File("d:\\sampleChart2.png"), Picture.PNG);
            Picture pict = new Picture(idx);

            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(20, 20, 300, 200));
            slide3.addShape(pict);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            int idx = ppt.addPicture(new File("d:\\sampleChart.png"), Picture.PNG);
            Picture pict = new Picture(idx);

            //set image position in the slide
            pict.setAnchor(new java.awt.Rectangle(20, 230, 600, 300));
            slide3.addShape(pict);
//            pict.moveTo(300,20);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("d://hslf-table.ppt");
            ppt.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

//    private void createPicture(){
//        SlideShow ppt = new SlideShow();
//
//        Slide slide = ppt.createSlide();
//
//        //Line shape
//        Line line = new Line();
//        line.setAnchor(new java.awt.Rectangle(50, 50, 100, 20));
//        line.setLineColor(new Color(0, 128, 0));
//        line.setLineStyle(Line.LINE_DOUBLE);
//        slide.addShape(line);
//
//        //TextBox
//        TextBox txt = new TextBox();
//        txt.setText("Hello, World!");
//        txt.setAnchor(new java.awt.Rectangle(300, 100, 300, 50));
//
//        //use RichTextRun to work with the text format
//        RichTextRun rt = txt.getTextRun().getRichTextRuns()[0];
//        rt.setFontSize(32);
//        rt.setFontName("Arial");
//        rt.setBold(true);
//        rt.setItalic(true);
//        rt.setUnderlined(true);
//        rt.setFontColor(Color.red);
//        rt.setAlignment(TextBox.AlignRight);
//
//        slide.addShape(txt);
//
//        //Autoshape
//        //32-point star
//        AutoShape sh1 = new AutoShape(ShapeTypes.Star32);
//        sh1.setAnchor(new java.awt.Rectangle(50, 50, 100, 200));
//        sh1.setFillColor(Color.red);
//        slide.addShape(sh1);
//
//        //Trapezoid
//        AutoShape sh2 = new AutoShape(ShapeTypes.Trapezoid);
//        sh2.setAnchor(new java.awt.Rectangle(150, 150, 100, 200));
//        sh2.setFillColor(Color.blue);
//        slide.addShape(sh2);
//
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("d://slideshow.ppt");
//            ppt.write(out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//
//    }
//
//    private void createTablePPT(){
//        //table data
//        String[][] data = {
//                {"宝马车系", "NUMBER OF RECORDS"},
//                {"宝马1", "11,559"},
//                {"宝马2", "300"},
//                {"宝马3", "10,000"},
//                {"宝马4", "10,200,038"}
//        };
//
//        SlideShow ppt = new SlideShow();
//
//        Slide slide = ppt.createSlide();
//        //create a table of 5 rows and 2 columns
//        Table table = new Table(5, 2);
//        for (int i = 0; i < data.length; i++) {
//            for (int j = 0; j < data[i].length; j++) {
//                TableCell cell = table.getCell(i, j);
//                cell.setText(data[i][j]);
//
//                RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
//                rt.setFontName(PPFont.TIMES_NEW_ROMAN.getFontName());
//                rt.setFontSize(10);
//
//                cell.setVerticalAlignment(TextBox.AnchorMiddle);
//                cell.setHorizontalAlignment(TextBox.AlignCenter);
//                float[] HSB = Color.RGBtoHSB(91, 155, 213, new float[]{0, 0, 0});
//                cell.setFillColor(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
//            }
//
//        }
//
//        //set table borders
//        Line border = table.createBorder();
//        border.setLineColor(Color.black);
//        border.setLineWidth(1.0);
//        table.setAllBorders(border);
//
//        //set width of the 1st column
//        table.setColumnWidth(0, 150);
//        //set width of the 2nd column
//        table.setColumnWidth(1, 75);
//        table.setRowHeight(0,10);
//        table.setRowHeight(1,20);
//        table.setRowHeight(2,30);
//        table.setRowHeight(3,40);
//        table.setRowHeight(4,50);
//
//
//        slide.addShape(table);
//        table.moveTo(20, 20);
//
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("d://hslf-table.ppt");
//            ppt.write(out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//    }
}
