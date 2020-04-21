package com.longdhps08836.asmcarclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class ForgotPassword extends AppCompatActivity {

    Button btnForgotPass;
    EditText edtEmailForgotPass, edtPasswordForgotPass, edtConfirmPasswordForgotPass;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://192.168.1.3:3000/");

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnForgotPass = findViewById(R.id.btnForgotPass);
        edtEmailForgotPass = findViewById(R.id.edtEmailForgotPass);
        edtPasswordForgotPass = findViewById(R.id.edtPasswordForgotPass);
        edtConfirmPasswordForgotPass = findViewById(R.id.edtConfirmPasswordForgotPass);

        init();
        mSocket.connect();
        mSocket.on("updateUser", onUpdateUser);

    }

    private void init() {
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ForgotPassword.this, Login.class);
//                startActivity(intent);

                String email = edtEmailForgotPass.getText().toString().trim();
                String pass = edtPasswordForgotPass.getText().toString().trim();
                String confirm = edtConfirmPasswordForgotPass.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()) {

                    if(pass.equals(confirm)){
                        mSocket.emit("updateUser", email, pass);
                        noUi noUi = new noUi(ForgotPassword.this);
                        noUi.toast("You have successfully update!");
                    }else {
                        noUi noUi = new noUi(ForgotPassword.this);
                        noUi.toast("Password and confirm password does not match");
                    }

                } else {
                    noUi noUi = new noUi(ForgotPassword.this);
                    noUi.toast("Please enter the crendential!");
                }



            }
        });

    }

    private Emitter.Listener onUpdateUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();

            Log.d("dataUpdateUser", args[0].toString());

            if (data == "true") {
                noUi noUi = new noUi(ForgotPassword.this);
                noUi.toast("UpdateUser Success");
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                noUi noUi = new noUi(ForgotPassword.this);
                noUi.toast("UpdateUser fail");
                Log.d("Update fail", "Update fail");
            }
        }
    };

}
