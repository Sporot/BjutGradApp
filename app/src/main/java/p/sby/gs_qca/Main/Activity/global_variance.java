package p.sby.gs_qca.Main.Activity;

import android.app.Application;

public class global_variance extends Application {
   private String sessionid;
   private String username;

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
}
