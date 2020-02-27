package tuvi2018.tuvitrondoi.vmb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Database.CheckDestiny;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class CheckDestinyActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private CheckDestiny helper;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;
    private TextView txt_label;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;

    private Button btn_result;
    private AdView banner;

    private Calendar calendar_2;
    private ProgressDialog dialog;

    DatePickerDialog.OnDateSetListener dateDialog_2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            calendar_2.set(Calendar.YEAR, year);
            calendar_2.set(Calendar.MONTH, monthOfYear);
            calendar_2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateSpinner2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_destiny);

        calendar_2 = Calendar.getInstance();
        helper = new CheckDestiny(CheckDestinyActivity.this);

        dialog = new ProgressDialog(CheckDestinyActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.check_destiny_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckDestinyActivity.this, HomeActivity.class));
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

        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.spinner_1:
                Utils.showYearDialog(CheckDestinyActivity.this, lbl_spinner_1);
                break;

            case R.id.spinner_2:
                new DatePickerDialog(CheckDestinyActivity.this, dateDialog_2,
                        calendar_2.get(Calendar.YEAR),
                        calendar_2.get(Calendar.MONTH),
                        calendar_2.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.check_destiny_checkYear))) {
                    Utils.shortSnackbar(CheckDestinyActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.all_birthday))) {
                    Utils.shortSnackbar(CheckDestinyActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(), lbl_spinner_2.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(CheckDestinyActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(CheckDestinyActivity.this, CheckDestinyActivity.this))
                    return;
                else {
                    dialog.show();
                    new GetResult("destiny?viewyear=" + lbl_spinner_1.getText()
                            + "&day=" + calendar_2.getTime().getDate()
                            + "&month=" + (calendar_2.getTime().getMonth() + 1)
                            + "&year=" + (calendar_2.getTime().getYear() + 1900),
                            CheckDestinyActivity.this).execute();
                }

                break;
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckDestinyActivity.this, HomeActivity.class));
        finish();
    }*/

    public void updateSpinner2() {
        lbl_spinner_2.setText(calendar_2.getTime().getDate() + " / "
                + (calendar_2.getTime().getMonth() + 1) + " / "
                + (calendar_2.getTime().getYear() + 1900));
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(CheckDestinyActivity.this, ResultActivity.class);
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
