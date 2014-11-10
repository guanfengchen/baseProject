package com.gof.base.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author gfchen
 * @since 6.2
 */

public class PieChart extends Chart{
    public void create() {
        initChart();
// Create a simple pie chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("第1季度", new Integer(75));
        pieDataset.setValue("第2季度", new Integer(150));
        pieDataset.setValue("第3季度", new Integer(60));
        pieDataset.setValue("第4季度", new Integer(112));
        JFreeChart chart = ChartFactory.createPieChart3D
                ("宝马2014销售报表", // Title
                        pieDataset, // Dataset
                        true, // Show legend
                        true, // Use tooltips
                        false // Configure chart to generate URLs?
                );
        Font titleFont = new Font("黑体", Font.BOLD, 18);
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(titleFont);// 为标题设置上字体

        Font plotFont = new Font("宋体", Font.PLAIN, 14);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(plotFont); // 为饼图元素设置上字体

        Font LegendFont = new Font("楷体", Font.PLAIN, 13);
        LegendTitle legend = chart.getLegend(0);
        legend.setItemFont(LegendFont);// 为图例说明设置字体

        chart.setBackgroundPaint(java.awt.Color.white);
        chart.setBorderVisible(false);
        chart.getPlot().setBackgroundPaint(java.awt.Color.white);
        PiePlot pieplot = (PiePlot) chart.getPlot();
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                ("{0}: ({2})"), NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        pieplot.setB

//        PiePlot piePlot= (PiePlot) chart.getPlot();
//        piePlot.setOutlinePaint(Color.WHITE);
//        piePlot.setShadowPaint(Color.WHITE);
        try {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, 500, 300);
            FileOutputStream file=new FileOutputStream("d:\\PieChart.jpg");
            file.write(byteArrayOutputStream.toByteArray());
            file.close();
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
    }


}
