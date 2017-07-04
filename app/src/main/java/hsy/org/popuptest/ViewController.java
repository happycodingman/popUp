package hsy.org.popuptest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by pc on 2017/7/4.
 */

final class ViewController implements PopupWindow.KeyEventHandler, View.OnTouchListener{

    private WindowManager mWindowManager;
    private Context mContext;
    private PopupWindow popupWindow;
    private View mContentView;
    private CharSequence mContent;
    private TextView mSourceTextView;
    private TextView mTargetTextView;

    private ViewDismissHandler mViewDismissHandler;



    public ViewController(Context application, CharSequence content) {
        mContent = content;
        mContext = application;
        mWindowManager = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setViewDismissHandler(ViewDismissHandler handler){
        mViewDismissHandler = handler;
    }

    public void updateContent(CharSequence content) {
        mContent = content;
        mSourceTextView.setText(mContent);
    }

    public void show() {
        PopupWindow view = (PopupWindow) View.inflate(mContext, R.layout.popup_view, null);

        mSourceTextView = (TextView) view.findViewById(R.id.pop_view_s_text);
        mTargetTextView = (TextView) view.findViewById(R.id.pop_view_t_text);

        mSourceTextView.setText(mContent);
        mTargetTextView.setText(mContent);

        //弹出窗口整体
        popupWindow = view;

        //保存2个TextView
        mContentView = view.findViewById(R.id.pop_view_content_view);

        popupWindow.setKeyEventHandler(this);
        popupWindow.setOnTouchListener(this);

        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;
        int type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = 0;

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP;

        mWindowManager.addView(popupWindow, params);

    }


    private void removePopView() {

        if(mWindowManager != null && popupWindow != null) {
            mWindowManager.removeView(popupWindow);
        }

        if(mViewDismissHandler != null) {
            mViewDismissHandler.onViewDismiss();
        }

        popupWindow.setOnTouchListener(null);
        popupWindow.setKeyEventHandler(null);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        Rect rect = new Rect();
        mContentView.getGlobalVisibleRect(rect);
        if(!rect.contains(x,y)) {
            removePopView();
        }
        return false;
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            removePopView();
        }
    }

    public interface ViewDismissHandler {
        void onViewDismiss();
    }
}

