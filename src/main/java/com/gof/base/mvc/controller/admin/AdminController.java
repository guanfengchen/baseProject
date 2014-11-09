package com.gof.base.mvc.controller.admin;


import org.apache.poi.hslf.model.*;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author gfchen
 * @since 1.0
 */

@Controller
public class AdminController {

    @RequestMapping(value="admin/createUser.htm", method = RequestMethod.GET)
    public String createUser() {
        return "admin/createUser";
    }

    @RequestMapping(value="admin/primefacesIndex.htm", method = RequestMethod.GET)
    public String primefacesIndex() throws IOException {
        SlideShow ppt = new SlideShow();

        Slide slide = ppt.createSlide();

        //Line shape
        Line line = new Line();
        line.setAnchor(new java.awt.Rectangle(50, 50, 100, 20));
        line.setLineColor(new Color(0, 128, 0));
        line.setLineStyle(Line.LINE_DOUBLE);
        slide.addShape(line);

        //TextBox
        TextBox txt = new TextBox();
        txt.setText("你好");
        txt.setAnchor(new java.awt.Rectangle(300, 100, 300, 50));

        //use RichTextRun to work with the text format
        RichTextRun rt = txt.getTextRun().getRichTextRuns()[0];
        rt.setFontSize(32);
        rt.setFontName("Arial");
        rt.setBold(true);
        rt.setItalic(true);
        rt.setUnderlined(true);
        rt.setFontColor(Color.red);
        rt.setAlignment(TextBox.AlignRight);

        slide.addShape(txt);

        //Autoshape
        //32-point star
        AutoShape sh1 = new AutoShape(ShapeTypes.Star32);
        sh1.setAnchor(new java.awt.Rectangle(50, 50, 100, 200));
        sh1.setFillColor(Color.red);
        slide.addShape(sh1);

        //Trapezoid
        AutoShape sh2 = new AutoShape(ShapeTypes.Trapezoid);
        sh2.setAnchor(new java.awt.Rectangle(150, 150, 100, 200));
        sh2.setFillColor(Color.blue);
        slide.addShape(sh2);

        FileOutputStream out = new FileOutputStream("slideshow.ppt");
        ppt.write(out);
        out.close();


        return "admin/primefacesIndex";
    }
}
