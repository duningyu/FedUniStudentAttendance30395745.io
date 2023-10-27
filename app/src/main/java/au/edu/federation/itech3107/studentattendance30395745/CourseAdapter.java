package au.edu.federation.itech3107.studentattendance30395745;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    List<Course> studentCourses;
    Context context;
    DBOpenHelper helper;

    public CourseAdapter(List<Course> studentCourses, Context context) {
        this.studentCourses = studentCourses;
        this.context = context;
        helper = new DBOpenHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Course item = studentCourses.get(position);
        holder.tv_teacher_name.setText(item.getTeacher_name());
        holder.tv_course_name.setText(item.getCourse_name());
        holder.tv_start_week.setText(item.getStart_week());
        holder.tv_end_week.setText(item.getEnd_week());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra("item",item);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Confirm the deletion")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                helper.delCourse(item);
                                studentCourses.remove(item);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentCourses.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_course_name;
        TextView tv_teacher_name;
        TextView tv_start_week;
        TextView tv_end_week;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_teacher_name = itemView.findViewById(R.id.tv_teacher_name);
            tv_start_week = itemView.findViewById(R.id.tv_start_week);
            tv_end_week = itemView.findViewById(R.id.tv_end_week);
        }
    }
}
