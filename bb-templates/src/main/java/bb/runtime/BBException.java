package bb.runtime;

/**
 * Created by eim on 8/1/2017.
 */
public class BBException extends RuntimeException {
    public BBException(){
    }

    public BBException(String message) {
        super(message);
    }

    public BBException(Exception e) {
        super(e);
    }
}
