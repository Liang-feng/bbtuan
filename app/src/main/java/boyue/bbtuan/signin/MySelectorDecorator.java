package boyue.bbtuan.signin;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import boyue.bbtuan.R;

/**
 * Created by mrx on 16-2-16.
 */
public class MySelectorDecorator implements DayViewDecorator {


    private final Drawable drawable;

    public MySelectorDecorator(Context context) {
        this.drawable = context.getResources().getDrawable(R.drawable.cal_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
