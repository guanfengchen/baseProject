package com.gof.base.mvc.controller.report;

import com.gof.base.chart.BarChart;
import com.gof.base.chart.PieChart;
import com.gof.base.chart.TimeSeriesChart;
import org.apache.poi.hslf.model.*;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
                cell.setFillColor(Color.getHSBColor(HSB[0],HSB[1],HSB[2]));
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
