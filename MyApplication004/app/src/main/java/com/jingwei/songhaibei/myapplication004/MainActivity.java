package com.jingwei.songhaibei.myapplication004;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    EditText myEditAccount, myEditPassword;
    TextInputLayout myAccountWrapper, myPasswordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myEditAccount = (EditText) findViewById(R.id.myAccount);
        myEditPassword = (EditText) findViewById(R.id.myPassword);
        myAccountWrapper = (TextInputLayout) findViewById(R.id.accountWrapper);
        myPasswordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        myAccountWrapper.setHint("请输入用户名称");
        myPasswordWrapper.setHint("请输入用户密码");
    }

    //响应单击按钮“登录”
    public void onClickmyBtn1(View v) {
        String myAccount = myEditAccount.getText().toString();
        String myPassword = myEditPassword.getText().toString();
        if (myAccount.length() < 1) {
            myAccountWrapper.setError("用户名称不能是空白!");
        } else if (myPassword.length() < 1) {
            myPasswordWrapper.setError("用户密码不能是空白!");
        } else {
            myAccountWrapper.setErrorEnabled(false);
            myPasswordWrapper.setErrorEnabled(false);
            //doLogin();
        }
    }
}