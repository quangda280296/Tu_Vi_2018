package tuvi2018.tuvitrondoi.vmb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import tuvi2018.tuvitrondoi.vmb.Database.CoupleAge;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Handler.GetYear;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Interface.IYearChoosen;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class ChooseCoupleAgeActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner, IYearChoosen {

    private Cursor model;
    private CoupleAge helper;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;
    private TextView txt_label;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;
    private TextView lbl_lunarYear;

    private Button btn_result;
    private AdView banner;

    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private int gend;
    private String arr_can[];
    private String arr_chi[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_couple_age);

        helper = new CoupleAge(ChooseCoupleAgeActivity.this);

        dialog = new ProgressDialog(ChooseCoupleAgeActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.choose_coupleAge_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCoupleAgeActivity.this, HomeActivity.class));
                finish();
            }
        });

        lbl_spinner_1 = (TextView) findViewById(R.id.lbl_spinner_1);
        spinner_1 = (FrameLayout) findViewById(R.id.spinner_1);
        spinner_1.setOnClickListener(this);
        lbl_spinner_1.setTypeface(myNewFace);

        lbl_spinner_2 = (TextView) findViewById(R.id.lbl_spinner_2);
        spinner_2 = (FrameLayout) findViewById(R.id.spinner_2);
        spinner_2.setOnClickListener(this);
        lbl_spinner_2.setTypeface(myNewFace);

        lbl_lunarYear = (TextView) findViewById(R.id.lbl_lunarYear);
        lbl_lunarYear.setTypeface(myNewFace);

        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);

        arr_can = this.getResources().getStringArray(R.array.arr_can);
        arr_chi = this.getResources().getStringArray(R.array.arr_chi);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChooseCoupleAgeActivity.this, HomeActivity.class));
        finish();
    }*/

    public void showDialogSpinner_1() {
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_gender, null);
        AlertDialog.Builder alert = new AlertDialog.Builder((ChooseCoupleAgeActivity.this));
        alert.setView(alertLayout);

        final RadioGroup gender = (RadioGroup) alertLayout.findViewById(R.id.gender);
        Button done = (Button) alertLayout.findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();

                switch (gender.getCheckedRadioButtonId()) {
                    case R.id.rb_male:
                        lbl_spinner_1.setText(getString(R.string.all_male));
                        gend = 0;
                        break;

                    case R.id.rb_female:
                        lbl_spinner_1.setText(getString(R.string.all_female));
                        gend = 1;
                        break;
                }
            }
        });

        alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.spinner_1:
                showDialogSpinner_1();
                break;

            case R.id.spinner_2:
                new GetYear(ChooseCoupleAgeActivity.this, ChooseCoupleAgeActivity.this).getYear();
                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.all_gender))) {
                    Utils.shortSnackbar(ChooseCoupleAgeActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.choose_coupleAge_birthyear))) {
                    Utils.shortSnackbar(ChooseCoupleAgeActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(), lbl_spinner_2.getText().toString(),
                        lbl_lunarYear.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(ChooseCoupleAgeActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(ChooseCoupleAgeActivity.this, ChooseCoupleAgeActivity.this))
                    return;
                else {
                    dialog.show();
                    String extra_url = "couple?gender=" + gend + "&eastyear=" + lbl_lunarYear.getText();
                    extra_url = extra_url.replaceAll("\\s", "");
                    new GetResult(extra_url, ChooseCoupleAgeActivity.this).execute();
                }

                break;
        }
    }

    public String getLunarYear(int i) {
        return arr_can[i % 10] + " " + arr_chi[i % 12];
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(ChooseCoupleAgeActivity.this, ResultActivity.class);
        intent.putExtra("result", result);

        startActivity(intent);
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(intentMyService));
    }

    @Override
    public void onYearReturn(int year) {
        lbl_spinner_2.setText(year + "");
        lbl_lunarYear.setText(getString(R.string.choose_coupleAge_lunarYear) + " " + getLunarYear(year));
    }
}
