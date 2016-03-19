package boyue.bbtuan.signin;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import boyue.bbtuan.R;
import boyue.bbtuan.db.SharedPreferenceDb;

public class SigninActivity extends Activity implements View.OnClickListener {

    private SharedPreferenceDb sb;
    private MaterialCalendarView mCalendarView;
    private TextView top_title;
    private Button btn_top_back;
    private ImageView signin_btn;
    private TextView signin_count;
    private TextView signin_score;
    private CalendarDay today = CalendarDay.today();
    private MediaPlayer mediaPlayer=null;
    private int year = today.getYear();
    private int month = today.getMonth();
    private int day = today.getDay();
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sb = new SharedPreferenceDb(this);
        setContentView(R.layout.activity_signin);
        initView();
        updateSignIn();
    }

    private void initView() {
        top_title = (TextView) findViewById(R.id.tv_top_btntext);
        btn_top_back = (Button) findViewById(R.id.btn_top_back);
        signin_btn = (ImageView) findViewById(R.id.iv_signin);
        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        signin_count = (TextView) findViewById(R.id.tv_signin_count);
        signin_score = (TextView) findViewById(R.id.tv_signin_score);

        top_title.setText("签到");
        btn_top_back.setOnClickListener(this);
        signin_btn.setOnClickListener(this);
        mCalendarView.setCalendarDisplayMode(CalendarMode.MONTHS);
        mCalendarView.setDynamicHeightEnabled(true);
        mCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        mediaPlayer=MediaPlayer.create(SigninActivity.this,R.raw.signed_prize);
        try {
            jsonArray = new JSONArray(sb.getSigninDates());
            if (isSigned(jsonArray, year, month, day)) {
                signin_btn.setImageDrawable(getResources().getDrawable(R.mipmap.ic_signined));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }
    }

    private void updateSignIn() {
        signin_count.setText("签到第"+sb.getSigninCount()+"天");
        signin_score.setText(sb.getSigninScore()+"分");
        try {
            JSONArray jsonArray = new JSONArray(sb.getSigninDates());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                mCalendarView.setDateSelected(CalendarDay.from(object.getInt("y"), object.getInt("m"), object.getInt("d")), true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isSigned(JSONArray jsonArray, int year, int month, int day) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getInt("y") == year && object.getInt("m") == month && object.getInt("d") == day) {
                return true;
            }
        }
        return false;
    }

    private void doSignin() {
        try {
            jsonArray = new JSONArray(sb.getSigninDates());
            if (isSigned(jsonArray, year, month, day)) {
                Toast.makeText(this,"今天已经签到过了，明天再来吧",Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            jsonArray = new JSONArray();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("y", year).put("m", month).put("d", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        sb.setSigninDates(jsonArray.toString());
        sb.setSigninCount(sb.getSigninCount()+1);
        sb.setSigninScore(sb.getSigninScore()+3);
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentBackgroundResource(R.color.transparent)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dlg_signed))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.iv_signin_dlg_close){
                            dialog.dismiss();
                        }
                    }
                })
                .create();
        dialog.show();
        signin_btn.setImageDrawable(getResources().getDrawable(R.mipmap.ic_signined));
        updateSignIn();
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_signin:
                doSignin();
                break;
            case R.id.btn_top_back:
                finish();
                break;
        }
    }

}
