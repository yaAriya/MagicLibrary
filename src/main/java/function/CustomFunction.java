package function;

public interface CustomFunction <T, R>{
    R apply(T t) throws Exception;
}
