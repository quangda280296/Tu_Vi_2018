package tuvi2018.tuvitrondoi.vmb.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import tuvi2018.tuvitrondoi.vmb.R;

/**
 * Created by keban on 9/1/2017.
 */

public class Check {

    public static boolean checkInternetConnection(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, 1);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Utils.shortToast(context, context.getString(R.string.no_internet));
            return false;
        }

        if (!networkInfo.isConnected()) {
            Utils.shortToast(context, context.getString(R.string.no_internet));
            return false;
        }

        if (!networkInfo.isAvailable()) {
            Utils.shortToast(context, context.getString(R.string.no_internet));
            return false;
        }
        return true;
    }
}
