package tuvi2018.tuvitrondoi.vmb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import tuvi2018.tuvitrondoi.vmb.Database.BoiEgypt;
import tuvi2018.tuvitrondoi.vmb.Handler.GetResult;
import tuvi2018.tuvitrondoi.vmb.Interface.IDataListenner;
import tuvi2018.tuvitrondoi.vmb.Utils.Check;
import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class BoiEgyptActivity extends AppCompatActivity implements View.OnClickListener, IDataListenner {

    private Cursor model;
    private BoiEgypt helper;

    private MaterialRippleLayout ic_back;
    private TextView txt_label;
    private TextView lbl_title;
    private EditText txt_spinner;
    private Button btn_result;
    private AdView banner;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boi_egypt);

        helper = new BoiEgypt(BoiEgyptActivity.this);

        dialog = new ProgressDialog(BoiEgyptActivity.this);
        dialog.setTitle(getString(R.string.processing));
        dialog.setMessage(getString(R.string.please_wait));

        lbl_title = (TextView) findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.boi_Egypt_CAP));
        lbl_title.setTypeface(myNewFace);

        ic_back = (MaterialRippleLayout) findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoiEgyptActivity.this, tuvi2018.tuvitrondoi.vmb.HomeActivity.class));
                finish();
            }
        });

        txt_spinner = (EditText) findViewById(R.id.txt_spinner);

        lbl_title.setTypeface(myNewFace);
        txt_spinner.setTypeface(myNewFace);

        txt_label = (TextView) findViewById(R.id.txt_label);
        txt_label.setTypeface(myNewFace);

        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(this);

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BoiEgyptActivity.this, tuvi2018.tuvitrondoi.vmb.HomeActivity.class));
        finish();
    }*/

    @Override
    public void onClick(View v) {
        if (txt_spinner.getText().toString().equals("")) {
            Utils.shortSnackbar(BoiEgyptActivity.this, getString(R.string.input_null));
            return;
        }

        model = helper.getData(txt_spinner.getText().toString());

        if (model.getCount() > 0) {
            Intent intent = new Intent(BoiEgyptActivity.this, tuvi2018.tuvitrondoi.vmb.ResultActivity.class);
            intent.putExtra("result", helper.getResult(model));

            startActivity(intent);
            //finish();
        } else if (!Check.checkInternetConnection(BoiEgyptActivity.this, BoiEgyptActivity.this))
            return;
        else {
            dialog.show();
            String extra_url = "boiaicap?name=" + txt_spinner.getText();
            extra_url = extra_url.replaceAll("\\s", "+");
            new GetResult(extra_url, BoiEgyptActivity.this).execute();
        }
    }

    @Override
    public void onDataReturn(String result) {
        dialog.dismiss();

        Intent intent = new Intent(BoiEgyptActivity.this, tuvi2018.tuvitrondoi.vmb.ResultActivity.class);
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
