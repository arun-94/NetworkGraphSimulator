package qaobee.com.networkgraph;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class FriendSelectionAdapter extends BaseAdapter
{

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<User> mainDataList = null;
    private ArrayList<User> arraylist;
    private int curPos;

    public FriendSelectionAdapter(Context context, ArrayList<User> mainDataList, int curPos)
    {
        mContext = context;
        this.mainDataList = mainDataList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>(mainDataList);
        this.curPos = curPos;
    }

    @Override
    public int getCount()
    {
        return mainDataList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mainDataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.friend_list_row_item, null);

            holder.colorName = (TextView) view.findViewById(R.id.friend_name);
            holder.colorCheck = (CheckBox) view.findViewById(R.id.friend_check_box);

            view.setTag(holder);
            view.setTag(R.id.friend_name, holder.colorName);
            view.setTag(R.id.friend_check_box, holder.colorCheck);

            holder.colorCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {

                @Override
                public void onCheckedChanged(CompoundButton vw, boolean isChecked)
                {
                    int getPosition = (Integer) vw.getTag();
                    if(vw.isChecked()) {
                        mainDataList.get(curPos).addFriend(mainDataList.get(getPosition));
                    }
                }
            });

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.colorCheck.setTag(position);

        holder.colorName.setText("Friend " + position);
        //holder.colorCode.setText(mainDataList.get(position).getCode());
        holder.colorCheck.setChecked(false);
        return view;
    }

    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        mainDataList.clear();
        if (charText.length() == 0)
        {
            mainDataList.addAll(arraylist);
        }
        else
        {
            for (User wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mainDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder
    {
        protected CheckBox colorCheck;
        protected TextView colorName;
    }

}