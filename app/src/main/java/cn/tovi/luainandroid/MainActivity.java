package cn.tovi.luainandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.util.ArrayList;
import java.util.HashMap;

import cn.tovi.luainandroid.ModelTest.DataModel;
import cn.tovi.luainandroid.ModelTest.Person;

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
            case R.id.btn_function_toast1:
                luaToast1();
                break;
            case R.id.btn_function_toast2:
                luaToast2();
                break;
            case R.id.btn_function_toast3:
                luaToast3();
                break;
            case R.id.btn_function_setOnClick:
                luaSetOnClick();
                break;
            case R.id.btn_onclick:
                toast("Hello I'm Coming");
                break;
            case R.id.btn_function_data_model:
                luaDataModel();
                break;
        }

    }

    private void luaDataModel() {
        DataModel dataModel = new DataModel();
        dataModel.aDouble = 1.00;
        dataModel.aFloat = 0.1f;
        dataModel.aLong = 10000;
        dataModel.anInt = 100;
        dataModel.aObject = MainActivity.this;
        dataModel.aString = "Hello World";
        dataModel.aStrings = new String[1];
        dataModel.aStrings[0] = "Tovi";
        dataModel.listString = new ArrayList<String>();
        dataModel.listString.add("Tovi List");
        dataModel.maps = new HashMap<String, String>();
        dataModel.maps.put("name", "Tovi");

        Person person = new Person();
        person.name = "Tovi";
        person.age = 1;

        dataModel.aPerson = person;


        //加载Lua
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_data_model));

        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "dataModel");

        //传递参数
        mLuaState.pushJavaObject(dataModel);

        //执行
        mLuaState.call(1, 1);

        appendResultLine("收到的结果:" + mLuaState.toString(-1));

    }

    private void luaSetOnClick() {

        /**
         * 测试发现，如果一个lua引用另外的lua，运行Android程序的时候，会报异常。如果将他们放到一个Lua中，不会报错，所以，将相互引用的Lua都加载起来。
         */
        String info = ReadUtil.readFromAssets(this, LoadLua.LUA_ROOT_PATH + "tovi_set_onclick.lua");
        info += ReadUtil.readFromAssets(this, LoadLua.LUA_ROOT_PATH + "tovi_toast.lua");

        //加载Lua内容
        mLuaState.LdoString(info);
//        mLuaState.LdoString(LoadLua.readAssetsAllFile(this));
//        mLuaState.LdoString(LoadLua.readRawAllFile(this));

        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "setOnClick");

        //设置参数
        mLuaState.pushJavaObject(this);

        mLuaState.call(1, 0);

    }

    /**
     * Toast1
     */
    private void luaToast1() {
        String message = "测试Test1";

        //加载Lua文件内容
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_toast));

        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "toast1");

        //设置参数
        mLuaState.pushJavaObject(this);
        mLuaState.pushString(message);
        mLuaState.pushNumber(Toast.LENGTH_SHORT);

        //执行Function
        mLuaState.call(3, 0);
    }

    /**
     * Toast2
     */
    private void luaToast2() {
        String message = "测试Test2";

        //加载Lua文件内容
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_toast));

        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "toast2");

        //设置参数
        mLuaState.pushJavaObject(this);
        mLuaState.pushString(message);
        mLuaState.pushNumber(Toast.LENGTH_SHORT);

        //执行Function
        mLuaState.call(3, 0);
    }

    /**
     * Toast3
     */
    private void luaToast3() {
        String message = "测试Test3";
        //加载Lua文件内容
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_toast));

        //获取方法名
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "toast3");

        //设置参数
        mLuaState.pushJavaObject(this);
        mLuaState.pushJavaObject(this);
        mLuaState.pushString(message);
        mLuaState.pushNumber(Toast.LENGTH_SHORT);

        //执行Function
        mLuaState.call(4, 0);
    }

    /**
     * 执行Function，获取返回值
     */
    private void getFunctionResult() {
        //加载Lua文件内容
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_get_function_result));
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
        mLuaState.LdoString(ReadUtil.readFromRaw(this, R.raw.tovi_open_setting));
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
        /**
         * 把指定的索引处的的 Lua 值转换为一个 C 中的 boolean 值（ 0 或是 1 ）。
         * 和 Lua 中做的所有测试一样， lua_toboolean 会把任何 不同于 false 和 nil 的值当作 1 返回； 否则就返回 0 。
         * 如果用一个无效索引去调用也会返回 0 。
         * （如果你想只接收真正的 boolean 值，就需要使用 lua_isboolean 来测试值的类型。）
         */
        appendResultLine("'testType' is boolean", mLuaState.isBoolean(-1));


        /**
         * 另一种取值,是对getGlobal方法的封装
         */
//        LuaObject obj = mLuaState.getLuaObject("testType"); // 取得参数，以LuaObject类返回到java中
//        System.out.println("result: " + obj.toString());
    }


    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    private void appendResultLine(String key, Object o) {
        mResult.append(key + ":" + o + "\n");
    }

    private void appendResultLine(String s) {
        mResult.append(s + "\n");
    }
}