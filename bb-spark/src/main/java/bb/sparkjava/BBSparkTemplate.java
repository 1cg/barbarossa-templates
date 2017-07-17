package bb.sparkjava;

import bb.runtime.BaseBqBTemplate;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class BBSparkTemplate extends BaseBBTemplate {

    private static ThreadLocal<Request> REQUEST = new ThreadLocal<Request>();
    private static ThreadLocal<Response> RESPONSE = new ThreadLocal<Response>();

    private boolean isRaw = false;

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
        if (isRaw) {
            isRaw = false;
            return super.toS(o);
        }
        return super.toS(o).replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot").replaceAll("'", "&#39");
    }

    public Object raw(Object o) {
        isRaw = true;
        return o;
    }
}
