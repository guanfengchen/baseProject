package com.gof.base.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * @author gfchen
 * @since 6.2
 */

public class BarChart extends Chart{
    public void create() {
        initChart();
        double[][] data = new double[][]{{1230, 1110, 1120}, {720, 750, 860}, {830, 780, 790}, {400, 380, 390}};
        String[] rowKeys = {"宝马1系", "宝马2系", "宝马3系", "宝马4系"};
        String[] columnKeys = {"新进店", "再次进店", "意向进店"};

        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);

        JFreeChart chart = ChartFactory.createBarChart3D("车型关注计数",
                "车型",
                "关注度",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);


        CategoryPlot plot = chart.getCategoryPlot();
        //设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
        //设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
        //设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);
        //显示每个柱的数值，并修改该数值的字体属性
        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        Font titleFont = new Font("黑体", Font.BOLD, 18);
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(titleFont);// 为标题设置上字体

//        Font plotFont = new Font("宋体", Font.PLAIN, 14);
//        PiePlot plot = (PiePlot) chart.getPlot();
//        plot.setLabelFont(plotFont); // 为饼图元素设置上字体

        Font LegendFont = new Font("楷体", Font.PLAIN, 13);
        LegendTitle legend = chart.getLegend(0);
        legend.setItemFont(LegendFont);// 为图例说明设置字体

        //默认的数字显示在柱子中，通过如下两句可调整数字的显示
        //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelAnchorOffset(10D);

        //设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.4);
        plot.setRenderer(renderer);

        //设置地区、销量的显示位置
        //将下方的“车型”放到上方
//        plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        //将默认放在左边的“关注度”放到右方
        plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        try {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(byteArrayOutputStream, chart, 600, 300);
            FileOutputStream file=new FileOutputStream("d:\\BarChart.jpg");
            file.write(byteArrayOutputStream.toByteArray());
            file.close();
        } catch (Exception e) {
            System.out.println("Problem occurred creating BarChart.");
        }
    }
}
