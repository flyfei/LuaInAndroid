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

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private LuaState mLuaState;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
    }

    private void init() {
        //init View
        mResult = (TextView) findViewById(R.id.result);

        //init lua
        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();

    }

    @Override
    public void onClick(View view) {
        mResult.setText("");

        switch (view.getId()) {
            case R.id.btn_bianliang:
                runStatement();
                break;
            case R.id.btn_open_setting:
                openSetting();
                break;
            case R.id.btn_function_result:
                getFunctionResult();
                break;
        }

    }

    /**
     * 执行Function，获取返回值
     */
    private void getFunctionResult() {
        //加载Lua文件内容
        mLuaState.LdoString(readRawStream(R.raw.tovi_get_function_result));
        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "getFunctionResult");

        //设置参数
        mLuaState.pushNumber(10);
        mLuaState.pushNumber(11);

        //执行Function
        mLuaState.call(2, 1);

        appendResultLine("传入'10','11',返回的结果:" + mLuaState.toString(-1));
    }

    /**
     * 运行Lua脚本文件，打开Setting
     */
    private void openSetting() {
        //加载Lua文件内容
        mLuaState.LdoString(readRawStream(R.raw.tovi_open_setting));
        //找到function方法
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "launchSetting");

        //传入参数（如果Funcation方法有参数）
        mLuaState.pushJavaObject(this);

        //执行Function
        /**
         * call(int nArgs, int nResults)<br>
         *     nArgs:几个参数
         *     nResults:几个返回值
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

    /**
     * 读取Raw文件内容
     *
     * @param resourceId
     * @return
     */
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