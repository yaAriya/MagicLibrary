package enity;

import java.util.Objects;

public class User {
    private String userName;
    private String userEmail;
    private int userAge;
    private int userID;

    public User (String userName, String userEmail, int userAge, int userID){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userID = userID;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public int getUserAge(){
        return userAge;
    }

    public void setUserAge(int userAge){
        this.userAge = userAge;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode(){
        return Objects.hash(userName, userEmail, userAge, userID);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if ((obj == null) || !(obj instanceof User)) return false;
        User user = (User) obj;
        return Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail) && userAge == user.userAge && userID == user.userID;
    }

    @Override
    public String toString(){
        return userName + ", " + userEmail + ", " + userAge + ", " + userID;
    }
}
