/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 07.01.13 
*
*/


package com.jmelzer.data.util;

public class FileUtil {
    public static String addPreview(String filename) {
        int n = filename.lastIndexOf(".");
        return filename.substring(0, n) + "-preview.jpg";
    }
}
