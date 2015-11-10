--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/7
-- Time: 下午5:01
-- To change this template use File | Settings | File Templates.
--

function setOnClick(context)
    --因为id是R.java里的一个静态类，所以引用Button的资源文件用下面的代码
    local id = luajava.bindClass("cn.tovi.luainandroid.R$id")
    mBtn = context:findViewById(id.btn_onclick)

    local listener = {
        onClick = function(view)
            toast(context, "Hello I'm back")
        end
    }
    local buttonProxy = luajava.createProxy("android.view.View$OnClickListener", listener)
    mBtn:setOnClickListener(buttonProxy)
end