package tuvi2018.tuvitrondoi.vmb.Handler;

import android.os.AsyncTask;
import android.util.Log;

import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;

import org.json.JSONException;
import org.json.JSONObject;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.base_URL;

/**
 * Created by Manh Dang on 01/05/2018.
 */

public class GetResult extends AsyncTask {

    private String URL;
    private String json;

    private String extra_url;
    private IDataListenner listenner;

    public GetResult(String extra_url, IDataListenner listenner) {
        this.extra_url = extra_url;
        this.listenner = listenner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        URL = base_URL + extra_url + "&p=vn.tracuutuvi.phongthuy";
        Log.d("eeeeeeeeee", URL);
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

                if (jsonObject != null)
                    listenner.onDataReturn(jsonObject.getString("result"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
