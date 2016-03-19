package boyue.bbtuan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import boyue.bbtuan.bean.USER;
import boyue.bbtuan.tabmain.TabMainActivity;
import boyue.bbtuan.utils.LocationApplication;

public class MainActivity extends Activity {
    private SharedPreferences preferences;
    private ImageView welcomeIv = null;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor="gcj02";
    private LocationClient mLocationClient;
    private Context mContext = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        initLocation();
        mLocationClient.start();
        // 读取SharedPreferences中需要的数据
        preferences = getSharedPreferences("baobaotuan", MODE_WORLD_READABLE);
        int count = preferences.getInt("count", 0);
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        if (count == 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), GuideActivity.class);
            startActivity(intent);
            finish();
        }
        welcomeIv = (ImageView) this.findViewById(R.id.iv_welcome);
        //获取设备像素值
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dm = this.getApplicationContext().getResources().getDisplayMetrics();
        float scalePx2Dp = this.getApplicationContext().getResources().getDisplayMetrics().density;
        USER.scalePx2Dp=scalePx2Dp;
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        USER.screenHeight=screenHeight;
        USER.screenWidth=screenWidth;
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(1000);// 设置动画显示时间
        welcomeIv.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            welcomeIv.setBackgroundResource(R.mipmap.ic_main_bg);

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }
    private void skip() {
        USER.setChangeCity(USER.getCity());
        startActivity(new Intent(this, TabMainActivity.class));
        finish();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        int span=3000;//定位次数
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        mLocationClient.stop();
    }


}
