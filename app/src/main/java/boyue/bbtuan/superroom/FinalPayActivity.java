package boyue.bbtuan.superroom;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import boyue.bbtuan.R;
import boyue.bbtuan.view.PopupWindow;

public class FinalPayActivity  extends Activity implements OnClickListener{
	private LinearLayout alipayLinLay;
	private LinearLayout weixinLinLay;
	private LinearLayout unionPayLinLay;
	private LinearLayout dangmianfuLinLay;
	private TextView top_title;
	private Button btn_top_back;
	private TextView tvCourseTextView;
	private TextView tvAllRmb;
	private int allRmb;//支付宝 ---------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_finalpay);
		tvCourseTextView=(TextView) findViewById(R.id.course);
		tvAllRmb=(TextView) findViewById(R.id.allRmb);
		Bundle bundle = getIntent().getExtras();
		if (bundle!=null) {
			allRmb = bundle.getInt("AllRmb");
			tvCourseTextView.setText(bundle.getString("course"));
			tvAllRmb.setText("¥"+allRmb);
			
		}
		top_title = (TextView) findViewById(R.id.tv_top_btntext);
		btn_top_back = (Button) findViewById(R.id.btn_top_back);
		btn_top_back.setOnClickListener(this);
		top_title.setText("选择支付方式");
		alipayLinLay=(LinearLayout) findViewById(R.id.ll_alipay);
		alipayLinLay.setOnClickListener(this);
		weixinLinLay=(LinearLayout) findViewById(R.id.ll_weixin);
		weixinLinLay.setOnClickListener(this);
		unionPayLinLay=(LinearLayout) findViewById(R.id.ll_unionpay);
		unionPayLinLay.setOnClickListener(this);
		dangmianfuLinLay=(LinearLayout) findViewById(R.id.ll_dangmianfu);
		dangmianfuLinLay.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_alipay:
			showToast("暂未开放支付宝支付服务!");
			break;
		case R.id.ll_weixin:
			showToast("暂未开放微信支付服务!");
			break;
		case R.id.ll_unionpay:
			showToast("暂未开放银联支付服务!");
			break;
		case R.id.ll_dangmianfu:
			//点击后，底部弹窗，下单成功。上传数据。已下单。
			new PopupWindow(FinalPayActivity.this).showPopupWindow(getResources().getString(R.string.dangmianfudizhi));
			break;
			case R.id.btn_top_back:
				finish();
				break;
		default:
			break;
		}
	}
	private void showToast(String str){
		Toast.makeText(FinalPayActivity.this, str, Toast.LENGTH_SHORT).show();
	}
}
