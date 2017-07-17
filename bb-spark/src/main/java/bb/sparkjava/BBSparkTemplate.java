package bb.sparkjava;

import bb.runtime.BaseBBTemplate;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class BBSparkTemplate extends BaseBBTemplate {

    private static ThreadLocal<Request> REQUEST = new ThreadLocal<Request>();
    private static ThreadLocal<Response> RESPONSE = new ThreadLocal<Response>();

    class RawObject {
        Object in;

        RawObject(Object in) {
            this.in = in;
        }
        public String toString() {
            return in.toString();
        }
        public Object getInstance() {
            return in;
        }
    }

    public Response getResponse() {
        return RESPONSE.get();
    }

    public Request getRequest() {
        return REQUEST.get();
    }

    public static void init() {
        before((request, response) -> {
            REQUEST.set(request);
            RESPONSE.set(response);
        });
        afterAfter((request, response) -> {
            REQUEST.set(null);
            RESPONSE.set(null);
        });
    }

    @Override
    public String toS(Object o) {
        if (o == null) {
            return "";
        }
        if (o.getClass() == RawObject.class) {
            RawObject thisO = (RawObject) o;
            return super.toS(thisO.getInstance());
        }
        return o.toString().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot").replaceAll("'", "&#39");
    }

    public Object raw(Object o) {
        return new RawObject(o);
    }
}
