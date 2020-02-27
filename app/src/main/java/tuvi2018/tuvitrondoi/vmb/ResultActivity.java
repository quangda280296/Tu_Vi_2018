package tuvi2018.tuvitrondoi.vmb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.ads.AdView;

import tuvi2018.tuvitrondoi.vmb.Utils.Utils;

import static tuvi2018.tuvitrondoi.vmb.Config.Config.intentMyService;
import static tuvi2018.tuvitrondoi.vmb.Config.Config.myNewFace;

public class ResultActivity extends AppCompatActivity {

    private WebView webkit;
    private AdView banner;

    private MaterialRippleLayout ic_back;
    private TextView lbl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        webkit = findViewById(R.id.webkit);
        webkit.getSettings().setJavaScriptEnabled(true);
        webkit.setBackgroundColor(Color.TRANSPARENT);

        final String result = getIntent().getStringExtra("result");

        webkit.loadDataWithBaseURL("", result, "text/html", "UTF-8", "");
        webkit.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webkit.loadUrl(
                        "javascript:document.body.style.setProperty(\"color\", \"#39103c\");"
                );
            }
        });

        lbl_title = findViewById(R.id.lbl_title);
        lbl_title.setText(getString(R.string.result));
        lbl_title.setTypeface(myNewFace);

        ic_back = findViewById(R.id.ic_back);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, HomeActivity.class));
                finish();
            }
        });

        banner = (AdView) findViewById(R.id.adView);
        Utils.showAd(banner);

        Utils.showAdPopup();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResultActivity.this, HomeActivity.class));
        finish();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(intentMyService));
    }
}
