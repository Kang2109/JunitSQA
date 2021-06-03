package com.example.btl_toeic.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_toeic.MainActivity;
import com.example.btl_toeic.R;
import com.example.btl_toeic.login.dao.UserDataBase;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView btnRegister;
    private TextView btnForgetPassword;
    private ImageView btnFb;
    private ImageView btnGg;
    private ImageView btnHg;
    private UserDataBase db;

//    protected FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        auth = FirebaseAuth.getInstance();
        db= Room.databaseBuilder(this, UserDataBase.class, "UserDB").allowMainThreadQueries().build();
        initview();
    }
    private void initview() {
        edtEmail= findViewById(R.id.edt_email);
        edtPassword= findViewById(R.id.edt_password);
        btnLogin= findViewById(R.id.btn_login);
        btnRegister= findViewById(R.id.btn_register);
        btnForgetPassword= findViewById(R.id.btn_forgetPassword);


        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //lấy username và password từ edittext
                String email= edtEmail.getText().toString();
                String pass= edtPassword.getText().toString();

                //ktra input
                if(email.isEmpty()){
                    edtEmail.setError("Mời điền vào ô trống");
                    return;
                }
                if(pass.isEmpty()){
                    edtPassword.setError("Mời điền vào ô trống");
                    return;
                }
                if(pass.length()<6){
                    edtPassword.setError("Mời nhập mật khẩu dài từ 6 ký tự");
                    return;
                }

                //lấy từ database tài khoản, kiểm tra nếu ko có thì toast ra
                if(db.userDao().getUser(email, pass).isEmpty()){
                    Toast.makeText(this, "Mời bạn kiểm tra lại tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //nếu có thì vào intent tiếp theo
                Intent employeeManagerIntent= new Intent(LoginActivity.this, MainActivity.class);
                startActivity(employeeManagerIntent);
                finish();
//                auth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(LoginActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
//
//                        } else{
//                            Toast.makeText(LoginActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                break;






                //chuyển màn đăng kí
            case R.id.btn_register:
                Intent registerActivityIntent= new Intent(this, RegisterActivity.class);
                startActivityForResult(registerActivityIntent, 1);
                break;
        }
    }

    //khi đăng kí register xong dữ liệu từ màn register sẽ đc trả về đây
    //nhận dữ iệu từ activity đăng kí
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode== RESULT_OK && data!=null){
                edtEmail.setText(data.getStringExtra("username"));
                edtPassword.setText(data.getStringExtra("password"));
            }
        }
    }
}