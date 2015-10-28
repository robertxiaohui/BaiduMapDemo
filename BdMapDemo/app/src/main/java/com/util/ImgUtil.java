package com.util;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

/**
 * Created by zjm on 2014/11/18.
 */
public class ImgUtil {

    public static String urlPrefix = "http://7xllly.com1.z0.glb.clouddn.com/";

    public static String urlSizePostfix = "?imageView2/0/w/960/h/720";

    public static void displayImage(SimpleDraweeView draweeView,String uuid,String urlDefault) {
        if (!TextUtils.isEmpty(uuid)) {
            draweeView.setImageURI(getImageUrl(uuid));
        } else if (!TextUtils.isEmpty(uuid)) {
            draweeView.setImageURI(Uri.parse(urlDefault));
        }
    }
//    public static void displayHeadImg(SimpleDraweeView draweeView,String uuid) {
//        Uri uri = new Uri.Builder()
//                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
//                .path(String.valueOf( R.mipmap.head_defult))
//                .build();
//        displayImage(draweeView,getImageUrl(uuid),uri);
//    }
//
//    public static void displayRoomImg(SimpleDraweeView draweeView,String uuid) {
//        Uri uri = new Uri.Builder()
//                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
//                .path(String.valueOf( R.mipmap.room_default))
//                .build();
//        displayImage(draweeView,getImageUrl(uuid),uri);
//    }
    public static void displayImg(SimpleDraweeView draweeView,String uuid,int default_res) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(default_res))
                .build();
        displayImage(draweeView,getImageUrl(uuid),uri);
    }

    public static void displayImage(SimpleDraweeView draweeView,Uri uri,Uri urlDefault) {
        if (uri != null) {
            draweeView.setImageURI(uri);
        } else {
            draweeView.setImageURI(urlDefault);
        }
    }


    public static Uri getImageUrl(String uuid) {
        if (!TextUtils.isEmpty(uuid))
            return Uri.parse(urlPrefix + uuid+ urlSizePostfix);
        else
            return null;
    }

    public static String getImageId(String url) {
        String id = url.replace(urlPrefix, "");
        if(id != null && id.contains("/")) {
            id = id.substring(id.lastIndexOf("/"));
        }

        return id;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > width) {
            int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(heightRatio,widthRatio);
        } else {
            int heightRatio = Math.round((float) height
                    / (float) reqWidth);
            int widthRatio = Math.round((float) width / (float) reqHeight);
            inSampleSize = Math.max(heightRatio,widthRatio);
        }
        inSampleSize = Math.max(inSampleSize,1);
        return inSampleSize;
    }

    public static class CompressInfo {
        public long fileSize;
        public String path;
        public String md5;
        public int width;
        public int height;
    }

    public static CompressInfo computeInfo(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                CompressInfo info = new CompressInfo();
                info.md5 = f.getName();
                info.path = filePath;
                info.height = options.outHeight;
                info.width = options.outWidth;
                return info;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
