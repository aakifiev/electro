package electro.model.result;

import java.util.function.Supplier;

public class Result<T> {
    public String message;
    public T data;

    public Result() {
    }

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(final T data) {
        //return new Result<>(null, data);
        return new Result<>(null, data);
    }

    public static <T> Result<T> error(final String message) {
        return new Result<>(message, null);
    }

    public static <T> Result<T> run(final Supplier<T> function) {
        final T result = function.get();
        return Result.success(result);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}