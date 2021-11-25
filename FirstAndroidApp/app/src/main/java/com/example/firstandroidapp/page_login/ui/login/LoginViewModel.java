package com.example.SimpleSender.page_login.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.SimpleSender.R;
import com.example.SimpleSender.config.ApplicationConfig;
import com.example.SimpleSender.enums.ServiceEnum;
import com.example.SimpleSender.page_login.data.LoginRepository;
import com.example.SimpleSender.page_login.data.model.LoggedInUser;
import com.example.SimpleSender.utils.HttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LoginViewModel() {
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        //系统生成的带数据源的旧逻辑
        // can be launched in a separate asynchronous job
        //        Result<LoggedInUser> result = loginRepository.login(username, password);
        //
        //        if (result instanceof Result.Success) {
        //            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
        //            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        //        } else {
        //            loginResult.setValue(new LoginResult(R.string.login_failed));
        //        }

        //自定义的新逻辑，异步请求后端登录接口，获取token
        Map<String, String> paramPair = new HashMap<>(2);
        paramPair.put("username", username);
        paramPair.put("password", password);

        HttpUtils.asyncPost(ServiceEnum.LOGIN, paramPair, null, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                loginResult.postValue(LoginResult.failed(500, e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String resultString = response.body().string();
                    JSONObject resultJson = new JSONObject(resultString);
                    String status = resultJson.getString("status");
                    if (!"200".equals(status)) {
                        loginResult.postValue(LoginResult.failed(Integer.valueOf(status), resultString));
                    } else {
                        String token = resultJson.getString("token");

                        if (token == null || token.isEmpty()) {
                            loginResult.postValue(LoginResult.failed(Integer.valueOf(status), resultString));
                        } else {
                            Integer userId = resultJson.getJSONObject("config").getInt("userId");
                            ApplicationConfig.setToken(token);
                            ApplicationConfig.setUser(new LoggedInUser(userId, username, token));
                            loginResult.postValue(LoginResult.succeed(Integer.valueOf(status), resultString));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}