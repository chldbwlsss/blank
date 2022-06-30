package ddobab.blank.exception.customException;

public class UnauthorizedException extends RuntimeException{

    private static final long serialVersionUID = -2336787412341058618L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
