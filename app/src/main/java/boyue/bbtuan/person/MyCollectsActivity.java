package boyue.bbtuan.person;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;

public class MyCollectsActivity extends Activity implements View.OnClickListener{
   private Button myCollectsBackButt;
    private TextView myCollectsTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollects);
        initView();
    }

    private void initView() {
        myCollectsBackButt=(Button)this.findViewById(R.id.btn_top_back);
        myCollectsBackButt.setOnClickListener(this);
        myCollectsTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        myCollectsTitleTv.setText("我的收藏");
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
