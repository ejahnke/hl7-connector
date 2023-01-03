package amazon.com;



public class MyRouteBuilder extends org.apache.camel.builder.RouteBuilder {
	
	private String port;
	private String queue;
	private String bucket;

	
	public void initializeRoute(String port, String queue,String bucket) {
		this.port = port;
		this.queue = queue;
		this.bucket = bucket;
        

	}

	@Override
	public void configure() {

		
		from("mllp:0.0.0.0:" + port)
		.bean(new MyEncoder(bucket), "EncodeBody")
		//.to("aws2-s3://" + bucket + "?useDefaultCredentialsProvider=true&useSSES3=true&keyName=" + keyName )
		//.setBody(simple(bucket + "/" +keyName ))
		//.bean(new MyTransformer(),"TransformContent")		
		.to("aws2-sqs://" + queue + "?useDefaultCredentialsProvider=true" );
	}
}