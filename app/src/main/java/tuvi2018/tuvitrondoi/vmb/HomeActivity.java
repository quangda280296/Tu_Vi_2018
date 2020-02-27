package tuvi2018.tuvitrondoi.vmb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int count;
    private TextView lbl_title;
    private AdView banner;

    private LinearLayout item_1;
    private LinearLayout item_2;
    private LinearLayout item_3;
    private LinearLayout item_4;
    private LinearLayout item_5;
    private LinearLayout item_6;
    private LinearLayout item_7;
    private LinearLayout item_8;
    private LinearLayout item_9;
    private LinearLayout item_10;
    private LinearLayout item_11;

    private TextView txt_item_1;
    private TextView txt_item_2;
    private TextView txt_item_3;
    private TextView txt_item_4;
    private TextView txt_item_5;
    private TextView txt_item_6;
    private TextView txt_item_7;
    private TextView txt_item_8;
    private TextView txt_item_9;
    private TextView txt_item_10;
    private TextView txt_item_11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        count = 0;

        item_1 = (LinearLayout) findViewById(R.id.item_1);
        item_2 = (LinearLayout) findViewById(R.id.item_2);
        item_3 = (LinearLayout) findViewById(R.id.item_3);
        item_4 = (LinearLayout) findViewById(R.id.item_4);
        item_5 = (LinearLayout) findViewById(R.id.item_5);
        item_6 = (LinearLayout) findViewById(R.id.item_6);
        item_7 = (LinearLayout) findViewById(R.id.item_7);
        item_8 = (LinearLayout) findViewById(R.id.item_8);
        item_9 = (LinearLayout) findViewById(R.id.item_9);
        item_10 = (LinearLayout) findViewById(R.id.item_10);
        item_11 = (LinearLayout) findViewById(R.id.item_11);

        item_1.setOnClickListener(this);
        item_2.setOnClickListener(this);
        item_3.setOnClickListener(this);
        item_4.setOnClickListener(this);
        item_5.setOnClickListener(this);
        item_6.setOnClickListener(this);
        item_7.setOnClickListener(this);
        item_8.setOnClickListener(this);
        item_9.setOnClickListener(this);
        item_10.setOnClickListener(this);
        item_11.setOnClickListener(this);

        findViewById(R.id.ic_back).setVisibility(View.GONE);
        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setTypeface(myNewFace);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        Spanned text = Html.fromHtml(getString(R.string.name) + " " + "<b>" + String.valueOf(year) + "</b>");
        lbl_title.setText(text);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        txt_item_1 = (TextView) findViewById(R.id.txt_item_1);
        txt_item_2 = (TextView) findViewById(R.id.txt_item_2);
        txt_item_3 = (TextView) findViewById(R.id.txt_item_3);
        txt_item_4 = (TextView) findViewById(R.id.txt_item_4);
        txt_item_5 = (TextView) findViewById(R.id.txt_item_5);
        txt_item_6 = (TextView) findViewById(R.id.txt_item_6);
        txt_item_7 = (TextView) findViewById(R.id.txt_item_7);
        txt_item_8 = (TextView) findViewById(R.id.txt_item_8);
        txt_item_9 = (TextView) findViewById(R.id.txt_item_9);
        txt_item_10 = (TextView) findViewById(R.id.txt_item_10);
        txt_item_11 = (TextView) findViewById(R.id.txt_item_11);

        txt_item_1.setTypeface(myNewFace);
        txt_item_2.setTypeface(myNewFace);
        txt_item_3.setTypeface(myNewFace);
        txt_item_4.setTypeface(myNewFace);
        txt_item_5.setTypeface(myNewFace);
        txt_item_6.setTypeface(myNewFace);
        txt_item_7.setTypeface(myNewFace);
        txt_item_8.setTypeface(myNewFace);
        txt_item_9.setTypeface(myNewFace);
        txt_item_10.setTypeface(myNewFace);
        txt_item_11.setTypeface(myNewFace);

        Utils.showAdPopup();
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count == 2)
            finish();
        else
            Utils.longToast(HomeActivity.this, getString(R.string.press_back_again));
    }

    @Override
    public void onClick(View v) {
        count = 0;
        int id = v.getId();

        switch (id) {
            case R.id.item_1:
                startActivity(new Intent(HomeActivity.this, CheckBeautifulDayActivity.class));
                //finish();
                break;

            case R.id.item_2:
                startActivity(new Intent(HomeActivity.this, CheckBeautifulCardActivity.class));
                //finish();
                break;

            case R.id.item_3:
                startActivity(new Intent(HomeActivity.this, CheckDestinyActivity.class));
                //finish();
                break;

            case R.id.item_4:
                startActivity(new Intent(HomeActivity.this, BoiEgyptActivity.class));
                //finish();
                break;

            case R.id.item_5:
                startActivity(new Intent(HomeActivity.this, ChooseCoupleAgeActivity.class));
                //finish();
                break;

            case R.id.item_6:
                startActivity(new Intent(HomeActivity.this, BoiLoveActivity.class));
                //finish();
                break;

            case R.id.item_7:
                startActivity(new Intent(HomeActivity.this, ChooseBuildingYearActivity.class));
                //finish();
                break;

            case R.id.item_8:
                startActivity(new Intent(HomeActivity.this, CheckHouseDirectionActivity.class));
                //finish();
                break;

            case R.id.item_9:
                startActivity(new Intent(HomeActivity.this, BoiParentBirthdayActivity.class));
                //finish();
                break;

            case R.id.item_10:
                startActivity(new Intent(HomeActivity.this, CheckStarDestinyActivity.class));
                //finish();
                break;

            case R.id.item_11:
                startActivity(new Intent(HomeActivity.this, CheckLuckyClothesActivity.class));
                //finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(intentMyService));
    }
}
