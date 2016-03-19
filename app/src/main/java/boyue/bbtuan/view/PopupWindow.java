
package boyue.bbtuan.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import boyue.bbtuan.R;

public class PopupWindow {
	private Context context;
	private Button btnOk;
	private TextView tipsTv;

	public PopupWindow(Context context) {
		this.context = context;

	}

	public void showPopupWindow(String tips) {
		View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
		final Dialog dialog = new Dialog(context, R.style.common_dialog);
		dialog.setContentView(view);
		dialog.show();
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_pay_ok:

					break;
				default:
					break;
				}
				dialog.dismiss();
			}
		};


		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		tipsTv=(TextView)view.findViewById(R.id.tv_pay_tips);
		tipsTv.setText(tips);
		btnOk = (Button) view.findViewById(R.id.btn_pay_ok);
		btnOk.setOnClickListener(listener);
		Window window = dialog.getWindow();
		window.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.CENTER;
		params.dimAmount=0.1f;
		
		window.setAttributes(params);
		
	}
}
