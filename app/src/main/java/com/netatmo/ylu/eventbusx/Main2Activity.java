package com.netatmo.ylu.eventbusx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.netatmo.ylu.core.EventBusX;
import com.netatmo.ylu.eventbus_annotation.Subscribe;
import com.netatmo.ylu.eventbus_annotation.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    // 发送事件按钮
    public void post(View view) {
        // 发送消息 / 事件
        EventBusX.getDefault().post(new UserInfo("simon", 35));
        finish();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EventBus.getDefault().post(new UserInfo("simon", 35));
//                finish();
//            }
//        }).start();
    }

    // 激活粘性按钮
    public void sticky(View view) {
        EventBusX.getDefault().register(this);
        EventBusX.getDefault().removeStickyEvent(UserInfo.class);
    }

    // Sticky粘性
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sticky(UserInfo user) {
        Log.e("sticky", user.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 示例代码
        UserInfo userInfo = EventBusX.getDefault().getStickyEvent(UserInfo.class);
        if (userInfo != null) {
            UserInfo info = EventBusX.getDefault().removeStickyEvent(UserInfo.class);
            if (info != null) {
                EventBusX.getDefault().removeAllStickyEvents();
            }
        }
        EventBusX.getDefault().unregister(this);
    }
}
