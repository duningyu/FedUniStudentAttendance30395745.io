package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CourseAddActivity extends AppCompatActivity {

    EditText et_teachername, et_coursename;
    TextView tv_startweek, tv_endweek;
    DBOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        helper = new DBOpenHelper(this);

        et_teachername = findViewById(R.id.et_teachername);
        et_coursename = findViewById(R.id.et_coursename);
        tv_startweek = findViewById(R.id.tv_startweek);
        tv_endweek = findViewById(R.id.tv_endweek);

        tv_startweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取实例
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                //弹窗
                DatePickerDialog pickerDialog = new DatePickerDialog(CourseAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                                tv_startweek.setText(date);

                                Calendar c = Calendar.getInstance();//获取实例
                                c.set(year, month + 1, dayOfMonth);
                                c.add(Calendar.WEEK_OF_MONTH, 11);
                                int y = c.get(Calendar.YEAR);
                                int m = c.get(Calendar.MONTH);
                                int d = c.get(Calendar.DATE);
                                tv_endweek.setText(String.format("%d-%d-%d", y, m + 1, d));
                            }
                        }, year, month, day);
                pickerDialog.show();


            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teachername = et_teachername.getText().toString().trim();
                String coursename = et_coursename.getText().toString().trim();
                String startweek = tv_startweek.getText().toString().trim();
                String endweek = tv_endweek.getText().toString().trim();

                if (TextUtils.isEmpty(teachername) || TextUtils.isEmpty(coursename)) {
                    Toast.makeText(CourseAddActivity.this, "Content cannot be empty ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(startweek) || TextUtils.isEmpty(endweek)) {
                    Toast.makeText(CourseAddActivity.this, "Week cannot be empty ", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean exit = helper.checkCourseExit(coursename);
                if (exit) {
                    Toast.makeText(CourseAddActivity.this, "Course Exit", Toast.LENGTH_SHORT).show();
                    return;
                }

                long res = helper.saveCourse(teachername, coursename, startweek, endweek);
                if (res > 0) {
                    Toast.makeText(CourseAddActivity.this, "Save Course Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CourseAddActivity.this, "Save Course Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}