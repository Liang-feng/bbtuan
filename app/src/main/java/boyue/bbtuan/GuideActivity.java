package boyue.bbtuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import boyue.bbtuan.adapter.ViewPagerAdapter;
import boyue.bbtuan.person.LoginActivity;
import boyue.bbtuan.person.PhoneActivity;

public class GuideActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private Button groupRegisterButt,groupLoginButt;
    public static Context context;

    //引导图片资源
    private static final int[] pics = {R.mipmap.ic_main_guide1,
            R.mipmap.ic_main_guide2, R.mipmap.ic_main_guide3,
    };
    //底部小店图片
    private ImageView[] dots ;

    //记录当前选中位置
    private int currentIndex;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        context=this.getApplicationContext();
        views = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        vp = (ViewPager) findViewById(R.id.viewpager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);
        groupLoginButt = (Button) findViewById(R.id.btn_group_login);
        groupRegisterButt = (Button) findViewById(R.id.btn_group_register);

        //初始化底部小点
        initDots();
    }
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true);//设置为白色，即选中状态
    }

    /**
     *设置当前的引导页
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }
    /**
     *这只当前引导小点的选中
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = positon;
    }
    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }
    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
         //注册控件
        groupRegisterButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(GuideActivity.this, PhoneActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //登录控件
        groupLoginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }
}

