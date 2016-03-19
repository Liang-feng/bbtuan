package boyue.bbtuan.jifen;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class JifenProActivity extends Activity implements View.OnClickListener{
    private TextView top_title;
    private Button btn_top_back;
    private String proTcl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        proTcl=intent.getStringExtra("protcl");
        if ( proTcl.equals("guake")){
            setContentView(R.layout.activity_guakepro);
        }else {
            setContentView(R.layout.activity_jifen_pro);
        }
        initView();
    }

    private void initView() {
        top_title = (TextView) findViewById(R.id.tv_top_btntext);
        btn_top_back = (Button) findViewById(R.id.btn_top_back);
        if ( proTcl.equals("guake")){
            top_title.setText("挂科险协议须知");
        }else {
            top_title.setText("积分规则");
        }
        btn_top_back.setOnClickListener(this);
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
