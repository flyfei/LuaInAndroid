--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/7
-- Time: 下午5:01
-- To change this template use File | Settings | File Templates.
--

--引入tovi_toast
--require "tovi_toast"

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

--Toast的持续时间默认设为LENGTH_SHORT
function toast(context, message)
    --    获取静态类Toast
    mToast = luajava.bindClass("android.widget.Toast")
    --    调用显示Toast Api (Toast.makeText(Context context, CharSequence text, @Duration int duration))
    mToast:makeText(context, message, mToast.LENGTH_SHORT):show()
end