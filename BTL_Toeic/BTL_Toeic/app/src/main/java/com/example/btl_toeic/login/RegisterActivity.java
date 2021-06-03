package com.example.btl_toeic.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btl_toeic.R;
import com.example.btl_toeic.login.dao.UserDataBase;
import com.example.btl_toeic.login.dao.UserDataBase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegister;
    private TextView btnBackToLogin;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtEmail;
    private UserDataBase db;

//    protected FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db= Room.databaseBuilder(this, UserDataBase.class, "UserDB").allowMainThreadQueries().build();
        initView();
//        auth= FirebaseAuth.getInstance();
    }
    private void initView() {
        btnRegister= findViewById(R.id.btn_register2);
        btnBackToLogin= findViewById(R.id.btn_backToLogin);

        btnRegister.setOnClickListener(this);
        btnBackToLogin.setOnClickListener(this);
        edtUsername = findViewById(R.id.edt_registerUsername);
        edtPassword = findViewById(R.id.edt_registerPassword);
        edtEmail= findViewById(R.id.edt_registerGmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register2:
                //lấy thông tin từ edittext
                String email= edtEmail.getText().toString();
                String pass= edtPassword.getText().toString();
                String name= edtUsername.getText().toString();
                if(email.isEmpty()){
                    edtEmail.setError("Mời điền vào ô trống");
                    return;
                }
                if(name.isEmpty()){
                    edtUsername.setError("Mời điền vào ô trống");
                }
                if(pass.isEmpty()){
                    edtPassword.setError("Mời điền vào ô trống");
                    return;
                }
                if(pass.length()<6){
                    edtPassword.setError("Mời nhập mật khẩu dài từ 6 ký tự");
                    return;
                }

                //lưu vào databse
                db.userDao().insert(new User(email, name, pass));

                //khởi tạo intent trả giữu liệu về cho login
                Intent intent= new Intent();
                intent.putExtra("username", email);
                intent.putExtra("password", pass);
                setResult(RESULT_OK, intent);
                finish();
//                auth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(RegisterActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
//
//                        } else{
//                            Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                break;
            case R.id.btn_backToLogin:
                setResult(RESULT_CANCELED);
                finish();
                break;

        }
    }
}