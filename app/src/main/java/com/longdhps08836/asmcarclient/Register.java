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

//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;

public class Register extends AppCompatActivity {

    Button btnRegister;
    EditText edtEmailRegister, edtPasswordRegister, edtConfirmPasswordRegister;


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
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtConfirmPasswordRegister = findViewById(R.id.edtConfirmPasswordRegister);

        init();

        mSocket.connect();
        mSocket.on("registerUser", onRegisterUser);
    }


    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmailRegister.getText().toString().trim();
                String pass = edtPasswordRegister.getText().toString().trim();
                String confirm = edtConfirmPasswordRegister.getText().toString().trim();

                if (!email.isEmpty() && !pass.isEmpty()) {

                    if(pass.equals(confirm)){
                        mSocket.emit("registerUser", email, pass);
                        noUi noUi = new noUi(Register.this);
                        noUi.toast("You have successfully registered!");
                    }else {
                        noUi noUi = new noUi(Register.this);
                        noUi.toast("Password and confirm password does not match");
                    }

                } else {
                    noUi noUi = new noUi(Register.this);
                    noUi.toast("Please enter the crendential!");
                }


            }
        });
    }


    private Emitter.Listener onRegisterUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();

            Log.d("dataRegister User", args[0].toString());

            if (data == "true") {
                noUi noUi = new noUi(Register.this);
                noUi.toast("Register Success");
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                noUi noUi = new noUi(Register.this);
                noUi.toast("Register fail");
                Log.d("Register fail", "Register fail");
            }
        }
    };

}
