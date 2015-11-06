--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/10/30
-- Time: 下午4:57
-- To change this template use File | Settings | File Templates.
--

-- 打开设置界面
function launchSetting(context)

    -- 该方法在华为手机上面崩溃

    intent = luajava.newInstance("android.content.Intent")
    c = luajava.newInstance("android.content.ComponentName", "com.android.settings", "com.android.settings.Settings")
    intent:setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
    intent:setComponent(c)
    context:startActivity(intent)
end

