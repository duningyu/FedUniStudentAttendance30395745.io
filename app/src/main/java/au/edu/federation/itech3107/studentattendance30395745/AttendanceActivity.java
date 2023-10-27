package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {


    DBOpenHelper helper;
    RecyclerView rv;
    CourseStudentCheckAdapter adapter;
    TextView tv_select_date;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        helper = new DBOpenHelper(this);

        tv_select_date = findViewById(R.id.tv_select_date);
        tv_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();//获取实例
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                //弹窗
                DatePickerDialog pickerDialog = new DatePickerDialog(AttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                                tv_select_date.setText(date);
                            }
                        }, year, month, day);
                pickerDialog.show();
            }
        });
        spinner = findViewById(R.id.spinner);

        List<String> dateList = new ArrayList<>();
        List<Course> allCourse = helper.getAllCourse();
        for (Course course : allCourse) {
            dateList.add(course.getCourse_name());
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateList);
        spinner.setAdapter(stringArrayAdapter);
        List<Student> students = new ArrayList<Student>();
        if (spinner.getCount() > 0) {
            students.addAll(helper.getStudentCourseList(dateList.get(0), spinner.getSelectedItem().toString()));
        }

        adapter = new CourseStudentCheckAdapter(students, this);
        adapter.setType(1);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                students.clear();
                students.addAll(helper.getStudentCourseList(spinner.getSelectedItem().toString(), tv_select_date.getText().toString().trim()));
                adapter.notifyDataSetChanged();
            }
        });


    }
}