package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    Spinner spinner;
    RecyclerView rv;
    DBOpenHelper helper;
    Course course;
    CourseStudentCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        helper = new DBOpenHelper(this);
        course = (Course) getIntent().getSerializableExtra("item");


        spinner = findViewById(R.id.spinner);

        List<String> dateList = new ArrayList<>();
        String start_week = course.getStart_week();
        dateList.add(start_week);
        String[] split = start_week.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        for (int i = 1; i < 12; i++) {
            calendar.set(Calendar.WEEK_OF_MONTH, i);
            String date = String.format("%d-%d-%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            dateList.add(date);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateList);
        spinner.setAdapter(stringArrayAdapter);



        List<Student> students = helper.getStudentList(course.getCourse_name(),spinner.getSelectedItem().toString());
        adapter = new CourseStudentCheckAdapter(students,this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.insertOrUpdate(students,spinner.getSelectedItem().toString(),course.getCourse_name());
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                students.clear();
                students.addAll(helper.getStudentList(course.getCourse_name(),spinner.getSelectedItem().toString()));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}