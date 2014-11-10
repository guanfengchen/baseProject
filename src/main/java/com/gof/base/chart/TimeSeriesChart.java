package com.gof.base.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * @author gfchen
 * @since 6.2
 */

public class TimeSeriesChart extends Chart {



    public void create() {
        initChart();

        TimeSeriesCollection lineDataset = new TimeSeriesCollection(); //访问量统计时间线
        TimeSeries timeSeriesA = new TimeSeries("宝马1系进店趋势");
        TimeSeries timeSeriesB = new TimeSeries("宝马2系进店趋势");
        TimeSeries timeSeriesC = new TimeSeries("宝马3系进店趋势");
        TimeSeries timeSeriesD = new TimeSeries("宝马4系进店趋势");
        //构造数据集合
        timeSeriesA.add(new Day(21, 9, 2014),345);
        timeSeriesA.add(new Day(22, 9, 2014), 456);
        timeSeriesA.add(new Day(23, 9, 2014),567);
        timeSeriesA.add(new Day(24, 9, 2014), 789);

        timeSeriesB.add(new Day(21, 9, 2014), 999);
        timeSeriesB.add(new Day(22, 9, 2014), 1500);
        timeSeriesB.add(new Day(23, 9, 2014), 56);
        timeSeriesB.add(new Day(24, 9, 2014), 300);

        timeSeriesC.add(new Day(21, 9, 2014), 123);
        timeSeriesC.add(new Day(22, 9, 2014), 777);
        timeSeriesC.add(new Day(23, 9, 2014), 345);
        timeSeriesC.add(new Day(24, 9, 2014), 1039);

        timeSeriesD.add(new Day(21, 9, 2014), 777);
        timeSeriesD.add(new Day(22, 9, 2014), 666);
        timeSeriesD.add(new Day(23, 9, 2014), 321);
        timeSeriesD.add(new Day(24, 9, 2014), 123);

        lineDataset.addSeries(timeSeriesA);
        lineDataset.addSeries(timeSeriesB);
        lineDataset.addSeries(timeSeriesC);
        lineDataset.addSeries(timeSeriesD);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("再次进店总趋势", "车系", "人数", lineDataset, true, true, true);
        // /设置主标题
        TextTitle title = new TextTitle("再次进店总趋势", new Font("隶书", Font.ITALIC, 15));
        chart.setTitle(title);
        //设置子标题
        TextTitle subtitle = new TextTitle("2014-09-21 到 2014-09-24", new Font("黑体", Font.BOLD, 12));
        chart.addSubtitle(subtitle);
        chart.setAntiAlias(true);
        //设置网格背景颜色
        chart.getPlot().setBackgroundPaint(Color.white);
        XYPlot plot = chart.getXYPlot();
        DateAxis axis = (DateAxis)plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        axis.setUpperMargin(0.2);//设置距离图片左端距离
        axis.setLowerMargin(0.2); //设置距离图片右端距离

        XYLineAndShapeRenderer  renderer = (XYLineAndShapeRenderer) plot
                .getRenderer();
        //设置曲线是否显示数据点, 显示相应数据值
        renderer.setBaseShapesVisible(true);
//        renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//        renderer.setBaseItemLabelsVisible(true);
//        renderer.setBaseItemLabelPaint(Color.BLACK);//设置数值颜色，默认黑色
//
//        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT));
        //下面可以进一步调整数值的位置，但是得根据ItemLabelAnchor选择情况.
//        renderer.setItemLabelAnchorOffset(10);

        try {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, 600, 300);
            FileOutputStream file=new FileOutputStream("d:\\TimeSeriesChart.jpg");
            file.write(byteArrayOutputStream.toByteArray());
            file.close();
        } catch (Exception e) {
            System.out.println("Problem occurred creating TimeSeriesChart.");
        }
    }


}
