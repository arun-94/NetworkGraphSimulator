package qaobee.com.networkgraph;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendSelectionActivity extends AppCompatActivity
{


    private static ArrayList<User> requiredUsers;
    private static ArrayList<User> totalUser;
    private SearchView searchView;
    private FriendSelectionAdapter objAdapter;
    private AppManager manager;
    private int curPos;
    @Bind(R.id.data_container) LinearLayout llContainer;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_selection);
        ButterKnife.bind(this);
        curPos = getIntent().getIntExtra("cur_pos", -1);
        manager = (AppManager) getApplication();

        getSupportActionBar().setTitle("Select Friends");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requiredUsers = new ArrayList<>();
        totalUser = manager.userList;

        for (int i = 0; i < totalUser.size(); i++)
        {
            requiredUsers.add(totalUser.get(i));
        }

        objAdapter = new FriendSelectionAdapter(this, totalUser, curPos);

        ListView lv = new ListView(this);
        //lv.setDivider(getResources().getDrawable(R.layout.divider));
        llContainer.addView(lv);
        lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lv.setAdapter(objAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                CheckBox chk = (CheckBox) view.findViewById(R.id.friend_check_box);
                User bean = totalUser.get(position);
                if (!manager.userList.get(curPos).getFriends().contains(bean))
                {
                    chk.setChecked(false);
                }
                else
                {
                    chk.setChecked(true);
                }

            }
        });

    }

    private String getSelectedColorNames()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < requiredUsers.size(); i++)
        {
            if (manager.userList.get(curPos).getFriends().contains(requiredUsers.get(i)))
            {
                sb.append("User " + i);
                sb.append(", ");
            }
        }

        String s = sb.toString().trim();
        if (s.length() > 0) s = s.substring(0, s.length() - 1);

        return s;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent();
        i.putExtra("friend_list", getSelectedColorNames());
        setResult(RESULT_OK, i);
        finish();
    }

}
