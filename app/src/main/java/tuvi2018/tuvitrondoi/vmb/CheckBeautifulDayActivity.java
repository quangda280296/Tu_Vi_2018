package tuvi2018.tuvitrondoi.vmb;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import tuvi2018.tuvitrondoi.vmb.Database.BeautifulDay;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class CheckBeautifulDayActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, IDataListenner {

    private Cursor model;
    private BeautifulDay helper;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;
    private TextView txt_label;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;

    private Button btn_result;
    private AdView banner;

    private Calendar myCalendar;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;
    private String Job[];
    private int indexSelected;

    DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateSpinner2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_beautiful_day);

        myCalendar = Calendar.getInstance();
        helper = new BeautifulDay(CheckBeautifulDayActivity.this);

        dialog = new ProgressDialog(CheckBeautifulDayActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.check_beautifulDay_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckBeautifulDayActivity.this, tuvi2018.tuvitrondoi.vmb.HomeActivity.class));
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

        Job = this.getResources().getStringArray(R.array.JOB);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckBeautifulDayActivity.this, tuvi2018.tuvitrondoi.vmb.HomeActivity.class));
        finish();
    }*/

    public void updateSpinner2() {
        lbl_spinner_2.setText(myCalendar.getTime().getDate() + " / "
                + (myCalendar.getTime().getMonth() + 1) + " / "
                + (myCalendar.getTime().getYear() + 1900));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.spinner_1:
                showDialog();
                break;

            case R.id.spinner_2:
                new DatePickerDialog(CheckBeautifulDayActivity.this, dateDialog,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.check_beautifulDay_work))) {
                    Utils.shortSnackbar(CheckBeautifulDayActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.check_beautifulDay_executeDay))) {
                    Utils.shortSnackbar(CheckBeautifulDayActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(), lbl_spinner_2.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(CheckBeautifulDayActivity.this, tuvi2018.tuvitrondoi.vmb.ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(CheckBeautifulDayActivity.this,CheckBeautifulDayActivity.this))
                    return;
                else {
                    dialog.show();
                    new GetResult("day?day=" + myCalendar.getTime().getDate()
                            + "&month=" + (myCalendar.getTime().getMonth() + 1)
                            + "&year=" + (myCalendar.getTime().getYear() + 1900)
                            + "&job=" + indexSelected, CheckBeautifulDayActivity.this).execute();
                }

                break;
        }
    }

    public void showDialog() {
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_list, null);
        AlertDialog.Builder alert = new AlertDialog.Builder((CheckBeautifulDayActivity.this));
        alert.setView(alertLayout);

        ListView ls = (ListView) alertLayout.findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(CheckBeautifulDayActivity.this, android.R.layout.simple_list_item_1, Job);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(this);

        alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        alertDialog.hide();
        indexSelected = position;
        lbl_spinner_1.setText(Job[position]);
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(CheckBeautifulDayActivity.this, tuvi2018.tuvitrondoi.vmb.ResultActivity.class);
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
