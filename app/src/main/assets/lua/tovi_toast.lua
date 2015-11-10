--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/6
-- Time: 下午7:33
-- To change this template use File | Settings | File Templates.
--

--Toast的持续时间默认设为LENGTH_SHORT
function toast(context, message)
    --    获取静态类Toast
    mToast = luajava.bindClass("android.widget.Toast")
    --    调用显示Toast Api (Toast.makeText(Context context, CharSequence text, @Duration int duration))
    mToast:makeText(context, message, mToast.LENGTH_SHORT):show()
end
