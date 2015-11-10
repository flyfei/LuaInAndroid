--
-- Created by IntelliJ IDEA.
-- User: Tengfei Zhao
-- Email:zhaotengfei9@gmail.com
-- Date: 15/11/10
-- Time: 下午3:15
-- To change this template use File | Settings | File Templates.
--

function dataModel(dataModel)
    if dataModel ~= nil then

        -- 字符串拼接可以用 string.format("%s%s", string1, string2)
        return string.format("%s%s", dataModel.aString, dataModel:toString())
    else
        return "Is Null"
    end
end
