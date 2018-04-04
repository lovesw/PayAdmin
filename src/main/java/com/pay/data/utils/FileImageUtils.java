package com.pay.data.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import com.jfinal.upload.UploadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @createTime: 2018/1/2
 * @author: HingLo
 * @description: 图片上传，读取的工具类
 */
@Slf4j
public class FileImageUtils {

    private static final String ROOT = rootPath();
    private static final Map<Integer, String> LOCATION = initLocation();


    /***
     * 根据不同的操作系统返回不同的文件目录
     * @return 文件路径
     */
    private static String rootPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return "E:/root/pay";
        } else {
            return "/root/pay";
        }
    }


    /**
     * 初始化文件位置
     *
     * @return 文件初始化对象的Map集合
     */
    private static Map<Integer, String> initLocation() {

        Map<Integer, String> map = new HashMap<>(2);
        map.put(FieldUtils.FILE_PDF, rootPath() + "/pdf/");
        map.put(FieldUtils.HEADER, rootPath() + "/header/");
        map.put(FieldUtils.CARD, rootPath() + "/card/");
        map.put(FieldUtils.INVOICE, rootPath() + "/invoice/");
        return map;
    }

    /**
     * 获取文件的后缀
     *
     * @param fileName 指定的文件名称
     * @return 返回文件后缀
     */
    private static String getSuffixUtils(String fileName) {
        if (StrUtil.isNotBlank(fileName)) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    /**
     * 检测文件是否是指定文件的后缀
     *
     * @param file   文件对象
     * @param suffix 指定的后缀
     * @return 操作结果
     */
    public static boolean isSpecifySuffixUtils(UploadFile file, String suffix) {
        return suffix.equalsIgnoreCase(getSuffixUtils(file.getOriginalFileName()));
    }

    /**
     * 读取指定名称的PDF文件
     *
     * @param pdfName PDF文件的名称
     * @return 返回读取的PDF文件对象
     */
    public static File readPDF(String pdfName) {
        String path = LOCATION.get(FieldUtils.FILE_PDF) + pdfName;
        return new File(path);
    }

    /**
     * 保存PDF文件到本地
     *
     * @param file     文件对象
     * @param fileName 文件的名称
     * @return 保存结果
     */
    public static boolean savePDF(UploadFile file, String fileName) {
        String path = LOCATION.get(FieldUtils.FILE_PDF) + fileName;
        File file1 = file.getFile();
        try {
            FileUtil.writeBytes(FileUtil.readBytes(file1), path);
            return true;
        } catch (IORuntimeException e) {
            return false;
        }
    }


    /**
     * 读取图片文件，将转为字节数组
     *
     * @param fileName 文件名称
     * @param type     图片类型
     * @return 返回字节数组
     */
    public static byte[] readImageUtils(String fileName, int type) {
        String path = readUrlUtils(type) + fileName;
        File file = new File(path);
        if (!file.isFile()) {
            //如果文件不是文件，就直接就设置为默认图片
            file = new File(ROOT + "/default.png");
        }
        try {
            return FileUtil.readBytes(file);
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存文件
     *
     * @param file     文件流
     * @param fileName 文件名称
     * @param type     类型，根据类型可以选择保存到不同的文件夹位置
     * @return 文件保存是否成功
     */
    public static boolean saveImageUtils(UploadFile file, String fileName, int type) {
        //文件名称是空白或者是null的时候就直接返回
        if (StrUtil.isBlank(fileName)) {
            return false;
        }

        String path = readUrlUtils(type);
        path += fileName;
        try {
            File file1 = file.getFile();
            FileUtil.writeBytes(FileUtil.readBytes(file1), path);
            return true;
        } catch (IORuntimeException e) {
            return false;
        }
    }

    /**
     * 根据传入的类型来确定文件的位置
     *
     * @param type 传递图片类型
     * @return 返回本地位置
     */
    private static String readUrlUtils(int type) {
        return LOCATION.get(type);
    }


    /**
     * 检测后缀是是否是一个图片格式
     *
     * @param fileName 文件名称
     * @return 是否是图片格式
     */
    public static boolean iconUtils(String fileName) {
        String suffix = FileImageUtils.getSuffixUtils(fileName);
        String[] s = {".PNG", ".JPG", ".GIF", ".JPEG"};
        suffix = suffix.toUpperCase();
        for (String ss : s) {
            if (suffix.equals(ss)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除指定的文件
     *
     * @param fileName 文件名称
     * @param type     文件类型
     * @return 返回是否删除成功
     */
    public static boolean deleteFileUtils(String fileName, int type) {
        //文件名称是空白或者是null的时候就直接返回
        if (StrUtil.isBlank(fileName)) {
            return false;
        }
        String path = readUrlUtils(type);
        path += fileName;
        try {
            FileUtil.del(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
