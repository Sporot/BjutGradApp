package p.sby.gs_qca.Main.Activity;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

import org.json.JSONArray;

import p.sby.gs_qca.util.ExceptionHandler;

public class global_variance extends Application {

   private String sessionid;
   private String version;
   private String username;
   private String courseid;
   private String classesid;
   private String statusnum;
   private JSONArray department;
   private JSONArray Course;
   private JSONArray aClass;
   private JSONArray Searchlist;
   private JSONArray Draftlist;
   private JSONArray Reportlist;
   private JSONArray exam_deparment;
   private JSONArray exam_course;
   private JSONArray exam_class;
   private JSONArray grad_department;
   private JSONArray grad_major;
   private JSONArray grad_teacher;
   private JSONArray grad_student;

    public void onCreate() {
        super.onCreate();
        //增加这句话
        QbSdk.initX5Environment(this,null);
        ExceptionHandler.getInstance().initConfig(this);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JSONArray getDepartment() {
        return department;
    }

    public void setDepartment(JSONArray department) {
        this.department = department;
    }

    public JSONArray getCourse() {
        return Course;
    }

    public void setCourse(JSONArray course) {
        Course = course;
    }

    public JSONArray getClasses() {
        return aClass;
    }

    public void setclasses(JSONArray aclass) {
        aClass = aclass;
    }

    public String getClassesid (){
        return classesid;
    }

    public String getCourseid() {
        return courseid;
    }

    public String getStatusnum() {
        return statusnum;
    }

    public void setStatusnum(String statusnum) {
        this.statusnum = statusnum;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public void setClassesid(String classesid){this.classesid=classesid;}

    public JSONArray getSearchlist() {
        return Searchlist;
    }

    public void setSearchlist(JSONArray searchlist) {
        Searchlist = searchlist;
    }

    public JSONArray getDraftlist(){
        return Draftlist;
    }

    public void setDraftlist(JSONArray draftlist){
        Draftlist=draftlist;
    }

    public JSONArray getReportlist(){
        return Reportlist;
    }

    public void setReportlist(JSONArray reportlist){
        Reportlist=reportlist;
    }

    public JSONArray getGrad_department() { return grad_department; }

    public void setGrad_department(JSONArray grad_department) { this.grad_department = grad_department; }

    public JSONArray getGrad_major() { return grad_major; }

    public void setGrad_major(JSONArray grad_major) { this.grad_major = grad_major; }


    public JSONArray getGrad_teacher() {
        return grad_teacher;
    }

    public void setGrad_teacher(JSONArray grad_teacher) {
        this.grad_teacher = grad_teacher;
    }

    public JSONArray getGrad_student() {
        return grad_student;
    }

    public void setGrad_student(JSONArray grad_student) {
        this.grad_student = grad_student;
    }

    public JSONArray getExam_deparment() { return exam_deparment; }

    public void setExam_deparment(JSONArray exam_deparment) { this.exam_deparment = exam_deparment; }

    public JSONArray getExam_course() { return exam_course; }

    public void setExam_course(JSONArray exam_course) { this.exam_course = exam_course; }

    public JSONArray getExam_class() {
        return exam_class;
    }

    public void setExam_class(JSONArray exam_class) {
        this.exam_class = exam_class;
    }
}

