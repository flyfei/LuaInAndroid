package cn.tovi.luainandroid;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class ReadUtil {

    /**
     * 读取Assets文件内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readFromAssets(Context context, String fileName) {
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));

            byteArrayOutputStream = new ByteArrayOutputStream();
            int i = inputReader.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputReader.read();
            }
            return byteArrayOutputStream.toString();


//
//
//如果用这个的话，Lua里面如果有注释，报错
//
//            bufReader = new BufferedReader(inputReader);
//            String line = "";
//            String Result = "";
//            while ((line = bufReader.readLine()) != null)
//                Result += line;
//            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufReader != null)
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (inputReader != null)
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (byteArrayOutputStream != null)
                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 读取Raw文件内容
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static String readFromRaw(Context context, int resourceId) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = context.getResources().openRawResource(resourceId);
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
