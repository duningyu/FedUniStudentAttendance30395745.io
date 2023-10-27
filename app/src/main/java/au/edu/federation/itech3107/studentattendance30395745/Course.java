package au.edu.federation.itech3107.studentattendance30395745;

import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String course_name;
    private String teacher_name;
    private String start_week;
    private String end_week;
    private String user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getStart_week() {
        return start_week;
    }

    public void setStart_week(String start_week) {
        this.start_week = start_week;
    }

    public String getEnd_week() {
        return end_week;
    }

    public void setEnd_week(String end_week) {
        this.end_week = end_week;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
