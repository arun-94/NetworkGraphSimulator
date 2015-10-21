package qaobee.com.networkgraph;

import java.util.ArrayList;

/**
 * Created by Arun on 21-Oct-15.
 */
public class User
{
    String name;
    String emailId;
    String mobileNo;
    String age;
    String address;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    int id;
    ArrayList<User> friends = new ArrayList<>();


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public ArrayList<User> getFriends()
    {
        return friends;
    }

    public void setFriends(ArrayList<User> friends)
    {
        this.friends = friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
