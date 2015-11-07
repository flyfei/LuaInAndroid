--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/6
-- Time: 下午7:33
-- To change this template use File | Settings | File Templates.
--

-- Lua Toast(直接显示Toast)
function toast1(context, message, duration)
    mToast = luajava.bindClass("android.widget.Toast")
    --    mInstanceToast = luajava.newInstance("android.widget.Toast")
    mToast:makeText(context, message, duration):show()
end

--Toast的持续时间默认设为LENGTH_SHORT
function toast2(context, message)
    mToast = luajava.bindClass("android.widget.Toast")
    mInstanceToast = luajava.newInstance("android.widget.Toast", context)
    mToast:makeText(context, message, mInstanceToast.LENGTH_SHORT):show()
end

-- Lua Toast(回调调用Android方法显示Toast)
function toast3(className, context, message, duration)
    --     被调用的toast(Context context, String message, int duration)必须是Public属性
    className:toast(context, message, duration)
end

