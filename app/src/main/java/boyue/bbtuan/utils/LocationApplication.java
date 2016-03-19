package boyue.bbtuan.utils;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import boyue.bbtuan.bean.USER;
public class LocationApplication extends Application {
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public TextView mLocationResult,logMsg;
    public TextView trigger,exit;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }


    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null)
                return;
            //Receive Location
            USER.setLatitude(location.getLatitude());
            USER.setLontitude(location.getLongitude());
            LocationClientOption option = new LocationClientOption();
            option.setIsNeedAddress(true);
            mLocationClient.setLocOption(option);
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                USER.setProvince(location.getProvince());
                USER.setCity(location.getCity());
            } else if (location.getLocType() == BDLocation.TypeGpsLocation) {
                USER.setProvince(location.getProvince());
                USER.setCity(location.getCity());
            }
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果

               USER.setAddr(location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                USER.setAddr(location.getAddrStr());
            }

           USER.setLocDescribe(location.getLocationDescribe());// 位置语义化信息
        }


    }


    /**
     * 显示请求字符串
     * @param str
     */
    public void logMsg(String str) {
        try {
            if (mLocationResult != null)
                mLocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
