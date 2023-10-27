package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText et_teacherId, et_teacherPassword;
    DBOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new DBOpenHelper(this);

        et_teacherId = findViewById(R.id.et_teacherId);
        et_teacherPassword = findViewById(R.id.et_teacherPassword);

        findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_teacherId.getText().toString().trim();
                String pwd = et_teacherPassword.getText().toString().trim();
                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "Please Input Account/Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (helper.checkLogin(id, pwd)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Fail！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegActivity.class));
            }
        });

    }
}