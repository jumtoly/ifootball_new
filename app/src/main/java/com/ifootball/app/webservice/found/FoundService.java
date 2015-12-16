package com.ifootball.app.webservice.found;

import android.net.Uri;

import com.ifootball.app.common.Common;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.found.FoundRespone;
import com.ifootball.app.entity.found.FoundRequest;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.webservice.BaseService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by liu.yao on 2015/12/16.
 */
public class FoundService extends BaseService {
    private static float latitude;// 纬度
    private static float longitude;// 经度

    static {
        latitude = Float.parseFloat(MySharedCache.get(Common.CURRENT_LATITUDE.name(), "0"));
        longitude = Float.parseFloat(MySharedCache.get(Common.CURRENT_LONGITUDE.name(), "0"));
    }

    public FoundRespone getNearByCourtData(int pageNumber, int pageSize, int type) throws IOException, JsonParseException, ServiceException {
        FoundRequest foundRequest = new FoundRequest(latitude, longitude, type, pageNumber, pageSize);
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/Discovery/Index");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = create(url, gson.toJson(foundRequest));
        Type listType = new TypeToken<ResultData<FoundRespone>>() {
        }.getType();
        ResultData<FoundRespone> resultData = gson.fromJson(
                jsonString, listType);
        return resultData.getData();
    }
}
