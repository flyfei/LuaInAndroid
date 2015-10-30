package cn.tovi.luainandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private LuaState mluaState;
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
        nameLists.add("定义一个Lua变量");
//        nameLists.add("定义一个Lua变量");
        location = 0;
        mState.setText(nameLists.get(location));


        //init lua
        mluaState = LuaStateFactory.newLuaState();
        mluaState.openLibs();


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
        }

        setNextLocation();
    }

    /**
     * 运行Lua脚本文件
     */
    private void runFile() {

    }

    /**
     * Lua变量
     */
    private void runStatement() {

        mluaState.LdoString("name = 'Tovi '");
        mluaState.getGlobal("name");
        appendResult("name", mluaState.toString(-1));

        mluaState.LdoString("true");
        mluaState.isBoolean(-1);
        appendResult("'true' is boolean", mluaState.isBoolean(-1));
    }

    private void appendResult(String key, Object o) {
        mResult.append(key + ":" + o + "\n");
    }
}