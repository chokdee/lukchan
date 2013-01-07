/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 07.01.13 
*
*/


package com.jmelzer.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Calls the external ImageMagick.
 * magickhome must be injected.
 * User: jmelzer
 */
public class ImageMagick {

    protected static Logger log = LoggerFactory.getLogger(ImageMagick.class);

    String convertExe;
    String compositeExe;
    boolean printArgs = false;
    String magickHome;

    public ImageMagick() {

    }

    public void setPrintArgs(boolean printArgs) {
        this.printArgs = printArgs;
    }

    public void setMagickHome(String magickHome) {
        this.magickHome = magickHome;
    }

    @PostConstruct
    public void init() {
        convertExe = magickHome + "/convert" + (isWin() ? ".exe" : "");
        compositeExe = magickHome + "/composite" + (isWin() ? ".exe" : "");
        Assert.isTrue(new File(convertExe).exists(), "ImageMagick doesn't exists " + convertExe);
        Assert.isTrue(new File(compositeExe).exists(), "ImageMagick doesn't exists " + compositeExe);
    }

    private boolean isWin() {
        return (System.getProperty("os.name").toLowerCase().contains("windows"));
    }

    public String startConvert(String filename, String outfilename, String... command) throws IOException {
        return start(convertExe, filename, outfilename, command);
    }

    public String start(String exe, String filename, String outfilename, String... command) throws IOException {

        if (exe == null) {
            throw new RuntimeException("you must define MAGICK_HOME in your .db.properties as path to ImageMagick-Convert executable");
        }

        try {

            List<String> commandList = new ArrayList<String>(command.length + 3);
            commandList.add(exe);
            commandList.addAll(Arrays.asList(command));


            //workaround for animated gifs
            commandList.add(filename + "[0]");
            commandList.add("JPG:" + outfilename);


            ProcessBuilder pb = new ProcessBuilder(commandList);

            if (printArgs) {
                commandList = pb.command();
                for (String s : commandList) {
                    System.out.print(s + " ");
                }
                System.out.print(System.getProperty("line.separator"));
            }

            pb.directory(new File("."));
            Process p = pb.start();

            p.waitFor();

            return outfilename;
        } catch (IOException e) {
            log.error("", e);
            throw e;
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return null;
    }

    public String applyThumbnail(String src, String dest,
                                 int width, int height, int quality) throws IOException {
        return calcAndCropImage(src, dest, width, height, quality);
    }

    private String calcAndCropImage(String src, String dest, int width, int height,
                                    int quality) throws IOException {
        try {

            return startConvert(src, dest,
                                "-colorspace", "RGB",
                                "-thumbnail", height+"x" +width+">",
                                "-background", "white",
                                "-unsharp", "0x.5",
                                "-extent", height+"x" +width,
                                "-gravity", "center");


        } catch (Exception e) {
            log.error("executing imagemagick failed", e);
            return null;
        }
    }

}
