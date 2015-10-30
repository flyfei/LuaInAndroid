--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/10/30
-- Time: 下午4:57
-- To change this template use File | Settings | File Templates.
--

function launchSetting(context)
    print("todo---------------------------launchSetting")
    intent = luajava.newInstance("android.content.Intent")
    c = luajava.newInstance("android.content.ComponentName","com.android.settings", "com.android.settings.Settings")
    intent:setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
    intent:setComponent(c)
    context:startActivity(intent)
end

