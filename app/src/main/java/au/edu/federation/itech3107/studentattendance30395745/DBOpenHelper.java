package au.edu.federation.itech3107.studentattendance30395745;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String TB_COURSE = "tb_course";
    public static final String TB_USER = "tb_user";
    public static final String TB_STUDENT = "tb_student";

    public static final String TB_USER_COURSE = "tb_stu_course";

    public DBOpenHelper(@Nullable Context context) {
        super(context, "stu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table " + TB_COURSE + " (id INTEGER  primary key autoincrement,course_name varchar(200) ,start_week varchar(200),end_week varchar(200),user_id varchar(200),teacher_name varchar(200))";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + TB_USER + " (id INTEGER  primary key autoincrement,name varchar(200) ,pwd varchar(200))";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + TB_STUDENT + " (id INTEGER  primary key autoincrement,name varchar(200) ,stu_id varchar(200),course_name varchar(200))";
        sqLiteDatabase.execSQL(sql);

        sql = "create table " + TB_USER_COURSE + " (id INTEGER  primary key autoincrement,course_name INTEGER ,stu_name INTEGER ,statue INTEGER,week varchar(200))";
        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean checkUserExit(String name) {
        Cursor cursor = getReadableDatabase().query(TB_USER, null, "name=?", new String[]{name}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public boolean checkStudentExit(String name, String stu_id, String course_name) {
        Cursor cursor = getReadableDatabase().query(TB_STUDENT, null, "name=? and stu_id=? and course_name=?", new String[]{name, stu_id, course_name}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public boolean checkCourseExit(String course_name) {
        Cursor cursor = getReadableDatabase().query(TB_COURSE, null, "course_name=?", new String[]{course_name}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public boolean checkLogin(String name, String pwd) {
        Cursor cursor = getReadableDatabase().query(TB_USER, null, "name=? and pwd=?", new String[]{name, pwd}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TB_COURSE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Course c = new Course();
            c.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            c.setCourse_name(cursor.getString(cursor.getColumnIndexOrThrow("course_name")));
            c.setTeacher_name(cursor.getString(cursor.getColumnIndexOrThrow("teacher_name")));
            c.setStart_week(cursor.getString(cursor.getColumnIndexOrThrow("start_week")));
            c.setEnd_week(cursor.getString(cursor.getColumnIndexOrThrow("end_week")));
            c.setUser_id(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            courses.add(c);
        }
        return courses;
    }

    public List<Student> getStudentListByCourseName(String course_name) {
        List<Student> courses = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TB_STUDENT, null, "course_name=?", new String[]{course_name}, null, null, null, null);
        while (cursor.moveToNext()) {
            Student c = new Student();
            c.setStuid(cursor.getString(cursor.getColumnIndexOrThrow("stu_id")));
            c.setCourse_name(cursor.getString(cursor.getColumnIndexOrThrow("course_name")));
            c.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));

            courses.add(c);
        }
        return courses;
    }

    public List<Student> getStudentList(String course_name, String week) {
        List<Student> students = new ArrayList<>();


        Cursor cursor = getReadableDatabase().query(TB_STUDENT, null, "course_name=?", new String[]{course_name}, null, null, null);
        while (cursor.moveToNext()) {
            Student c = new Student();
            c.setStuid(cursor.getString(cursor.getColumnIndexOrThrow("stu_id")));
            c.setCourse_name(cursor.getString(cursor.getColumnIndexOrThrow("course_name")));
            c.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            students.add(c);
        }

        Cursor cursor2 = getReadableDatabase().query(TB_USER_COURSE, null, "course_name=? and week=?", new String[]{course_name, week}, null, null, null);
        while (cursor2.moveToNext()) {

            String cname = cursor2.getString(cursor2.getColumnIndexOrThrow("course_name"));
            String stu_name = cursor2.getString(cursor2.getColumnIndexOrThrow("stu_name"));
            for (Student student : students) {
                if (student.getCourse_name().equals(cname) && student.getName().equals(stu_name)) {
                    int statue = cursor2.getInt(cursor2.getColumnIndexOrThrow("statue"));
                    student.setCheck(statue == 1);
                }
            }


        }

        return students;
    }

    public List<Student> getStudentCourseList(String course_name, String week) {
        List<Student> students = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TB_USER_COURSE, null, "course_name=? and week=?", new String[]{course_name,week}, null, null, null);
        while (cursor.moveToNext()) {
            Student c = new Student();
            c.setName(cursor.getString(cursor.getColumnIndexOrThrow("stu_name")));
            c.setCourse_name(cursor.getString(cursor.getColumnIndexOrThrow("course_name")));
            int statue = cursor.getInt(cursor.getColumnIndexOrThrow("statue"));
            c.setCheck(statue==1);
            students.add(c);
        }
        return students;
    }

    public long register(String name, String pwd) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("pwd", pwd);
        return getWritableDatabase().insert(TB_USER, null, cv);
    }


    public long saveCourse(String teachername, String coursename, String startweek, String endweek) {
        ContentValues cv = new ContentValues();
        cv.put("course_name", coursename);
        cv.put("teacher_name", teachername);
        cv.put("start_week", startweek);
        cv.put("end_week", endweek);
        return getWritableDatabase().insert(TB_COURSE, null, cv);
    }

    public long saveStudent(String id, String name, String course_name) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("stu_id", id);
        cv.put("course_name", course_name);
        return getWritableDatabase().insert(TB_STUDENT, null, cv);
    }

    public int delStudent(Student item) {
        return getWritableDatabase().delete(TB_STUDENT, "name=? and stu_id=? and course_name=?", new String[]{item.getName(), item.getStuid(), item.getCourse_name()});
    }
    public int delCourse(Course item) {
        return getWritableDatabase().delete(TB_COURSE, "id=?", new String[]{item.getId()+""});
    }

    public void insertOrUpdate(List<Student> students, String week, String course_name) {
        getWritableDatabase().delete(TB_USER_COURSE, "week=? and course_name=?", new String[]{week, course_name});
        for (Student student : students) {
            ContentValues cv = new ContentValues();
            cv.put("course_name", course_name);
            cv.put("stu_name", student.getName());
            cv.put("statue", student.isCheck() ? 1 : 0);
            cv.put("week", week);
            long insert = getWritableDatabase().insert(TB_USER_COURSE, null, cv);
            System.out.println("insert:" + insert);
        }

    }
}
