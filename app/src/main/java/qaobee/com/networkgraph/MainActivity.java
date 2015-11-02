package qaobee.com.networkgraph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import qaobee.com.networkgraph.graph.GraphSurfaceView;
import qaobee.com.networkgraph.graph.GraphView;
import qaobee.com.networkgraph.graph.beans.Dimension;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements Runnable
{

    private int height, width;
    private AppManager manager;
    private ArrayList<Node> nodes;
    private ArrayList<String> ids;
    private User currentSource;

    private static Queue<User> queue;

    private boolean locker = true;

    private GraphView currentGraphView;

    private GraphSurfaceView graphSurface;

    private SurfaceHolder holder;
    /**
     * The Thread.
     */
    private Thread thread;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (AppManager) getApplication();
        nodes = new ArrayList<>(manager.userList.size());
        ids = new ArrayList<>(manager.userList.size());
        queue =  new LinkedList<>();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        //setUpGraph();

        for (int i = 0; i < manager.userList.size(); i++)
        {
            if(manager.userList.get(i).getIs_Source())
                currentSource = manager.userList.get(i);
        }
        queue.add(currentSource);

        currentSource.messageCount++;

        floodContactsBFS();
       // floodContacts(currentSource);
        //clearGraph();
        setUpGraph();


        String message = "";

        for (int i = 0; i < manager.userList.size(); i++)
        {
            if(manager.userList.get(i).getFriends().size() == 0)
                message += manager.userList.get(i).getName() + ", ";
        }

        message += " cannot be reached as they have no friends";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void floodContactsBFS() {

        while(!queue.isEmpty()) {
            User sourceUser = queue.remove();
            for(String mobileNo: sourceUser.contacts.keySet()) {

                User friend = sourceUser.contacts.get(mobileNo);
                if(friend.messageCount < 2 && !sourceUser.receivedFrom.contains(mobileNo) && !friend.receivedFrom.contains(sourceUser.mobileNo)) {
                    friend.messageCount++;
                    Log.d("temp", friend.getName() + " " + friend.messageCount);
                    friend.receivedFrom.add(sourceUser.mobileNo);
                    if(!queue.contains(friend))
                        queue.add(friend);
                }
            }
        }
    }


    private void floodContacts(User currentSource)
    {
        for(String mobileNo: currentSource.contacts.keySet()) {
            User friend = currentSource.contacts.get(mobileNo);
            if(friend.messageCount < 2 && !currentSource.receivedFrom.contains(mobileNo) && !friend.receivedFrom.contains(currentSource.mobileNo)) {
                friend.messageCount++;
                friend.receivedFrom.add(currentSource.mobileNo);
                floodContacts(friend);
            }
        }
    }



    private void clearGraph()
    {
        nodes.clear();
        ids.clear();
        resume();
    }


    private void setUpGraph()
    {
        Graph graph = new SimpleGraph();

        for (int i = 0; i < manager.userList.size(); i++)
        {
            nodes.add(null);
            ids.add(null);
        }

        for (int i = 0; i < manager.userList.size(); i++)
        {
            if(currentSource.equals(manager.userList.get(i)))
            {
                nodes.set(i, new SimpleNode("SOURCE " + manager.userList.get(i).getName() + "," + manager.userList.get(i).messageCount));
            }
            else {
                nodes.set(i, new SimpleNode("" + manager.userList.get(i).getName() + "," + manager.userList.get(i).messageCount));
            }
            ids.add(manager.userList.get(i).getName());
            graph.add(nodes.get(i));
        }
        for (int i = 0; i < manager.userList.size(); i++)
        {
            ArrayList<User> userFriends = manager.userList.get(i).getFriends();
            Node a = nodes.get(i);
            for (int j = 0; j < manager.userList.size(); j++)
            {
                if(userFriends.contains(manager.userList.get(j))) {
                    Node b = nodes.get(j);
                    graph.addEdge(new SimpleEdge(a, b, "1"));
                }
            }
        }

        if(findViewById(R.id.mysurface) != null)
        {
            View surface = findViewById(R.id.mysurface);
            RelativeLayout parent = (RelativeLayout) surface.getParent();
            int index = parent.indexOfChild(surface);

            parent.removeView(surface);
            graphSurface = new GraphSurfaceView(this.getApplicationContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            // layoutParams.addRule(RelativeLayout.BELOW, R.id.buttonswap);
            parent.addView(graphSurface, index, layoutParams);
        }
        holder = graphSurface.getHolder();
        currentGraphView = new GraphView(this);
        currentGraphView.init(graph, new Dimension(400, 400));
    }


    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        while (locker)
        {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!holder.getSurface().isValid())
            {
                continue;
            }
            /** Start editing pixels in this surface.*/
            Canvas canvas = holder.lockCanvas();

            int width = graphSurface.getWidth() - 200;
            int height = graphSurface.getHeight();
            currentGraphView.resize(new Dimension(width, graphSurface.getHeight() - 50));
            currentGraphView.doLayout();

            draw(canvas);


            // End of painting to canvas. system will paint with this canvas,to the surface.
            holder.unlockCanvasAndPost(canvas);
            locker = false;
        }
    }

    /**
     * This method deals with paint-works. Also will paint something in background
     *
     * @param canvas the canvas
     */
    private void draw(Canvas canvas)
    {
        float scaleFactor = graphSurface.getScaleFactor();

        canvas.drawColor(Color.WHITE);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        currentGraphView.draw(canvas, getResources());
        canvas.restore();
    }

    /**
     * On pause.
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        pause();
    }

    /**
     * Pause void.
     */
    private void pause()
    {
        //CLOSE LOCKER FOR run();
        locker = false;
        while (true)
        {
            try
            {
                //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
                thread.join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    /**
     * On resume.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        resume();
    }

    /**
     * Resume void.
     */
    private void resume()
    {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;
        thread = new Thread(this);
        thread.start();
    }

}
