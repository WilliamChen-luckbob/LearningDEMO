package com.example.SimpleSender.page_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SimpleSender.config.ApplicationConfig;
import com.example.SimpleSender.enums.ServiceEnum;
import com.example.SimpleSender.page_login.ui.login.LoginActivity;
import com.example.SimpleSender.utils.HttpUtils;
import com.example.SimpleSender.utils.KeyboardUtil;
import com.example.SimpleSender.R;
import com.example.SimpleSender.utils.ThreadPoolUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private EditText textBox;
    private Button sendButton;
    private final String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBox = findViewById(R.id.editTextTextPersonName);
        sendButton = findViewById(R.id.submit);
        ThreadPoolUtil.submit(this::clearAndFocusTextBox);
    }

    /**
     * 点击提交按钮
     *
     * @param view
     */
    public void main_page_click_submit(View view) {
        if (!ApplicationConfig.hastoken()) {
            showMessageTip("请先登录！才能发送数据！");
            return;
        }

        lockSendButton();
        JSONObject json = new JSONObject();
        try {
            json.put("content", String.valueOf(textBox.getText()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.asyncPost(ServiceEnum.SUBMIT_DATA,
            null,
            json,
            new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("http", e.getMessage());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    showMessageTip(e.getMessage());
                    unlockSendButton();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i("http", "提交成功");
                    String feedbackString = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(feedbackString);
                        if (jsonObject.getInt("status") == 200) {
                            runOnUiThread(() -> {
                                clearAndFocusTextBox();
                                showMessageTip("发送成功");
                            });
                        } else {
                            Log.e("http", feedbackString);
                            showMessageTip("发送失败！" + feedbackString);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessageTip("发送失败！" + e.getMessage());
                    }
                    unlockSendButton();
                }
            });

    }

    private void showMessageTip(String message) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });
    }


    private void clearAndFocusTextBox() {
        runOnUiThread(() -> {
            textBox.setText("");
            textBox.requestFocus();
            KeyboardUtil.hideKeyboard(this);
        });

    }

    private void lockSendButton() {
        runOnUiThread(() -> {
            sendButton.setClickable(false);
            sendButton.setText("发送中...");
        });
    }

    private void unlockSendButton() {
        runOnUiThread(() -> {
            sendButton.setClickable(true);
            sendButton.setText("提交");
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                KeyboardUtil.hideKeyboard(this);
            }
            return super.dispatchTouchEvent(ev);
        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftAndTop = {0, 0};
            //获取输入框当前的location位置，相对于父窗口里的坐标
            view.getLocationInWindow(leftAndTop);
            int left = leftAndTop[0];
            int top = leftAndTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right &&
                event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void doLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
