package amazon.com;

public class MyRouteBuilder extends org.apache.camel.builder.RouteBuilder {
	
	private String port;
	private String queue;
	
	public void initializeRoute(String port, String queue) {
		this.port = port;
		this.queue = queue;	
	}

	@Override
	public void configure() {		
		from("mllp:0.0.0.0:" + port)
		.to("aws2-sqs://" + queue + "?useDefaultCredentialsProvider=true" );
	}
}