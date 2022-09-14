package com.benben.carmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.benben.carmanager.MyApplication;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final String TAG = "jyw";
    private static Gson gson = new Gson();

    public static boolean isPhoneNumber(String phone) {
        String regex = "^((1[1-9][0-9]))\\d{8}$";
        if (phone.length() != 11) {
            System.out.println("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (isMatch) {
                System.out.println("您的手机号" + phone + "是正确格式@——@");
                return true;
            } else {
                System.out.println("您的手机号" + phone + "是错误格式！！！");
                return false;
            }
        }

    }

    /**
     * 压缩图片
     *
     * @param file
     * @param scale 缩放比例2的倍数
     * @return
     */
    private static File compressPhoto(File file, int scale) {
        String photo_path = MyApplication.getContext().getExternalCacheDir() + File.separator + "餐厅助手" + File.separator + "photos";
        File dir = new File(photo_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File targetFile = new File(dir, "photo.png");
        int degree = imgDegree(file.getAbsolutePath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        bitmap = rotateImage(bitmap, degree);
        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth() / scale, bitmap.getHeight() / scale, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        RectF rectF = new RectF(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight());
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return targetFile;
    }


    /**
     * 调整图片尺寸
     *
     * @param photoPath 图片的绝对路径
     * @return
     */
    public static Bitmap adjustPhotoDegress(String photoPath) {
        int degree = imgDegree(photoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        bitmap = rotateImage(bitmap, degree);
        return bitmap;
    }



    /**
     * 获取图片角度
     */
    public static int imgDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }

        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            //相应异常处理
        }
        return degree;
    }


    /**
     * 图片旋转
     *
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateImage(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }


    /**
     * bitmap转file
     *
     * @param bitmap
     * @return
     */
    public static File getFile(Bitmap bitmap) {


        String photo_path = Environment.getExternalStorageDirectory() + File.separator + "餐厅助手" + File.separator + "photos";
        File dir = new File(photo_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File targetFile = new File(dir, "photo.png");

        Bitmap resultBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        RectF rectF = new RectF(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight());
        canvas.drawBitmap(bitmap, null, rectF, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return targetFile;
    }




    /**
     * 将图片添加到系统相册 供其他软件使用
     *
     * @param originPhoto
     */
    public static void addPhotoToAlbum(Context context, File originPhoto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //10.0系统以上不支持动态扫描图片 建议直接将图片添加到图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), originPhoto.getAbsolutePath(), "photo.png", null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }

        } else {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", originPhoto);
            } else {
                uri = Uri.fromFile(originPhoto);
            }
            mediaScanIntent.setData(uri);
            context.sendBroadcast(mediaScanIntent);
        }

    }


    /**
     * 比较新版本号大吗
     *
     * @param newVersion
     * @return 如果新版本号大 返回true
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public static boolean compareVersionCodeLarger(Context context, String newVersion) throws PackageManager.NameNotFoundException {
        long versionCode = getCurrentVersionCode(context);
        Log.d(TAG, "compareVersionCodeLarger: 当前版本值 == " + versionCode);
        if (!TextUtils.isEmpty(newVersion)) {
            String oldVersionCode = versionCode + ".";
            String[] oldVer = oldVersionCode.split("\\.");
            String[] newVer = newVersion.split("\\.");
            boolean neiOver = false;
            for (int i = 0; i < newVer.length; i++) {
                if (!neiOver) {
                    for (int j = 0; j < oldVer.length; j++) {
                        int i1 = Integer.parseInt(newVer[i]);
                        int i2 = Integer.parseInt(oldVer[i]);
                        if (i1 > i2) {
                            return true;
                        }
                    }

                    neiOver = true;
                }

                int i1 = Integer.parseInt(newVer[i]);
                if (i != 0) {
                    if (i1 == 0) {

                    } else {
                        return true;
                    }
                }

            }
        }

        return false;
    }


    private static long getCurrentVersionCode(Context context) throws PackageManager.NameNotFoundException {
        long version = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                version = packageInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                version = packageInfo.getLongVersionCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return version;
    }

    public static Uri getFileUri(Context context, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        return uri;
    }

    public static Uri getTargetFileUri(Context context) {
        String photo_path = Environment.getExternalStorageDirectory() + File.separator + "餐厅助手" + File.separator + "photos";
        File dir = new File(photo_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File targetFile = new File(dir, "photo.png");
        return getFileUri(context, targetFile);
    }


    public static boolean isIDCardNum(String idNumber) {
        String regex = "^\\d{17}(\\d|X|x)$";
        if (idNumber.length() != 18) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(idNumber);
            boolean isMatch = m.matches();
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    /**
     * 车牌号的判断
     * @param number
     * @return
     */
    public static boolean isCarNumber(String number) {
        String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(number);
        boolean matches = m.matches();
        if (matches){
            return true;
        }
        return false;
    }
}