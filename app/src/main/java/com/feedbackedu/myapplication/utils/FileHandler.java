package com.feedbackedu.myapplication.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler {
    static String AppPath = Environment.getExternalStorageDirectory() + "/UserApp/";

    public static void createAppDirs() {
        File f = new File(AppPath);
        f.mkdirs();
    }

    public static String savePicture(Bitmap pic, String name) {
        createAppDirs();
        File f = new File(AppPath + name + ".png");
        if (f.exists()) f.delete();
        try {
            FileOutputStream fos = new FileOutputStream(f);
            pic.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
