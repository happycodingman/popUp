package hsy.org.popuptest;

/*
 * FloatingBallWindow making by Syusuke/琴声悠扬 on 2016/6/1
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.floatingballwindow.PopupService
 * Description: null
 */

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.CellIdentityLte;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PopupService extends Service implements ViewController.ViewDismissHandler{

    /**
     * 维护ClipboardManager类监听复制过程
     */
    private ClipboardManager cm;
    private ClipboardManager.OnPrimaryClipChangedListener mListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                public void onPrimaryClipChanged() {
                    performClipboardCheck();
                }
            };
    /**
     * 控件window
     */
    private static CharSequence mLastContent = null;
    private ViewController mViewController;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(mListener);

    }


    private void performClipboardCheck(){
        if(cm == null) return;
        ClipData data = cm.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        CharSequence content = item.getText();
        Log.i("Testing My App", "=====copied text : " + item.getText());

        if(TextUtils.isEmpty(content)) return;

        showContent(content);
    }

    private void showContent(CharSequence content) {
        if (mLastContent != null && mLastContent.equals(content) || content == null) {
            return;
        }
        mLastContent = content;

        if (mViewController != null) {
            mViewController.updateContent(content);
        } else {
            mViewController = new ViewController(getApplication(), content);
            mViewController.setViewDismissHandler(this);
            mViewController.show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        cm.removePrimaryClipChangedListener(mListener);

        mLastContent = null;

        if(mViewController != null){
            mViewController.setViewDismissHandler(null);
            mViewController = null;
        }
    }

    @Override
    public void onViewDismiss() {
        mLastContent = null;
        mViewController = null;
    }


}
