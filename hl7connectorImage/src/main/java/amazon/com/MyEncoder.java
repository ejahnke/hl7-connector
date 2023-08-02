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
		/*System.out.println("START");
		System.out.println(body);
		System.out.println("END");
		
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
        
        s3.close();
        

		String cleanedBody = body.replaceAll("[\\x0B]", "").replaceAll("[\\x1C]", "");
        String[] msh  = null;
		String[] segments = cleanedBody.split("[\\x0D]");
		
        for (int x=0; x<segments.length; x++) {
        	String[] segmentElements = segments[x].split("\\|");
        	
        	if (segmentElements[0].equals("MSH")) {
        		msh = segmentElements;
        		break;
        	}
        }
        
        MSHSegment mshSegment = new MSHSegment(
        		msh[1],
        		msh[2],
        		msh[3],
        		msh[4],
        		msh[5],
        		msh[6],
        		msh[7],
        		msh[8],
        		msh[9],
        		msh[10],
        		msh[11]
        );

		
		
        
        
        String message;
		JSONObject json = new JSONObject();
		
		try {
			json.put("EncodingCharacters", mshSegment.getEncodingCharacters() );
			json.put("SendingApplication", mshSegment.getSendingApplication() );
			json.put("SendingFacility", mshSegment.getSendingFacility() );
			json.put("ReceivingApplication", mshSegment.getReceivingApplication() );
			json.put("ReceivingFacility", mshSegment.getReceivingFacility()  );
			json.put("DateTimeOfMessage", mshSegment.getDateTimeOfMessage() );
			json.put("Security", mshSegment.getSecurity() );
			json.put("MessageType", mshSegment.getMessageType() );
			json.put("MessageControlId",mshSegment.getMessageControlId()  );
			json.put("ProcessingId", mshSegment.getProcessingId() );
			json.put("VersionId", mshSegment.getVersionId() );
			json.put("msgUrl",bucketName + "/" + keyName);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		message = json.toString();
        System.out.println(message);
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
