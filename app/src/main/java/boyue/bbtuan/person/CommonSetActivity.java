package boyue.bbtuan.person;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class CommonSetActivity extends Activity implements View.OnClickListener{
    private Button commonSetBackButt;
    private TextView commonSetTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_commonset);
        initView();
    }

    private void initView() {
        commonSetBackButt=(Button)this.findViewById(R.id.btn_top_back);
        commonSetBackButt.setOnClickListener(this);
        commonSetTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        commonSetTitleTv.setText("通用设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            default:
                break;
        }
    }
}
