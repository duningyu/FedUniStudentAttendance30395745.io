package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {
    EditText et_teacherId, et_teacherPassword, et_teacherPassword2;
    DBOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        helper = new DBOpenHelper(this);

        et_teacherId = findViewById(R.id.et_teacherId);
        et_teacherPassword = findViewById(R.id.et_teacherPassword);
        et_teacherPassword2 = findViewById(R.id.et_teacherPassword2);

        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_teacherId.getText().toString().trim();
                String pwd = et_teacherPassword.getText().toString().trim();
                String pwd2 = et_teacherPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegActivity.this, "Please Input Account/Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pwd.equals(pwd2)) {
                    Toast.makeText(RegActivity.this, "The two passwords are inconsistent!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean exit = helper.checkUserExit(id);
                if (exit) {
                    Toast.makeText(RegActivity.this, "User Exit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (helper.register(id, pwd) > 0) {
                    Toast.makeText(RegActivity.this, "Register Success！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegActivity.this, "Register Fail！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}