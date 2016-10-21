package com.sunnybear.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具
 * Created by guchenkai on 2015/12/8.
 */
public final class ImageUtils {

    /**
     * 将小图片x轴循环填充进imageView中
     *
     * @param imageView imageView
     * @param bitmap    目标图片
     */
    public static void fillXInImageView(Context context, ImageView imageView, Bitmap bitmap) {
        int screenWidth = DensityUtil.getScreenW(context);//屏幕宽度
        imageView.setImageBitmap(createRepeater(screenWidth, bitmap));
    }


    /**
     * 将图片在imageView中x轴循环填充需要的bitmap
     *
     * @param width 填充宽度
     * @param src   图片源
     * @return 新图片源
     */
    private static Bitmap createRepeater(int width, Bitmap src) {
        int count = (width + src.getWidth() - 1) / src.getWidth(); //计算出平铺填满所给width（宽度）最少需要的重复次数
        Bitmap bitmap = Bitmap.createBitmap(src.getWidth() * count, src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int idx = 0; idx < count; ++idx) {
            canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
        }
        return bitmap;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     *
     * @param filePath 图片路径
     * @param width    压缩后的宽
     * @param height   压缩后的高
     */
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将bitmap输出到sdCard中
     *
     * @param outPath 输出路径
     */
    public static void bitmapOutSdCard(Bitmap bitmap, String outPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath, OnImageSaveCallback callback) {
        int mSrollViewHeight = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                child.setBackgroundResource(R.color.white);
                mSrollViewHeight += child.getHeight();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), mSrollViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取滚动屏画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutScrollViewToImage(ScrollView view, String savePath) {
        cutOutScrollViewToImage(view, savePath, null);
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath, OnImageSaveCallback callback) {
//        int mSrollViewHeight = 0;
//        for (int i = 0; i < view.getChildCount(); i++) {
//            View child = view.getChildAt(i);
//            if (child instanceof LinearLayout || child instanceof RelativeLayout) {
//                child.setBackgroundResource(R.color.white);
//                mSrollViewHeight += child.getHeight();
//            }
//        }
        view.setBackgroundResource(R.color.white);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(savePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Logger.d(isSuccess ? "保存图片成功" : "保存图片成功");
            if (callback != null) callback.onImageSave(isSuccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取图片保存成功的回调
     */
    public interface OnImageSaveCallback {

        void onImageSave(boolean isSuccess);
    }

    /**
     * 截取画面保存到图片
     *
     * @param view     view源
     * @param savePath 保存路径
     */
    public static void cutOutViewToImage(View view, String savePath) {
        cutOutViewToImage(view, savePath, null);
    }

    /**
     * 截取view画面保存至bitmap
     *
     * @param view view源
     */
    public static Bitmap cutOutViewToBitmap(View view) {
        return Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * file转bitmap
     *
     * @param file 图片文件
     * @return bitmap
     */
    public static Bitmap fileToBitmap(File file) {
        return BitmapFactory.decodeFile(file.getPath());
    }

    /**
     * file转bitmap
     *
     * @param filePath 图片文件路径
     * @return bitmap
     */
    public static Bitmap fileToBitmap(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * 获得相片拍摄的GPS坐标信息
     *
     * @param photoPath 相片文件路径
     */
    public static LatLng getPhotoLocation(String photoPath) {
        LatLng latLng = new LatLng();
        try {
            ExifInterface exif = new ExifInterface(photoPath);
            float lat = converRationalLatLonToFloat(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                    , exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
            latLng.setLat(lat);
            float lng = converRationalLatLonToFloat(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                    , exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
            latLng.setLng(lng);
            return latLng;
        } catch (IOException e) {
            Logger.e(e);
            return null;
        }
    }

    /**
     * 获得相片拍摄的GPS坐标信息
     *
     * @param photoFile 相片文件
     */
    public static LatLng getPhotoLocation(File photoFile) {
        return getPhotoLocation(photoFile.getAbsoluteFile());
    }

    /**
     * 将GPS时分秒坐标转换为标准GPS坐标
     *
     * @param rational 时分秒坐标
     * @param ref      坐标参照方向
     */
    private static float converRationalLatLonToFloat(String rational, String ref) {
        if (StringUtils.isEmpty(rational) || StringUtils.isEmpty(ref))
            return (float) 0.0;
        String[] parts = rational.split(",");
        String[] pair = null;

        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());
        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());
        pair = parts[2].split("/");
        double second = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (second / 3600.0);
        if (ref.equals("S") || ref.equals("W"))
            return (float) -result;
        return (float) result;
    }

    /**
     * 图片经纬度信息
     */
    public static class LatLng {
        private float lat;
        private float lng;

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "LatLng{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }
}
