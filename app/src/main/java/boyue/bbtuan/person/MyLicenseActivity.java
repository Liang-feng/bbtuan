package boyue.bbtuan.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import boyue.bbtuan.R;


public class MyLicenseActivity extends Activity{
    private Button myLicenseRButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mylicense);
        myLicenseRButt=(Button)this.findViewById(R.id.btn_mylicense_back);
        myLicenseRButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
