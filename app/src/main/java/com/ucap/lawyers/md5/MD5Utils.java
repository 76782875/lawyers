package com.ucap.lawyers.md5;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/5/16.
 * MD5 加密
 */
public class MD5Utils {
    /**
     * MD5 加密字符串
     *
     * @param password 密码
     * @return 返回加密后的16进制字符串
     */
    public static String encryption(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1)
                    hexString = "0" + hexString;
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密文件
     *
     * @param apkPath
     * @return
     */
    public static String encryptionFile(String apkPath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream is = new FileInputStream(new File(apkPath));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                md.update(bytes, 0, len);//更新加密文件
            }
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1)
                    hexString = "0" + hexString;
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
