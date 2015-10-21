package qaobee.com.networkgraph;

import android.app.Application;

import java.util.ArrayList;


public class AppManager extends Application
{

    public ArrayList<User> userList = new ArrayList<>();
    public int curPos = 0;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }
}
