package com.gof.base.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;

import java.awt.*;

/**
 * @author gfchen
 * @since 6.2
 */

public class Chart {

    public void initChart(){
        StandardChartTheme theme = new StandardChartTheme("unicode") {
            public void apply(JFreeChart chart) {
                chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                super.apply(chart);
            }
        };
        theme.setExtraLargeFont(new Font("宋体", Font.ITALIC, 14));
        theme.setLargeFont(new Font("宋体", Font.ITALIC, 12));
        theme.setRegularFont(new Font("宋体", Font.ITALIC, 12));
        theme.setSmallFont(new Font("宋体", Font.ITALIC, 12));
        ChartFactory.setChartTheme(theme);
    }
}
