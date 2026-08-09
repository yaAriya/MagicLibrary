package converter;

import entity.User;

public interface UserConverter {
    User convertLineToUser(String line);

    String convertUserToLine(User user);
}
