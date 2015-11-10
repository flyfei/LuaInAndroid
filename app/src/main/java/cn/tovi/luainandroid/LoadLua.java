package cn.tovi.luainandroid;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class LoadLua {

    /**
     * Lua文件存储目录名称
     */
    private static final String LUA_ROOT_NAME = "lua";
    public static final String LUA_ROOT_PATH = LUA_ROOT_NAME + File.separator;


    /**
     * 加载所有AssetsLua文件
     *
     * @param context
     * @return
     */
    public static String readAssetsAllFile(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        String[] list = new String[0];
        try {
            list = assetManager.list(LUA_ROOT_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer luaString = new StringBuffer();
        for (String fileName : list) {
            try {
                luaString.append(readFile(assetManager.open(LUA_ROOT_PATH + fileName)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return luaString.toString();
    }


    /**
     * 加载所有Raw文件
     *
     * @param context
     * @return
     */
    public static String readRawAllFile(Context context) {

        StringBuffer luaString = new StringBuffer();
        List<Integer> resourceIds = getRawResourceIds();
        if (resourceIds.size() > 0) {
            Resources resources = context.getResources();
            for (Integer resourceId : resourceIds) {
                luaString.append(readFile(resources.openRawResource(resourceId)));
            }
        }
        return luaString.toString();
    }

    private static ArrayList<Integer> getRawResourceIds() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Field[] fields = R.raw.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                list.add(fields[i].getInt(R.raw.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fields = null;
        Log.e("raw文件目录", list.size() + "");
        return list;
    }

    private static String readFile(InputStream inputStream) {
        if (inputStream == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            Log.e("ReadStream", "读取文件流失败：" + e.toString());
            return null;
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
