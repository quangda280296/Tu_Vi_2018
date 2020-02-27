package tuvi2018.tuvitrondoi.vmb;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.crashlytics.android.Crashlytics;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;
import tuvi2018.tuvitrondoi.vmb.Service.ShowAdService;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_start;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_end;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class SplashActivity extends Activity {

    private ComponentName service;

    Thread wait = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        Calendar calendar = Calendar.getInstance();
        time_start = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        time_end = time_start;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        intentMyService = new Intent(this, ShowAdService.class);
        service = startService(intentMyService);

        myNewFace = Typeface.createFromAsset(getAssets(), "SanFranciscoDisplay-Light.otf");
        wait.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(intentMyService));
    }
}
