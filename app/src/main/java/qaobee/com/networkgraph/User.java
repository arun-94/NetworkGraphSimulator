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
    int age;
    String address;
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

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
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
