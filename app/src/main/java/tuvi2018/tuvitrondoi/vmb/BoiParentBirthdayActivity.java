package tuvi2018.tuvitrondoi.vmb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import tuvi2018.tuvitrondoi.vmb.Database.ParentBirthyear;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class BoiParentBirthdayActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private ParentBirthyear helper;

    private MaterialRippleLayout ic_back;
    private TextView txt_label;
    private TextView lbl_title;

    private FrameLayout spinner_1;
    private FrameLayout spinner_2;
    private FrameLayout spinner_3;

    private TextView lbl_spinner_1;
    private TextView lbl_spinner_2;
    private TextView lbl_spinner_3;

    private Button btn_result;
    private AdView banner;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_parent_birthday);

        helper = new ParentBirthyear(BoiParentBirthdayActivity.this);

        dialog = new ProgressDialog(BoiParentBirthdayActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.boi_parentBirthday_CAP));
        lbl_title.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoiParentBirthdayActivity.this, HomeActivity.class));
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

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BoiParentBirthdayActivity.this, HomeActivity.class));
        finish();
    }*/

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.spinner_1:
                Utils.showYearDialog(BoiParentBirthdayActivity.this, lbl_spinner_1);
                break;

            case R.id.spinner_2:
                Utils.showYearDialog(BoiParentBirthdayActivity.this, lbl_spinner_2);
                break;

            case R.id.spinner_3:
                Utils.showYearDialog(BoiParentBirthdayActivity.this, lbl_spinner_3);
                break;

            case R.id.btn_result:
                if (lbl_spinner_1.getText().toString().equals(getString(R.string.boi_parentBirthday_father_birthyear))) {
                    Utils.shortSnackbar(BoiParentBirthdayActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_2.getText().toString().equals(getString(R.string.boi_parentBirthday_mother_birthyear))) {
                    Utils.shortSnackbar(BoiParentBirthdayActivity.this, getString(R.string.input_null));
                    return;
                }

                if (lbl_spinner_3.getText().toString().equals(getString(R.string.boi_parentBirthday_children_birthyear))) {
                    Utils.shortSnackbar(BoiParentBirthdayActivity.this, getString(R.string.input_null));
                    return;
                }

                model = helper.getData(lbl_spinner_1.getText().toString(),
                        lbl_spinner_2.getText().toString(), lbl_spinner_3.getText().toString());

                if (model.getCount() > 0) {
                    Intent intent = new Intent(BoiParentBirthdayActivity.this, ResultActivity.class);
                    intent.putExtra("result", helper.getResult(model));

                    startActivity(intent);
                    //finish();
                } else if (!Check.checkInternetConnection(BoiParentBirthdayActivity.this, BoiParentBirthdayActivity.this))
                    return;
                else {
                    dialog.show();
                    new GetResult("born?father=" + lbl_spinner_1.getText()
                            + "&mother=" + lbl_spinner_2.getText()
                            + "&child=" + lbl_spinner_3.getText(), BoiParentBirthdayActivity.this).execute();
                }

                break;
        }
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(BoiParentBirthdayActivity.this, ResultActivity.class);
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
