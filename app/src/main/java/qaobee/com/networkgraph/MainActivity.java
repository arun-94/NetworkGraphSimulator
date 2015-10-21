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
public class MainActivity extends AppCompatActivity implements Runnable {

    private int height, width;
    private ArrayList<User> users;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        users = new ArrayList<>();
        createUsers();
        setUpGraph();
    }


    private void createUsers() {

        for(int i = 0; i < 6; i++) {
            User a = new User();
            a.setName("User " + (i + 1));
            a.setAge("" + 10);
            a.setEmailId("user" + (i + 1) + "@user.com");
            a.setMobileNo("1234567890");
            users.add(a);
        }
    }

    private void setUpGraph() {
        Graph graph = new SimpleGraph();
        Node v1 = new SimpleNode("" + users.get(0).getName());
        Node v2 = new SimpleNode("" + users.get(1).getName());
        graph.addNode(v1);
        graph.addNode(v2);
        graph.addEdge(new SimpleEdge(v1, v2, "1"));
        users.get(0).addFriend(users.get(1));
        users.get(1).addFriend(users.get(0));

        Node v3 = new SimpleNode("" + users.get(2).getName());
        graph.addNode(v3);
        graph.addEdge(new SimpleEdge(v2, v3, "1"));
        users.get(1).addFriend(users.get(2));
        users.get(2).addFriend(users.get(1));

        Node v4 = new SimpleNode("" + users.get(3).getName());
        graph.addNode(v4);
        graph.addEdge(new SimpleEdge(v3, v4, "1"));
        users.get(3).addFriend(users.get(2));
        users.get(2).addFriend(users.get(3));

        Node v5 = new SimpleNode("" + users.get(4).getName());
        graph.addNode(v5);
        graph.addEdge(new SimpleEdge(v3, v5, "1"));
        users.get(2).addFriend(users.get(4));
        users.get(4).addFriend(users.get(2));

        Node v6 = new SimpleNode("" + users.get(5).getName());
        graph.addNode(v6);
        graph.addEdge(new SimpleEdge(v6, v3, "1"));
        users.get(2).addFriend(users.get(5));
        users.get(5).addFriend(users.get(2));


        View surface = findViewById(R.id.mysurface);
        RelativeLayout parent = (RelativeLayout) surface.getParent();
        int index = parent.indexOfChild(surface);
        parent.removeView(surface);
        graphSurface = new GraphSurfaceView(this.getApplicationContext());
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(width,
                        height);
        // layoutParams.addRule(RelativeLayout.BELOW, R.id.buttonswap);
        parent.addView(graphSurface, index, layoutParams);
        holder = graphSurface.getHolder();
        currentGraphView = new GraphView(this);
        currentGraphView.init(graph, new Dimension(400, 400));
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (locker) {
            //checks if the lockCanvas() method will be success,and if not, will check this statement again
            if (!holder.getSurface().isValid()) {
                continue;
            }
            /** Start editing pixels in this surface.*/
            Canvas canvas = holder.lockCanvas();

            int width = graphSurface.getWidth() - 200;
            int height = graphSurface.getHeight();
            currentGraphView.resize(new Dimension(width, graphSurface.getHeight()- 50));
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
    private void draw(Canvas canvas) {
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
    protected void onPause() {
        super.onPause();
        pause();
    }

    /**
     * Pause void.
     */
    private void pause() {
        //CLOSE LOCKER FOR run();
        locker = false;
        while (true) {
            try {
                //WAIT UNTIL THREAD DIE, THEN EXIT WHILE LOOP AND RELEASE a thread
                thread.join();
            } catch (InterruptedException e) {
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
    protected void onResume() {
        super.onResume();
        resume();
    }

    /**
     * Resume void.
     */
    private void resume() {
        //RESTART THREAD AND OPEN LOCKER FOR run();
        locker = true;
        thread = new Thread(this);
        thread.start();
    }

}
