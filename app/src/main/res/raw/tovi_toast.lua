--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/6
-- Time: 下午7:33
-- To change this template use File | Settings | File Templates.
--

-- Lua Toast(调用Android Toast)
function toast(context, message)
    --    mToast = luajava.newInstance("android.widget.Toast")
    MainActivity.toast(context, message)
end

