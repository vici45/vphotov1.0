package com.zomo.vphoto.controller;

import com.zomo.vphoto.utils.QRCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Controller
public class QRCodeController {

    @RequestMapping(value = "/getQRCode")
    public String getQRCode(String id, HttpServletResponse response) throws Exception {
        String url="http://vphoto.zomo-studio.com/findByIdPage/"+id;
        BufferedImage image=QRCodeUtils.createImage(url,"logo.png",true);
        ImageIO.write(image,"JPEG",response.getOutputStream());
        return "QRCode";

    }
}
