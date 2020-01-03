package com.ssm.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    public static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    public static String generateThumbnail(CommonsMultipartFile thumbnail,String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr+realFileName+extension;
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(thumbnail.getInputStream()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.png")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch(IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     *
     * 生成随机文件名,当前年月日小时分钟秒+五位随机数
     * @return
     */
    private static String getRandomFileName(){
        //获取随机的五位数
        int rannum = r.nextInt(89999+10000);
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+rannum;
    }

    /**
     *
     * 获取输入文件流的扩展名
     * @param commonsMultipartFile
     * @return
     */
    private static String getFileExtension(CommonsMultipartFile commonsMultipartFile) {
        String originalFileName = commonsMultipartFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录,即/home/work/xiangce/xxx.jpg,
     * 那么 home work xiangce 这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr){
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }

    }

    /**
     * storePath是文件路径还是目录的路径，
     * 如果storePath是文件路径则删除该文件，
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File file[] = fileOrPath.listFiles();
                for (int i=0;i<file.length;i++){
                    file[i].delete();
                }
            }
            fileOrPath.delete();
        }

    }

}