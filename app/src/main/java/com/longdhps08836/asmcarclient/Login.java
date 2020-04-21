package com.longdhps08836.asmcarclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;


public class Login extends AppCompatActivity {

    private TextView txtForgotPass, txtRegister;
    private Button btnLogin;
    private EditText edtEmail, edtPassword;

    private boolean stateChanged = true;
    private Socket mSocket;


    {
        try {
            mSocket = IO.socket("http://192.168.1.3:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }



    private Emitter.Listener onLoginUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();
            noUi noUi = new noUi(Login.this);
            Log.d("data loginUser", args[0].toString());

                if (data == "true") {

                    noUi.toast("Login Success");
                    Intent intent = new Intent(Login.this, Main_Buttom_Navigation_Layout.class);
                    startActivity(intent);
                    Log.d("Login Success", "Login Success");
                    finish();
                } else {
//                    noUi noUi = new noUi(Login.this);
//                    noUi.toast("Login fail");
                    Log.d("Login fail", "Login fail");
                }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectLayout();
        init();


        mSocket.connect();
        mSocket.on("loginUser", onLoginUser);

    }

    private void connectLayout() {
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void checkLogin(String email, String pass) {
        mSocket.emit("loginUser", email, pass);
    }

    private void init() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()) {
                    checkLogin(email, pass);
                } else {
                    noUi noUi = new noUi(Login.this);
                    noUi.toast("Please enter the crendential!");
                }
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "forgot pass", Toast.LENGTH_SHORT).show();

                if (stateChanged == true) {
                    // reset background to default;
                    txtForgotPass.setTextColor(getColor(R.color.colorBlueLight));

                    Intent intent = new Intent(Login.this, ForgotPassword.class);
                    startActivity(intent);
                } else {
                    txtForgotPass.setTextColor(getColor(R.color.colorWhite));
                }
//                stateChanged = !stateChanged;
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Register", Toast.LENGTH_SHORT).show();
                if (stateChanged == true) {
                    // reset background to default;
                    txtRegister.setTextColor(getColor(R.color.colorBlueFresh));

                    Intent intent = new Intent(Login.this, Register.class);
                    startActivity(intent);
                } else {
                    txtRegister.setTextColor(getColor(R.color.colorBlueLight));
                }
//                stateChanged = !stateChanged;
            }
        });


    }




}
