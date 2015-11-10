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

    settings = luajava.bindClass("android.provider.Settings")
    intent = luajava.newInstance("android.content.Intent", settings.ACTION_SETTINGS)
    intent:setFlags(intent.FLAG_ACTIVITY_NEW_TASK)
    context:startActivity(intent)

    --    Intent intent = new Intent(Settings.ACTION_SETTINGS);
    --    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    --    startActivity(intent);
end

