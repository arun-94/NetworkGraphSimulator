package qaobee.com.networkgraph;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arun on 21-Oct-15.
 */
public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.VHUser>
{
    private final Context mContext;
    private List<User> users;
    private AppManager manager;

    public UserDetailsAdapter(Context context, ArrayList<User> users, AppManager manager)
    {
        mContext = context;
        if (users != null)
        {
            this.users = users;
        }
        else
        {
            this.users = new ArrayList<User>();
        }

        this.manager = manager;
    }

    public void add(User s, int position)
    {
        position = position == -1 ? getItemCount() : position;
        users.add(position, s);
        notifyItemInserted(position);
    }

    public void remove(int position)
    {
        if (position < getItemCount())
        {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VHUser onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.user_details_list, parent, false);
        return new VHUser(view, new MyCustomEditTextListener(), new MyCustomEditTextListener1(), new MyCustomEditTextListener2(), new MyCustomEditTextListener3(), new MyCustomEditTextListener4());
    }

    @Override
    public void onBindViewHolder(VHUser holder, final int position)
    {
        //User product = users.get(position);
        holder.myCustomEditTextListener.updatePosition(position);
        holder.myCustomEditTextListener1.updatePosition(position);
        holder.myCustomEditTextListener2.updatePosition(position);
        holder.myCustomEditTextListener3.updatePosition(position);
        holder.myCustomEditTextListener4.updatePosition(position);

        holder.name.setText(manager.userList.get(position).getName());
        holder.age.setText(manager.userList.get(position).getAge());
        holder.email.setText(manager.userList.get(position).getEmailId());
        holder.mobile.setText(manager.userList.get(position).getMobileNo());
        holder.address.setText(manager.userList.get(position).getAddress());
    }

    @Override
    public int getItemCount()
    {
        return users.size();
    }


    public static class VHUser extends RecyclerView.ViewHolder
    {
        public final EditText name;
        public final EditText age;
        public final EditText mobile;
        public final EditText email;
        public final EditText address;
        public final View friends;

        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEditTextListener1 myCustomEditTextListener1;
        public MyCustomEditTextListener2 myCustomEditTextListener2;
        public MyCustomEditTextListener3 myCustomEditTextListener3;
        public MyCustomEditTextListener4 myCustomEditTextListener4;


        public VHUser(View view, MyCustomEditTextListener myCustomEditTextListener, MyCustomEditTextListener1 myCustomEditTextListener1, MyCustomEditTextListener2 myCustomEditTextListener2, MyCustomEditTextListener3 myCustomEditTextListener3, MyCustomEditTextListener4 myCustomEditTextListener4)
        {
            super(view);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.myCustomEditTextListener1 = myCustomEditTextListener1;
            this.myCustomEditTextListener2 = myCustomEditTextListener2;
            this.myCustomEditTextListener3 = myCustomEditTextListener3;
            this.myCustomEditTextListener4 = myCustomEditTextListener4;
            name = (EditText) view.findViewById(R.id.editText_user_name);
            name.addTextChangedListener(myCustomEditTextListener);
            age = (EditText) view.findViewById(R.id.editText_user_age);
            age.addTextChangedListener(myCustomEditTextListener1);
            email = (EditText) view.findViewById(R.id.editText_email);
            email.addTextChangedListener(myCustomEditTextListener2);
            mobile = (EditText) view.findViewById(R.id.editText_mobileNumber);
            mobile.addTextChangedListener(myCustomEditTextListener3);
            address = (EditText) view.findViewById(R.id.editText_address);
            address.addTextChangedListener(myCustomEditTextListener4);
            friends = (View) view.findViewById(R.id.ll_select_friends);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher
    {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            manager.userList.get(position).setName(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    private class MyCustomEditTextListener1 implements TextWatcher
    {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            manager.userList.get(position).setAge(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    private class MyCustomEditTextListener2 implements TextWatcher
    {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            manager.userList.get(position).setEmailId(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    private class MyCustomEditTextListener3 implements TextWatcher
    {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            manager.userList.get(position).setMobileNo(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
    private class MyCustomEditTextListener4 implements TextWatcher
    {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            manager.userList.get(position).setAddress(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}


