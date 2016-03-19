package boyue.bbtuan.superroom;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import boyue.bbtuan.R;
import boyue.bbtuan.view.KeywordsFlow;

public class SuperSearchActivity extends Activity implements View.OnClickListener {
    public static final String[] keywords = { "高等数学A1", "高等数学A2", "工程制图A",
            "工程制图B", "工程制图C",//
            "大学物理A", "大学物理B", "大学物理c", "C语言程序设计A", "C语言程序设计B",//
            "C语言程序设计C", "线性代数A", "线性代数B", "线性代数C", "复变函数A",//
            "复变函数B", "复变函数C", "概率论", "电路分析基础", "模拟电子技术A",//
            "数据结构B(双语教学)", "数字逻辑A", "信号与系统分析A", "高频电子线路", "微处理系统与接口技术",//
            "数据库原理与应用", "会计学", "统计学", "财务管理", "运筹学",//
            "商务谈判", "市场营销" };
    private KeywordsFlow keywordsFlow;//科目显示特效
    private TextView superSearchKeyrefTv;//科目更新按键
    private Button topBackButt;//界面返回
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_supersearch);
        initView();
        keywordsFlow.setDuration(800l);
        keywordsFlow.setOnItemClickListener(this);
        // 添加
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        keywordsFlow.rubKeywords();
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
    }

    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        superSearchKeyrefTv=(TextView)this.findViewById(R.id.tv_supersearch_keyref);
        superSearchKeyrefTv.setOnClickListener(this);
        keywordsFlow = (KeywordsFlow) findViewById(R.id.fraLay_supersearch_keyref);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_supersearch_keyref:
                keywordsFlow.rubKeywords();
                feedKeywordsFlow(keywordsFlow, keywords);
                keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
                break;
            case R.id.btn_top_back:
                finish();
                break;
            default:
                break;
        }
        if (v instanceof TextView) {
            String keyword = ((TextView) v).getText().toString();
            if (keyword.equals("换一换")){
            }else {
                showToast(keyword);
                Log.e("Search", keyword);
            }
        }
    }
    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }
    private void showToast(String str){
        Toast.makeText(SuperSearchActivity.this, str, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

