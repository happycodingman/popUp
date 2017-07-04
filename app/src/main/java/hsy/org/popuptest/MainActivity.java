package hsy.org.popuptest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch mSwitch;

    PopupService mPopupservice;

    private ServiceConnection mServiceConnection;

    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mSwitch = (Switch) findViewById(R.id.id_open_floating);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 1234);
                    }
                    startService(new Intent(MainActivity.this, PopupService.class));
                }
                 else stopService(new Intent(MainActivity.this, PopupService.class));
            }
        });


    }

    @Override
    public void onResume() {

        if(!Settings.canDrawOverlays(this))
            mSwitch.setChecked(false);
        super.onResume();
    }

}
