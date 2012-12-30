/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.12.12 
*
*/


package com.jmelzer.service.impl;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtil {
    private ImageUtil() {
    }

    public static BufferedImage calcImage(byte[] bytes) {
        if (bytes==null) {
            return null;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
            bufferedImage = Scalr.resize(bufferedImage, 20, 20);
            return bufferedImage;
        } catch (IOException e) {
            //todo log
            e.printStackTrace();
        }
        return null;
    }

}
