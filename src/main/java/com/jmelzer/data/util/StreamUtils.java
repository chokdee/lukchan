
/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.data.util;

import java.io.*;

public class StreamUtils {

    public static byte[] toByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyStream(stream, out);
        return out.toByteArray();
    }

    public static void toStream(OutputStream stream, byte[] bytes) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        copyStream(in, stream);
    }

    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] data = new byte[1024];
        int read;
        while ((read = in.read(data)) >= 0)
            out.write(data, 0, read);
        out.flush();
    }

    public static byte[] loadFile(String filename) throws IOException {
        return loadFile(new File(filename));
    }

    public static void storeFile(String filename, byte[] bytes) throws IOException {
        storeFile(new File(filename), bytes);
    }

    public static byte[] loadFile(File file) throws IOException {
        FileInputStream fis = null;
        try {
            return toByteArray(fis = new FileInputStream(file));
        }
        finally {
            try {
                if (fis != null)
                    fis.close();
            }
            catch (Exception e) {
                //ignore
            }
        }
    }

    public static void storeFile(File file, byte[] bytes) throws IOException {
        FileOutputStream fos = null;
        try {
            toStream(fos = new FileOutputStream(file), bytes);
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch (Exception e) {
            }
        }
    }
}
