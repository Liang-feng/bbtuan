package boyue.bbtuan.superroom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import boyue.bbtuan.R;
import boyue.bbtuan.view.RoundImageView;

public class SuperTeacherinfoActivity extends Activity implements View.OnClickListener{
    private Button topBackButt;//返回键
    private Button teacherStatusButt;//拨打老师电话按键
    private TextView topTitleTv;//界面主题
    private Button teacherinfogPutButt;//团队报名按键
    private Button teacherinfopPutButt;//个人报名按钮
    private RoundImageView tecinfoHeadImg;//老师头像
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherinfo);
        initView();
    }

    private void initView() {
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("陈志良");//老师名字
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("高等数学A1");//老师授课科目
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("10");//当前报名人数
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("15");//课程总人数
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("一对多");//授课形式
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("7课时");//授课课时
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("星期四晚上7:00-9:00");//授课时间
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("电子工程与自动化学院");//授课学院
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("99");//授课单价
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("电子工程与自动化学院");//老师所在学院
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("15078332282");//老师联系方式
        ((TextView)this.findViewById(R.id.tv_tecinfo_name)).setText("在教学中注重基础知识的把握与梳理。能根据学生的特点有针对性的辅导。在我的辅导下学生都能得到充分的发展。你会，不自觉的喜欢我的课。希望我们一起提高，共同发展。同学们，你们的成功离不开老师的帮助，我们的快乐离不开学生的好评。");////老师个人介绍
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        teacherStatusButt=(Button)this.findViewById(R.id.btn_tecinfo_status);
        teacherStatusButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        topTitleTv.setText("课程信息");
        teacherinfogPutButt=(Button)this.findViewById(R.id.btn_tecinfog_put);
        teacherinfopPutButt=(Button)this.findViewById(R.id.btn_tecinfop_put);
        teacherinfogPutButt.setOnClickListener(this);
        teacherinfopPutButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.btn_tecinfo_status:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15078332282"));
                startActivity(intent);
                break;
            case R.id.btn_tecinfog_put:
                Intent intent1=new Intent(SuperTeacherinfoActivity.this,SuperGroupPutActivity.class);
                intent1.putExtra("signway","group");
                startActivity(intent1);
                break;
            case R.id.btn_tecinfop_put:
                Intent intent2=new Intent(SuperTeacherinfoActivity.this,SuperGroupPutActivity.class);
                intent2.putExtra("signway","person");
                startActivity(intent2);
                break;
            default:

                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
