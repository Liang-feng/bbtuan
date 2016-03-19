package boyue.bbtuan.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class MyMessageActivity extends Activity implements View.OnClickListener {
    private Button myMessageBackButt;
    private TextView myMessageTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        initView();
    }

    private void initView() {
        myMessageBackButt=(Button)this.findViewById(R.id.btn_top_back);
        myMessageBackButt.setOnClickListener(this);
        myMessageTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        myMessageTitleTv.setText("我的消息");
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
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
