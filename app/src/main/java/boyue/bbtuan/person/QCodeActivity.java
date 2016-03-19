package boyue.bbtuan.person;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import boyue.bbtuan.R;

public class QCodeActivity extends Activity {
    private ImageView qrCodeIv;
    private Button QcodeBackButt;
    private TextView QcodeTitleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qcode);
        QcodeBackButt=(Button)this.findViewById(R.id.btn_top_back);
        QcodeBackButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        QcodeTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        QcodeTitleTv.setText("软件二维码");
        qrCodeIv=(ImageView)findViewById(R.id.iv_qrcode);
        String myJpgPath = "/mnt/sdcard/BoYue/logo_qrcode.png";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
        qrCodeIv.setImageBitmap(bm);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
