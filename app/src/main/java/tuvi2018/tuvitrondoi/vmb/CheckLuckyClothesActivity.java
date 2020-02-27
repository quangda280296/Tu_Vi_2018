package tuvi2018.tuvitrondoi.vmb;

import android.app.AlertDialog;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Database.LuckyClothes;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class CheckLuckyClothesActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private LuckyClothes helper;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;
    private TextView txt_label;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;

    private Button btn_result;
    private AdView banner;

    private int indexSelected;
    private Calendar dateCalendar;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;

    DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            dateCalendar.set(Calendar.YEAR, year);
            dateCalendar.set(Calendar.MONTH, monthOfYear);
            dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateSpinner2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_lucky_clothes);

        dateCalendar = Calendar.getInstance();
        helper = new LuckyClothes(CheckLuckyClothesActivity.this);

        dialog = new ProgressDialog(CheckLuckyClothesActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.check_luckyClothes_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckLuckyClothesActivity.this, HomeActivity.class));
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

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckLuckyClothesActivity.this, HomeActivity.class));
        finish();
    }*/

    public void updateSpinner2() {
        lbl_spinner_2.setText(dateCalendar.getTime().getDate() + " / "
                + (dateCalendar.getTime().getMonth() + 1) + " / "
                + (dateCalendar.getTime().getYear() + 1900));
    }

    public void showDialogSpinner_1() {
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_gender, null);
        AlertDialog.Builder alert = new AlertDialog.Builder((CheckLuckyClothesActivity.this));
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
                        indexSelected = 0;
                        break;

                    case R.id.rb_female:
                        lbl_spinner_1.setText(getString(R.string.all_female));
                        indexSelected = 1;
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
                new DatePickerDialog(CheckLuckyClothesActivity.this, dateDialog,
                        dateCalendar.get(Calendar.YEAR),
                        dateCalendar.get(Calendar.MONTH),
                        dateCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.all_gender))) {
                    Utils.shortSnackbar(CheckLuckyClothesActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.all_birthday))) {
                    Utils.shortSnackbar(CheckLuckyClothesActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(), lbl_spinner_2.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(CheckLuckyClothesActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(CheckLuckyClothesActivity.this, CheckLuckyClothesActivity.this))
                    return;
                else {
                    dialog.show();
                    new GetResult("trangphuc?"
                            + "&day=" + dateCalendar.getTime().getDate()
                            + "&month=" + (dateCalendar.getTime().getMonth() + 1)
                            + "&year=" + (dateCalendar.getTime().getYear() + 1900)
                            + "&gender=" + indexSelected, CheckLuckyClothesActivity.this).execute();
                }

                break;
        }
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(CheckLuckyClothesActivity.this, ResultActivity.class);
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
