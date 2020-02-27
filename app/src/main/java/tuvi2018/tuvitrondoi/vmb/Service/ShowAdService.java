package tuvi2018.tuvitrondoi.vmb.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Date;

import tuvi2018.tuvitrondoi.vmb.Handler.GetTimePopup;
import tuvi2018.tuvitrondoi.vmb.Interface.ITimeReturn;
import tuvi2018.tuvitrondoi.vmb.R;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.codeAccessAPI;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.iad;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.offset_time_show_popup;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_start_show_popup;

/**
 * Created by Manh Dang on 01/08/2018.
 */

public class ShowAdService extends Service implements ITimeReturn {

    private String idDevice;
    private String installTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        iad = new InterstitialAd(this);

        // set the ad unit ID
        iad.setAdUnitId(getString(R.string.popup_ads));

        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
        iad.loadAd(adRequest);

        idDevice = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        PackageManager pm = getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("tuvi2018.tuvitrondoi.vmb", PackageManager.GET_PERMISSIONS);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            installTime = dateFormat.format(new Date(packageInfo.firstInstallTime));
            installTime = installTime.replaceAll("\\s", "");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        new GetTimePopup(codeAccessAPI, idDevice, installTime, ShowAdService.this).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTimeReturn(int time_start, final int offset_time) {
        time_start_show_popup = time_start;
        offset_time_show_popup = offset_time;

        Log.d("ssssssssss", "start=" + time_start_show_popup + " offset=" + offset_time_show_popup);
    }
}
