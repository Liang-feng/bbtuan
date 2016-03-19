package boyue.bbtuan.person;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class MyGoodsActivity extends Activity implements View.OnClickListener{
    private Button myGoodsBackButt;
    private TextView myGoodsTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygoods);
        initView();
    }

    private void initView() {
        myGoodsBackButt=(Button)this.findViewById(R.id.btn_top_back);
        myGoodsBackButt.setOnClickListener(this);
        myGoodsTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        myGoodsTitleTv.setText("我的订单");
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
