//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sky.pm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    protected static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            System.err.println("初始化失败，MessageDigest不支持MD5Util。");
            var1.printStackTrace();
        }

    }

    public Crypt() {
    }

    public static String encrypt(String key, String password) throws Exception {
        String a = key + "inankai";
        byte[] b = a.getBytes("UTF-8");
        String c = Base64.encode(b);
        key = c.substring(0, 8);
        return DES.encryptDES(password, key);
    }

    public static String decrypt(String key, String password) throws Exception {
        String a = key + "inankai";
        byte[] b = a.getBytes("UTF-8");
        String c = Base64.encode(b);
        key = c.substring(0, 8);
        return DES.decryptDES(password, key);
    }

    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        boolean numRead = false;

        int numRead1;
        while((numRead1 = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead1);
        }

        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    public static String getFileMD5String_old(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(MapMode.READ_ONLY, 0L, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for(int l = m; l < k; ++l) {
            appendHexPair(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        File file = new File("C:/12345.txt");
        String md5 = getFileMD5String(file);
        long end = System.currentTimeMillis();
//        MULog.i("log", "md5:" + md5 + " time:" + (end - begin) / 1000L + "s");
    }
}
