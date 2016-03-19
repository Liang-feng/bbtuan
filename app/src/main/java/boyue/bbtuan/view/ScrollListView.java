package boyue.bbtuan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ScrollListView extends ScrollView{

    public interface OnGetBottomListener {
        public void onBottom();
    }
    private OnGetBottomListener onGetBottomListener;
    public ScrollListView(Context context) {
        super(context);
    }
    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        //到底部，给listview放松消息，让其获取触摸事件
        if( getChildCount()>=1&&getHeight() + getScrollY() == getChildAt(getChildCount()-1).getBottom()){
            onGetBottomListener.onBottom();
        }
    }
    public interface ScrollViewListener {
        void onScrollChanged(ScrollListView scrollView, int x, int y, int oldx, int oldy);
    }

    public void setBottomListener(OnGetBottomListener listener){
        onGetBottomListener = listener;
    }
}
