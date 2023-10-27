package au.edu.federation.itech3107.studentattendance30395745;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    CourseAdapter adapter;
    List<Course> dataList = new ArrayList<>();
    DBOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBOpenHelper(this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CourseAdapter(dataList, this);
        rv.setAdapter(adapter);

        findViewById(R.id.btn_add_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CourseAddActivity.class));
            }
        });
        findViewById(R.id.btn_add_stu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentAddActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataList.clear();
        List<Course> allCourse = helper.getAllCourse();
        dataList.addAll(allCourse);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_c) {
            startActivity(new Intent(MainActivity.this,AttendanceActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}