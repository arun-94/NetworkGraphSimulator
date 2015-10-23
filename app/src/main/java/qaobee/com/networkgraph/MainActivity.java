package qaobee.com.networkgraph;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.RelativeLayout;

import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;

import java.util.ArrayList;

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

    /**
     * The Locker.
     */
    private boolean locker = true;
    /**
     * The Current graph view.
     */
    private GraphView currentGraphView;
    /**
     * The Graph surface.
     */
    private GraphSurfaceView graphSurface;
    /**
     * The Holder.
     */
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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        setUpGraph();
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

            nodes.set(i, new SimpleNode("" + manager.userList.get(i).getName()));
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

        View surface = findViewById(R.id.mysurface);
        RelativeLayout parent = (RelativeLayout) surface.getParent();
        int index = parent.indexOfChild(surface);

        parent.removeView(surface);
        graphSurface = new GraphSurfaceView(this.getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        // layoutParams.addRule(RelativeLayout.BELOW, R.id.buttonswap);
        parent.addView(graphSurface, index, layoutParams);
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
