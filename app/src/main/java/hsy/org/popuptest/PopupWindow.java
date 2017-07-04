package hsy.org.popuptest;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.KeyEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pc on 2017/7/4.
 */

public class PopupWindow extends FrameLayout {

    public KeyEventHandler mKeyEventHandler;


    public PopupWindow(Context context) {
        super(context);
    }

    public PopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyEventHandler(KeyEventHandler handler) {
        mKeyEventHandler = handler;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(mKeyEventHandler != null) {
            mKeyEventHandler.onKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    public interface KeyEventHandler{
        void onKeyEvent(KeyEvent event);
    }

}
