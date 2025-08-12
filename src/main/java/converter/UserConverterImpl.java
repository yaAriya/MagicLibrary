package converter;

import entity.User;

public class UserConverterImpl implements UserConverter {

    private static final String PARAMETER = ",";

    private static UserConverterImpl INSTANCE;

    public static UserConverterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserConverterImpl();
        }
        return INSTANCE;
    }

    private UserConverterImpl() {
    }


    @Override
    public User convertLineToUser(String line) {
        String[] parameters = line.split(PARAMETER);

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameters[i].trim();
        }

        User user = new User();
        user.setId(Long.parseLong(parameters[0]));
        user.setName(parameters[1]);
        user.setEmail(parameters[2]);
        user.setAge(Integer.parseInt(parameters[3]));
        return user;
    }

    @Override
    public String convertUserToLine(User user) {
        String idToString = Long.toString(user.getId());
        String ageToString = Integer.toString(user.getAge());

        StringBuilder sb = new StringBuilder();
        sb.append(idToString).append(PARAMETER);
        sb.append(user.getName()).append(PARAMETER);
        sb.append(user.getEmail()).append(PARAMETER);
        sb.append(ageToString);

        if (user.getBooks().size() != 0) {
            long bookId;
            sb.append(",");
            for (int i = 0; i < user.getBooks().size(); i++) {
                bookId = user.getBooks().get(i).getId();
                sb.append(bookId);
                if (i < (user.getBooks().size() - 1)) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }
}
