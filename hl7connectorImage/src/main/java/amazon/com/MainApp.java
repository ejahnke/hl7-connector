package amazon.com;


import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

    public static void main(String[] args) {
        MyRouteBuilder routeBuilder = new MyRouteBuilder();
        routeBuilder.initializeRoute(System.getenv("HL7_PORT"),System.getenv("HL7_QUEUE"));
        CamelContext ctx = new DefaultCamelContext();
        try {
            ctx.addRoutes(routeBuilder);
            ctx.start();
            //Thread.sleep(5 * 60 * 1000);
            //ctx.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}