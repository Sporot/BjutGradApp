package p.sby.gs_qca.Activity;

import android.app.Application;

public class global_variance extends Application {
   private String sessionid;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
