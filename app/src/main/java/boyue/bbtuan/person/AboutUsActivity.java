package boyue.bbtuan.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class AboutUsActivity extends Activity implements View.OnClickListener{
    private Button aboutBackButt;
    private TextView aboutTitleTv,aboutLicenseTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    private void initView() {
        aboutBackButt=(Button)this.findViewById(R.id.btn_top_back);
        aboutBackButt.setOnClickListener(this);
        aboutTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        aboutTitleTv.setText("关于我们");
        aboutLicenseTv=(TextView)this.findViewById(R.id.tv_infolicense);
        aboutLicenseTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.tv_infolicense:
                startActivity(new Intent(AboutUsActivity.this,MyLicenseActivity.class));
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
