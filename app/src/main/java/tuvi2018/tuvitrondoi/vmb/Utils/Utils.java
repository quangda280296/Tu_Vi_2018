package tuvi2018.tuvitrondoi.vmb.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;
import java.util.Random;

import tuvi2018.tuvitrondoi.vmb.R;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.iad;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.offset_time_show_popup;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_end;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_start;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.time_start_show_popup;

/**
 * Created by keban on 10/13/2017.
 */

public class Utils {

    public static void shortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void shortSnackbar(Activity activity, String text) {
        Snackbar.make(activity.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    public static void longSnackbar(Activity activity, String text) {
        Snackbar.make(activity.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show();
    }

    public static int rand(int min, int max) {
        try {
            Random rn = new Random();
            int range = max - min + 1;
            int randomNum = min + rn.nextInt(range);
            return randomNum;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void showAd(final AdView banner) {
        // load advertisement
        final AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);
        banner.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                banner.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                banner.loadAd(adRequest);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                banner.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
    }

    public static void showAdPopup() {
        Calendar calendar = Calendar.getInstance();
        int end = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);

        Log.d("tttttt", "time_start=" + (end - time_start) + " / time_offset=" + (end - time_end));

        if (end - time_start >= time_start_show_popup)
            if (end - time_end >= offset_time_show_popup) {
                calendar = Calendar.getInstance();
                time_end = ((calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.DAY_OF_YEAR)) * 24
                        + calendar.get(Calendar.HOUR_OF_DAY)) * 3600 + calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);

                iad.show();
                AdRequest adRequest = new AdRequest.Builder().build();

                // Load ads into Interstitial Ads
                iad.loadAd(adRequest);
            }
    }

    public static void showYearDialog(final Context context, final TextView label) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.show();

        final Calendar mCalendar = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.date_picker);
        Button date_time_set = (Button) dialog.findViewById(R.id.date_time_set);
        datePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), null);

        LinearLayout ll = (LinearLayout) datePicker.getChildAt(0);
        LinearLayout ll1 = (LinearLayout) ll.getChildAt(0);

        ll1.getChildAt(0).setVisibility(View.GONE);
        ll1.getChildAt(1).setVisibility(View.GONE);

        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                label.setText(datePicker.getYear() + "");
            }
        });
    }
}
