package p.sby.gs_qca.Main.Activity;

import android.app.Application;

import org.json.JSONArray;

public class global_variance extends Application {
   private String sessionid;
   private String username;
   private JSONArray department;
   private JSONArray Course;

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
}
