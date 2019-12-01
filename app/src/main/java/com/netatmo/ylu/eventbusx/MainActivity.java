package com.netatmo.ylu.eventbusx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.netatmo.ylu.core.EventBusX;
import com.netatmo.ylu.eventbus.apt.EventBusIndex;
import com.netatmo.ylu.eventbus_annotation.Subscribe;
import com.netatmo.ylu.eventbus_annotation.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 163) {
                UserInfo user = (UserInfo) msg.obj;
                tv.setText(user.toString());
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        EventBusX.getDefault().addIndex(new EventBusIndex());
        EventBusX.getDefault().register(this);
    }

    // 跳转按钮
    public void jump(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }

    // 粘性按钮
    public void sticky(View view) {
        EventBusX.getDefault().postSticky(new UserInfo("simon", 35));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void abc(UserInfo user) {
//        tv.setText(user.toString());
//        Log.e("abc", user.toString());
        Message msg = new Message();
        msg.obj = user;
        msg.what = 163;
        handler.sendMessage(msg);
        Log.e("abc", user.toString());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, priority = 1)
    public void abc2(UserInfo user) {
        //tv.setText(user.toString());
        Log.e("abc2", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBusX.getDefault().isRegistered(this)) {
            EventBusX.getDefault().unregister(this);
        }
        EventBusX.clearCaches();
    }
}

