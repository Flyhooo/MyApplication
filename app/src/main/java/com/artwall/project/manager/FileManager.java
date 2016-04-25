package com.artwall.project.manager;

import android.os.Environment;

import com.artwall.project.util.ImageLoaderUtil;

import java.io.File;

/**
 * Created by 95 on 2016/4/20.
 */
public class FileManager {

    private static String homeDir;
    private static String logDir;
    private static String imgDownloadDir;

    /**
     * get app's home directory
     *
     * @return
     */
    public static String getHomeDir() {
        if (homeDir == null) {
            if (isSdExist()) {
                homeDir = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/Artwall";
            } else {
                homeDir = Environment.getRootDirectory()
                        .getAbsolutePath() + "/Artwall";
            }
        } else {
            File file = new File(homeDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return homeDir;
    }

    /**
     * get image cache directory
     *
     * @return
     */
    public static String getImgCacheDir() {
        return getHomeDir() + ImageLoaderUtil.TEMP_IMG_CACHE_FOLDER;
    }


    /**
     * get downloaded images' directory
     *
     * @return
     */
    public static String getImgDownloadDir() {
        imgDownloadDir = getHomeDir() + "/download";
        File file = new File(imgDownloadDir);
        file.mkdirs();
        return imgDownloadDir;
    }

    /**
     * check whether the sd card exists
     *
     * @return
     */
    public static boolean isSdExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}