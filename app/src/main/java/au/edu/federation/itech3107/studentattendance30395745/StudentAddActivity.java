package au.edu.federation.itech3107.studentattendance30395745;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StudentAddActivity extends AppCompatActivity {

    EditText et_student_id, et_student_name;
    Spinner spinner;
    RecyclerView rv;
    DBOpenHelper helper;
    CourseStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        helper = new DBOpenHelper(this);
        List<Course> allCourse = helper.getAllCourse();
        List<String> names = new ArrayList<>();
        for (Course course : allCourse) {
            names.add(course.getCourse_name());
        }

        List<Student> students = new ArrayList<>();
        if (!names.isEmpty()) {
            students.addAll(helper.getStudentListByCourseName(names.get(0)));
        }


        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseStudentAdapter(students, this);
        rv.setAdapter(adapter);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                students.clear();
                students.addAll(helper.getStudentListByCourseName(names.get(position)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_student_name = findViewById(R.id.et_student_name);
        et_student_id = findViewById(R.id.et_student_id);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_student_id.getText().toString().trim();
                String name = et_student_name.getText().toString().trim();
                String item = spinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(id) || TextUtils.isEmpty(item)) {
                    Toast.makeText(StudentAddActivity.this, "Content cannot be empty ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (helper.checkStudentExit(name, id, item)) {
                    Toast.makeText(StudentAddActivity.this, "Add Exit", Toast.LENGTH_SHORT).show();
                    return;
                }

                long res = helper.saveStudent(id, name, item);
                if (res > 0) {
                    Student student = new Student();
                    student.setName(name);
                    student.setStuid(id);
                    student.setCourse_name(item);
                    students.add(student);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(StudentAddActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(StudentAddActivity.this, "Add Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}