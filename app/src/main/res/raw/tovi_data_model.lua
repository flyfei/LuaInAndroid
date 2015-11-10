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
        return dataModel:toString()
    else
        return "Is Null"
    end
end
