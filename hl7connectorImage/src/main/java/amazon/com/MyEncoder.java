package amazon.com;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import org.json.JSONException;
import org.json.JSONObject;

public class MyEncoder {
	
	private UUID uuid;
	private String uuidAsString;
	private String keyName;
	private String bucketName;
	
	
	public MyEncoder(String bucketName) {
		super();
		this.bucketName = bucketName;
	}


	public String EncodeBody(String body) {
		
		/*System.out.println(body);
		System.out.println(this.bucketName);
		return "";*/
		
		uuid = UUID.randomUUID();
        uuidAsString = uuid.toString();
        keyName = uuidAsString + System.currentTimeMillis() + ".hl7";
        
        Region region = Region.CA_CENTRAL_1;
        S3Client s3 = S3Client.builder()
            .region(region)
            .build();
		
        String result = putS3Object(s3, bucketName, keyName, body);
        //System.out.println("Tag information: "+result);
        s3.close();
        
		//System.out.println("Invoked EncodeBody by route");
		//return Base64.getEncoder().encodeToString(body.getBytes());
        
        String message;
		JSONObject json = new JSONObject();
		
		try {
			json.put("date", System.currentTimeMillis());
			json.put("msgUrl",bucketName + "/" + keyName);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		message = json.toString();
        
		return message;
	}


	private static String putS3Object(S3Client s3, String bucketName, String objectKey, String body) {

        try {
            Map<String, String> metadata = new HashMap<String, String>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

            PutObjectResponse response = s3.putObject(putOb, RequestBody.fromBytes(body.getBytes()));
            return putOb.bucket() + putOb.key();

        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            
        }

        return "";
    }

}
