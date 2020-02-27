package tuvi2018.tuvitrondoi.vmb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import tuvi2018.tuvitrondoi.vmb.Database.HouseDirection;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class CheckHouseDirectionActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private HouseDirection helper;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;
    private TextView txt_label;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;
    private FrameLayout spinner_3;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;
    private TextView lbl_spinner_3;

    private Button btn_result;
    private AdView banner;

    private String arr_direction[];

    private int indexSelected;
    private ProgressDialog dialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_house_direction);

        helper = new HouseDirection(CheckHouseDirectionActivity.this);

        dialog = new ProgressDialog(CheckHouseDirectionActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.check_houseDirection_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckHouseDirectionActivity.this, HomeActivity.class));
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

        lbl_spinner_3 = (TextView) findViewById(R.id.lbl_spinner_3);
        spinner_3 = (FrameLayout) findViewById(R.id.spinner_3);
        spinner_3.setOnClickListener(this);
        lbl_spinner_3.setTypeface(myNewFace);

        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);

        arr_direction = this.getResources().getStringArray(R.array.arr_direction);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckHouseDirectionActivity.this, HomeActivity.class));
        finish();
    }*/

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.spinner_1:
                showDialogSpinner_1();
                break;

            case R.id.spinner_2:
                showDialogSpinner_2();
                break;

            case R.id.spinner_3:
                Utils.showYearDialog(CheckHouseDirectionActivity.this, lbl_spinner_3);
                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.all_gender))) {
                    Utils.shortSnackbar(CheckHouseDirectionActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.check_houseDirection_direction))) {
                    Utils.shortSnackbar(CheckHouseDirectionActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_3.getText().toString().equals(getString(R.string.all_birthyear))) {
                    Utils.shortSnackbar(CheckHouseDirectionActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(),
                        lbl_spinner_2.getText().toString(), lbl_spinner_3.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(CheckHouseDirectionActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(CheckHouseDirectionActivity.this, CheckHouseDirectionActivity.this))
                    return;
                else {
                    dialog.show();

                    String text_2 = lbl_spinner_2.getText().toString();
                    text_2 = text_2.replaceAll("\\s", "");

                    new GetResult("house?year_birth=" + lbl_spinner_3.getText()
                            + "&direction=" + text_2
                            + "&gender=" + indexSelected, CheckHouseDirectionActivity.this).execute();
                }

                break;
        }
    }

    public void showDialogSpinner_1() {
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_gender, null);
        AlertDialog.Builder alert = new AlertDialog.Builder((CheckHouseDirectionActivity.this));
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

    public void showDialogSpinner_2() {
        View alertLayout = getLayoutInflater().inflate(R.layout.dialog_list, null);
        AlertDialog.Builder alert = new AlertDialog.Builder((CheckHouseDirectionActivity.this));
        alert.setView(alertLayout);

        ListView ls = (ListView) alertLayout.findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<String>(CheckHouseDirectionActivity.this, android.R.layout
                .simple_list_item_1, arr_direction);
        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog.hide();
                lbl_spinner_2.setText(arr_direction[position]);
            }
        });

        alertDialog = alert.create();
        alertDialog.show();
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(CheckHouseDirectionActivity.this, ResultActivity.class);
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