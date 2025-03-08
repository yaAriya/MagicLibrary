package DAOClass;

import enity.User;

public class UserDAOImplementation implements UserDAO {
    public User initializeUser(String userText){
        String [] userString = userText.split(",");

        for(String word: userString){
            System.out.println(word);
        }
        User user = new User();
        user.setUserId(Integer.parseInt(userString[0]));
        user.setUserName(userString[1]);
        user.setUserEmail(userString[2]);
        user.setUserAge(Integer.parseInt(userString[3]));
        //user.setBooks();

        User userObj = new User(user.getUserId(),user.getUserName(), user.getUserEmail(), user.getUserAge());

        return userObj;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User read(int ID) {
       /* List<User> users = initializeUsers();
        for(int i = 0; i< users.size(); i++){
            if(users.get(i).getUserID() == ID) {
                return users.get(i);
            }
        }*/
        return null;
    }
}
