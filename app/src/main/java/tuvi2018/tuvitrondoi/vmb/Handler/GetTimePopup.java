package tuvi2018.tuvitrondoi.vmb.Handler;

import android.os.AsyncTask;
import android.util.Log;

import tuvi2018.tuvitrondoi.vmb.Interface.ITimeReturn;

import org.json.JSONException;
import org.json.JSONObject;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.popup_API_URL;

/**
 * Created by Manh Dang on 01/08/2018.
 */

public class GetTimePopup extends AsyncTask {

    private String code;
    private String deviceID;
    private String time_install;
    private ITimeReturn listenner;

    private String URL;
    private String json;

    public GetTimePopup(String code, String deviceID, String time_install, ITimeReturn listenner) {
        this.code = code;
        this.deviceID = deviceID;
        this.time_install = time_install;
        this.listenner = listenner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        URL = popup_API_URL + "&code=" + code
                + "&deviceID=" + deviceID
                + "&time_install=" + time_install;
        Log.d("aaaaaaaaaa", URL);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        HttpHandler jsonParser = new HttpHandler();
        json = jsonParser.callService(URL, HttpHandler.GET);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);

                if (jsonObject != null) {
                    JSONObject obj = (JSONObject) jsonObject.get("config");

                    if (obj != null)
                        listenner.onTimeReturn(obj.getInt("time_start_show_popup"), obj.getInt("offset_time_show_popup"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
