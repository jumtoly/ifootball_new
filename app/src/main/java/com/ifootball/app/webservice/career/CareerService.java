package com.ifootball.app.webservice.career;

import android.net.Uri;

import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.career.CareerBaseInfo;
import com.ifootball.app.webservice.BaseService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by liu.yao on 2015/12/23.
 */
public class CareerService extends BaseService {
    public CareerBaseInfo getCareerHomeVM() throws IOException, JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/Customer/GetCareerHomeVM");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = read(url);
        Type listType = new TypeToken<ResultData<CareerBaseInfo>>() {
        }.getType();
        ResultData<CareerBaseInfo> resultData = gson.fromJson(
                jsonString, listType);
        return resultData.getData();
    }
}
