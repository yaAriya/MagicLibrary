package converter;

import entity.Book;
import entity.User;

import java.util.Arrays;
import java.util.List;

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
        String[] parameters = Arrays.stream(line.split(PARAMETER))
                .map(String::trim)
                .toArray(String[]::new);

        User user = new User();
        user.setId(Long.parseLong(parameters[0].trim()));
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

        if (!user.getBooks().isEmpty()) {
            sb.append(PARAMETER);
            List<Long> booksId = user.getBooks().stream()
                    .map(Book::getId)
                    .toList();
            sb.append(String.join(PARAMETER, booksId.stream().map(String::valueOf).toList()));
        }
        return sb.toString();
    }
}
