/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */


package com.jmelzer.data.util;

import com.jmelzer.data.model.Attachment;

import java.io.File;
import java.io.IOException;


public class AttachmentUtil {

    //todo
    static String rootPath = "c:/tmp/wreckcontrol";
    static File rootDir = new File(rootPath);

    public static void store(Attachment media, byte[] blob) {
//        File typeDir = getTypeDir(type);
        try {
            StreamUtils.storeFile(getPath(media), blob);            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] load(Attachment media) {
        try {
            return StreamUtils.loadFile(getPath(media));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getPath(Attachment media) {
        return new File(rootDir, media.getFileName());
    }

    private static void createDir(String name) {
        File dir = new File(rootDir, name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String getUrl(Attachment media, String type) {
        return "/attachments/" + type + "/" + media.getFileName();
    }

}
