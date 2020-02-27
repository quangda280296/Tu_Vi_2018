package tuvi2018.tuvitrondoi.vmb.Config;

import android.content.Intent;
import android.graphics.Typeface;

import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Manh Dang on 01/05/2018.
 */

public class Config {
    public static String base_URL = "http://api.appsbeez.com/oracle/";
    public static String popup_API_URL = "http://gamemobileglobal.com/api/control-app.php?";
    public static String codeAccessAPI = "456";

    public static Typeface myNewFace;

    public static int time_start;
    public static int time_end;
    public static Intent intentMyService;

    public static InterstitialAd iad;
    public static int time_start_show_popup;
    public static int offset_time_show_popup;
}
