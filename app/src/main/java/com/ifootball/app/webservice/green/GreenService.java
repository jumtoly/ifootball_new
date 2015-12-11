package com.ifootball.app.webservice.green;

import android.net.Uri;

import com.ifootball.app.entity.AreaInfo;
import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.VenueDetailInfo;
import com.ifootball.app.entity.VenueSearchCriteria;
import com.ifootball.app.entity.VenueSearchResultInfo;
import com.ifootball.app.webservice.BaseService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class GreenService extends BaseService {
    public VenueSearchResultInfo search(int pageNumber, int pageSize, int citySysNo, String districtSysno, String category) throws IOException, JsonParseException, ServiceException {
        VenueSearchCriteria criteria = new VenueSearchCriteria(pageNumber, pageSize, citySysNo, districtSysno, category);
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/venue/query");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = create(url, gson.toJson(criteria));
        Type listType = new TypeToken<ResultData<VenueSearchResultInfo>>() {
        }.getType();
        ResultData<VenueSearchResultInfo> resultData = gson.fromJson(
                jsonString, listType);
        return resultData.getData();
    }

    public VenueDetailInfo LoadVenue(int id) throws IOException,
            JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/venue/LoadVenue");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = read(url + "?id=" + id);
        Type listType = new TypeToken<ResultData<VenueDetailInfo>>() {
        }.getType();
        ResultData<VenueDetailInfo> resultData = gson.fromJson(jsonString,
                listType);
        return resultData.getData();
    }

    public List<AreaInfo> GetDistrictByCity(int citysysno) throws IOException,
            JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/common/GetDistrictByCity");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = read(url + "?id=" + citysysno);
        Type listType = new TypeToken<ResultData<List<AreaInfo>>>() {
        }.getType();
        ResultData<List<AreaInfo>> resultData = gson.fromJson(jsonString, listType);
        return resultData.getData();
    }

    public ResultData<String> sendValidateCode(String cellphone)
            throws IOException, JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/common/AjaxSendValidateCellphone");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = read(url + "?id=" + cellphone);
        Type listType = new TypeToken<ResultData<CustomerInfo>>() {
        }.getType();
        ResultData<String> resultData = gson.fromJson(jsonString, listType);
        return resultData;
    }


}
