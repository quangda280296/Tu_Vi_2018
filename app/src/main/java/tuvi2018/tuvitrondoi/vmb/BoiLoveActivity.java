package tuvi2018.tuvitrondoi.vmb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Database.BoiLove;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class BoiLoveActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private BoiLove helper;

    private MaterialRippleLayout ic_back;
    private TextView txt_label;
    private TextView lbl_title;

    private FrameLayout spinner_2;
    private FrameLayout spinner_4;

    private TextView lbl_female;
    private TextView lbl_male;

    private EditText txt_spinner_1;
    private TextView lbl_spinner_2;
    private EditText txt_spinner_3;
    private TextView lbl_spinner_4;

    private Button btn_result;
    private AdView banner;

    private Calendar calendar_1;
    private Calendar calendar_2;
    private ProgressDialog dialog;

    DatePickerDialog.OnDateSetListener dateDialog_1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            calendar_1.set(Calendar.YEAR, year);
            calendar_1.set(Calendar.MONTH, monthOfYear);
            calendar_1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateSpinner2();
        }
    };

    DatePickerDialog.OnDateSetListener dateDialog_2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            calendar_2.set(Calendar.YEAR, year);
            calendar_2.set(Calendar.MONTH, monthOfYear);
            calendar_2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateSpinner4();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_love);

        Typeface font = Typeface.createFromAsset(getAssets(), "SanFranciscoDisplay-Semibold.otf");

        calendar_1 = Calendar.getInstance();
        calendar_2 = Calendar.getInstance();
        helper = new BoiLove(BoiLoveActivity.this);

        dialog = new ProgressDialog(BoiLoveActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.boi_love_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        lbl_female = (TextView) findViewById(R.id.lbl_female);
        lbl_female.setTypeface(font);

        lbl_male = (TextView) findViewById(R.id.lbl_male);
        lbl_male.setTypeface(font);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoiLoveActivity.this, HomeActivity.class));
                finish();
            }
        });

        lbl_spinner_2 = (TextView) findViewById(R.id.lbl_spinner_2);
        spinner_2 = (FrameLayout) findViewById(R.id.spinner_2);
        spinner_2.setOnClickListener(this);
        lbl_spinner_2.setTypeface(myNewFace);

        lbl_spinner_4 = (TextView) findViewById(R.id.lbl_spinner_4);
        spinner_4 = (FrameLayout) findViewById(R.id.spinner_4);
        spinner_4.setOnClickListener(this);
        lbl_spinner_4.setTypeface(myNewFace);

        txt_spinner_1 = (EditText) findViewById(R.id.txt_spinner_1);
        txt_spinner_3 = (EditText) findViewById(R.id.txt_spinner_3);

        txt_spinner_1.setTypeface(myNewFace);
        txt_spinner_3.setTypeface(myNewFace);

        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BoiLoveActivity.this, HomeActivity.class));
        finish();
    }*/

    public void updateSpinner2() {
        lbl_spinner_2.setText(calendar_1.getTime().getDate() + " / "
                + (calendar_1.getTime().getMonth() + 1) + " / "
                + (calendar_1.getTime().getYear() + 1900));
    }

    public void updateSpinner4() {
        lbl_spinner_4.setText(calendar_2.getTime().getDate() + " / "
                + (calendar_2.getTime().getMonth() + 1) + " / "
                + (calendar_2.getTime().getYear() + 1900));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.spinner_2:
                new DatePickerDialog(BoiLoveActivity.this, dateDialog_1,
                        calendar_1.get(Calendar.YEAR),
                        calendar_1.get(Calendar.MONTH),
                        calendar_1.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.spinner_4:
                new DatePickerDialog(BoiLoveActivity.this, dateDialog_2,
                        calendar_2.get(Calendar.YEAR),
                        calendar_2.get(Calendar.MONTH),
                        calendar_2.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btn_result:
                if (lbl_spinner_2.getText().toString().equals(getString(R.string.all_birthday))) {
                    Utils.shortSnackbar(BoiLoveActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_4.getText().toString().equals(getString(R.string.all_birthday))) {
                    Utils.shortSnackbar(BoiLoveActivity.this, getString(R.string.input_null));
                    return;
                }

                if (txt_spinner_1.getText().toString().equals("")) {
                    Utils.shortSnackbar(BoiLoveActivity.this, getString(R.string.input_null));
                    return;
                }

                if (txt_spinner_3.getText().toString().equals("")) {
                    Utils.shortSnackbar(BoiLoveActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(txt_spinner_1.getText().toString(), lbl_spinner_2.getText().toString(),
                        txt_spinner_3.getText().toString(), lbl_spinner_4.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(BoiLoveActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(BoiLoveActivity.this, BoiLoveActivity.this))
                    return;
                else {
                    dialog.show();
                    String extra_url = "love?"
                            + "boy_name=" + txt_spinner_1.getText()
                            + "&boy_birthday=" + calendar_1.getTime().getDate()
                            + "&boy_birthmonth=" + (calendar_1.getTime().getMonth() + 1)
                            + "&boy_birthyear=" + (calendar_1.getTime().getYear() + 1900)

                            + "&girl_name=" + txt_spinner_3.getText()
                            + "&girl_birthday=" + calendar_2.getTime().getDate()
                            + "&girl_birthmonth=" + (calendar_2.getTime().getMonth() + 1)
                            + "&girl_birthyear=" + (calendar_2.getTime().getYear() + 1900);

                    extra_url = extra_url.replaceAll("\\s", "+");
                    new GetResult(extra_url, BoiLoveActivity.this).execute();
                }

                break;
        }
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(BoiLoveActivity.this, ResultActivity.class);
        intent.putExtra("result", result);

        startActivity(intent);
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(intentMyService));
    }
}
