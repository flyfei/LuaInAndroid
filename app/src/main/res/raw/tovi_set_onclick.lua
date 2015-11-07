--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/7
-- Time: 下午5:01
-- To change this template use File | Settings | File Templates.
--

--improt 'tovi_toast'

button_cb = {}
function button_cb.onClick()
--    tovi_toast.toast2()
end

--因为id是R.java里的一个静态类，所以引用Button的资源文件用下面的代码
local id = luajava.bindClass("cn.tovi.luainandroid.R$id")
local launch = activity:findViewById(id.btn_onclick)

buttonProxy = luajava.createProxy("android.view.View$OnClickListener",button_cb)
launch:setOnClickListener(buttonProxy)