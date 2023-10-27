package au.edu.federation.itech3107.studentattendance30395745;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseStudentAdapter extends RecyclerView.Adapter<CourseStudentAdapter.ViewHolder> {
    List<Student> studentCourses;
    Context context;


    public CourseStudentAdapter(List<Student> studentCourses, Context context) {
        this.studentCourses = studentCourses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_student_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Student item = studentCourses.get(position);
        holder.tv_name.setText(item.getName());
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper helper = new DBOpenHelper(context);
                helper.delStudent(item);
                studentCourses.remove(item);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentCourses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        Button btn_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            btn_del = itemView.findViewById(R.id.btn_del);

        }
    }
}
