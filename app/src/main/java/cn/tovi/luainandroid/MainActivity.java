package cn.tovi.luainandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private LuaState mLuaState;
    private TextView mState;
    private TextView mResult;

    private List<String> nameLists = new ArrayList<String>();
    private int location = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
    }

    private void init() {
        //init View
        mResult = (TextView) findViewById(R.id.result);
        mState = (TextView) findViewById(R.id.state);

        //init Data
        nameLists.add("Lua变量");
        nameLists.add("运行 .Lua文件");
//        nameLists.add("定义一个Lua变量");
        location = 0;
        mState.setText(nameLists.get(location));


        //init lua
        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();


        onClick(null);
    }

    private void setNextLocation() {
        if (location + 1 > nameLists.size() - 1)
            location = 0;
        else
            location++;
    }

    @Override
    public void onClick(View view) {
        mState.setText(nameLists.get(location));
        mResult.setText("");

        switch (location) {
            case 0:
                runStatement();
                break;
            case 1:
                runLuaFile();
                break;
        }

        setNextLocation();
    }

    /**
     * 运行Lua脚本文件
     */
    private void runLuaFile() {
        //加载Lua文件内容
        mLuaState.LdoString(readRawStream(R.raw.tovi_open_setting));
        //找到function方法
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "launchSetting");

//        mLuaState.pushJavaObject(getApplicationContext());
//        mLuaState.pushJavaObject();
        //传入参数（如果Funcation方法有参数）
        mLuaState.pushJavaObject(this);

        //执行Function
        /**
         * call(int nArgs, int nResults)<br>
         *     nArgs:几个参数
         *     nResults:几个返回值?返回值存放的位置？
         */
        mLuaState.call(1, 0);
    }

    /**
     * Lua变量
     */
    private void runStatement() {

        appendResultLine("创建变量Name、取值");
        //创建Name变量
        mLuaState.LdoString("name = 'Tovi '");
        //获取值
        mLuaState.getGlobal("name");
        //输出值
        appendResultLine("name", mLuaState.toString(-1));
        appendResultLine("");//空一行


//        appendResultLine("创建空变量/赋值/取值");
//        mLuaState.LdoString("Age");
//        mLuaState.pushInteger(1);
//        mLuaState.getGlobal("Age");
//        appendResultLine("Age", mLuaState.toString(-1));
//        appendResultLine("");//空一行

        appendResultLine("判断变量属性");
        //定义变量testType
        mLuaState.LdoString("testType = true");
        //获取值
        mLuaState.getGlobal("testType");
        //isBoolean 判断值类型
        appendResultLine("'testType' is boolean", mLuaState.isBoolean(-1));
    }

    private String readRawStream(int resourceId) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = getResources().openRawResource(resourceId);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            Log.e("ReadStream", "读取文件流失败");
            return "";
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

    private void appendResultLine(String key, Object o) {
        mResult.append(key + ":" + o + "\n");
    }

    private void appendResultLine(String s) {
        mResult.append(s + "\n");
    }
}