package au.edu.federation.itech3107.studentattendance30395745;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseStudentCheckAdapter extends RecyclerView.Adapter<CourseStudentCheckAdapter.ViewHolder> {
    List<Student> studentCourses;
    Context context;
    private int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CourseStudentCheckAdapter(List<Student> studentCourses, Context context) {
        this.studentCourses = studentCourses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_student_check_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Student item = studentCourses.get(position);
        holder.tv_name.setText("Student Name:"+item.getName());
        holder.cb.setChecked(item.isCheck());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setCheck(isChecked);
            }
        });

        if (type == 0) {
            holder.cb.setVisibility(View.VISIBLE);
            holder.tv_statue.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_statue.setVisibility(View.VISIBLE);
            holder.cb.setVisibility(View.INVISIBLE);
        }

        holder.tv_statue.setText(item.isCheck() ? "已出勤" : "未出勤");
    }


    @Override
    public int getItemCount() {
        return studentCourses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_statue;
        CheckBox cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_statue = itemView.findViewById(R.id.tv_statue);
            tv_name = itemView.findViewById(R.id.tv_name);
            cb = itemView.findViewById(R.id.cb);

        }
    }
}
