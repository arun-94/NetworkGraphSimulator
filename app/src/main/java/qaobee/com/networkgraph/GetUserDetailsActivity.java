package qaobee.com.networkgraph;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetUserDetailsActivity extends AppCompatActivity
{
    @Bind(R.id.editText_number_of_user) EditText userCount;
    @Bind(R.id.ll_select_friends) View selectFriendsLL;
    @Bind(R.id.editText_user_name) EditText userName;
    @Bind(R.id.editText_address) EditText address;
    @Bind(R.id.editText_email) EditText email;
    @Bind(R.id.editText_mobileNumber) EditText mobile;
    @Bind(R.id.editText_user_age) EditText age;
    @Bind(R.id.layoutcontainer) LinearLayout linearLayout;
    @Bind(R.id.userId) TextView textView;
    @Bind(R.id.is_source) CheckBox is_source;

    private int count = 0;
    private int i = 0;
    TextView friendText;


    @OnClick(R.id.submitUsersCount) void submit() {
        count = Integer.parseInt(userCount.getText().toString().trim());
        linearLayout.setVisibility(View.VISIBLE);
        textView.setText("User " + (i));

        for(int j = 0; j < count; j++)
        {
            User a = new User();
            a.setId(j);
            manager.userList.add(a);
        }
    }

    @OnClick(R.id.ll_select_friends)
    void clickSelectColor()
    {
        Intent i1 = new Intent(GetUserDetailsActivity.this, FriendSelectionActivity.class);
        i1.putExtra("cur_pos", i);
        startActivityForResult(i1, 1);
    }

    private RecyclerView.LayoutManager mLayoutManager;
    private UserDetailsAdapter mAdapter;
    private AppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);
        manager = (AppManager) getApplication();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout.setVisibility(View.GONE);
        friendText = ButterKnife.findById(selectFriendsLL, R.id.selector_text);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SaveDetails();
                i++;
                if(i == count)
                {
//                    SaveDetails();

                    for(int j = 0; j < manager.userList.size(); j++)
                    {
                        for (User user : manager.userList.get(j).getFriends())
                        {
                            manager.userList.get(j).contacts.put(user.mobileNo, user);
                        }
                    }
                    Intent i1 = new Intent(GetUserDetailsActivity.this, MainActivity.class);
                    startActivity(i1);
                }
                else {
                    textView.setText("User " + (i));
                    userName.setText("");
                    age.setText("");
                    address.setText("");
                    email.setText("");
                    mobile.setText("");
                    is_source.setChecked(false);
                    friendText.setText("Add Friends");

                }
            }
        });

    }

    private void SaveDetails()
    {
        manager.userList.get(i).setMobileNo(mobile.getText().toString().trim());
        manager.userList.get(i).setName(userName.getText().toString().trim());
        manager.userList.get(i).setAddress(address.getText().toString().trim());
        manager.userList.get(i).setEmailId(email.getText().toString().trim());
        manager.userList.get(i).setAge(age.getText().toString().trim());
        manager.userList.get(i).setIs_Source(is_source.isChecked());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1) {
            String stringExtra = data.getStringExtra("friend_list");
            if (!stringExtra.equals(""))
            {
                friendText.setVisibility(View.VISIBLE);
                friendText.setText(stringExtra);
            }
            else
            {
                friendText.setVisibility(View.GONE);
            }
        }
    }
}
