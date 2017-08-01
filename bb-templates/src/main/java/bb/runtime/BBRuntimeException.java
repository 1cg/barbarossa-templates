package bb.runtime;

/**
 * Created by eim on 8/1/2017.
 */
public class BBRuntimeException extends RuntimeException {
    public BBRuntimeException(){
    }

    public BBRuntimeException(String message) {
        super(message);
    }

    public BBRuntimeException(Exception e) {
        super(e);
    }
}
