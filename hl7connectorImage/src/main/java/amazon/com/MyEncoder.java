package amazon.com;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

		String[] pid = null;
		String[] evn = null;
		String[] pv1 = null;
		String[] pv2 = null;
		String[] dg1 = null;
		
        for (int x=0; x<segments.length; x++) {
        	String[] segmentElements = segments[x].split("\\|", -1);

			System.out.println("Segment " + segmentElements[0] + " size: " + segmentElements.length);
			System.out.println("raw segment: " +  segments[x]);
        	
        	if (segmentElements[0].equals("MSH")) {
        		msh = segmentElements;
        		//break;
        	}
			if (segmentElements[0].equals("PID")) {
				pid = segmentElements;
			}if (segmentElements[0].equals("EVN")) {
				evn = segmentElements;
			} if (segmentElements[0].equals("PV1")) {
				pv1 = segmentElements;
			} if (segmentElements[0].equals("PV2")) {
				pv2 = segmentElements;
			} if (segmentElements[0].equals("DG1")) {
				dg1 = segmentElements;
			} 
        }
		MSHSegment mshSegment = new MSHSegment("","","","","","","","","","","");
		PIDSegment pidSegment = new PIDSegment("","","","","","","","","","","","","");
		EVNSegment evnSegment = new EVNSegment("","","");
		PV1Segment pv1Segment = new PV1Segment("","","","","","","","","","",getToday(),"","","","","","","","","");
		PV2Segment pv2Segment = new PV2Segment("","");
		DG1Segment dG1Segment = new DG1Segment("");

        try {
			mshSegment.setEncodingCharacters(msh[1]);
			mshSegment.setSendingApplication(msh[2]);
			mshSegment.setSendingFacility(msh[3]);
			mshSegment.setReceivingApplication(msh[4]);
			mshSegment.setReceivingFacility(msh[5]);
			mshSegment.setDateTimeOfMessage(msh[6]);
			mshSegment.setSecurity(msh[7]);		
			mshSegment.setMessageType(msh[8]);
			mshSegment.setMessageControlId(msh[9]);	
			mshSegment.setProcessingId(msh[10]);
			mshSegment.setVersionId(msh[11]);
		} catch (Exception e) {
			System.out.println("Error ran out of MSH segment: " + e.getMessage());
		};

		try {
			pidSegment.setPatientID(pid[2]);
			pidSegment.setPatientIDList(pid[3]);
			pidSegment.setPatientName(pid[5]);
			pidSegment.setDateOfBirth(pid[7]);
			pidSegment.setAdministrativeSex(pid[8]);
			pidSegment.setRace(pid[10]);
			pidSegment.setPatientAddress(pid[11]);
			pidSegment.setPrimaryLanguage(pid[15]);
			pidSegment.setPatientAccountNumber(pid[18]);
			pidSegment.setPatientHealthNumber(pid[19]);
			pidSegment.setDeathDateTime(pid[29]);
			pidSegment.setDeathIndicator(pid[29]); // using death date for the death indicator initializator method.... careful PID[30]
			pidSegment.setLastUpdateTime(pid[33]);

		} catch (Exception e) {
			System.out.println("Error ran out of PID segment: " + e.getMessage());
		};

		try {
			evnSegment.setEventTypeCode(evn[1]);
			evnSegment.setEventDateTime(evn[2]);
			evnSegment.setOperatorId(evn[5]);
		} catch (Exception e) {
			System.out.println("Error ran out of EVN segment: " + e.getMessage());
		};

		try {
		// generated data should be captured as burden on producers (powerpoint)
			pv1Segment.setAssignedPatientLocation(pv1[3]); //assigned location
			pv1Segment.setAdmissionType(pv1[4]); //admissionType
			pv1Segment.setTriageLevel(pv1[4]); //triage level
			pv1Segment.setAttendingProvider(pv1[7]); //attending provider
			pv1Segment.setConsultingProvider(pv1[9]); //consulting provider
			pv1Segment.setHospitalService(pv1[10]); //hospital service
			pv1Segment.setAdmittingProvider(pv1[17]); //admit provider
			pv1Segment.setPatientType(pv1[18]); //patient type
			pv1Segment.setDischargeDisposition(pv1[36]); // discharge disposition
			pv1Segment.setPatientStatus(pv1[41]); //patient status
			pv1Segment.setAdmissionDateTime(pv1[44]); //Admission Date Time
			pv1Segment.setPrimaryCareProvider(pv1[52]); //PCP
			
		} catch (Exception e) {
			System.out.println("Error ran out of PV1 segment: " + e.getMessage());
		} finally {
			//generate synthetic dates
			System.out.println("Generating synthetic dates for admission date: " + pv1Segment.getAdmissionDateTime());		
			pv1Segment.setInitialAssessmentDateTime(generateInitialAssessmentTime(pv1Segment.getAdmissionDateTime()));		
			pv1Segment.setRegistrationDateTime(generateRegistrationDateTime(pv1Segment.getInitialAssessmentDateTime()));
			pv1Segment.setTriageDateTime(generateTriageDateTime(pv1Segment.getRegistrationDateTime()));
			pv1Segment.setClinicalDecisionUnitDateTimeIn(pv1Segment.getTriageDateTime());
			pv1Segment.setClinicalDecisionUnitDateTimeOut(generateClinicalDecisionUnitDateTimeOut(pv1Segment.getClinicalDecisionUnitDateTimeIn()));
			pv1Segment.setUnitTransferDateTime(generateUnitTransferDateTime(pv1Segment.getClinicalDecisionUnitDateTimeOut()));
			pv1Segment.setLeftedDateTime(generateLeftEDDateTime(pv1Segment.getUnitTransferDateTime()));
			pv1Segment.setDischargeDateTime(generateDischargeDateTime(pv1Segment.getLeftedDateTime()));
		}

		try {
			
			pv2Segment.setAdmitReason(pv2[3]);
			pv2Segment.setModeOfArrivalCode(pv2[38]);
			
		} catch (Exception e) {
			System.out.println("Error ran out of PV2 segment: " + e.getMessage());
		};

		try {
			
			dG1Segment.setChiefComplaint(dg1[3]);
			
			
		} catch (Exception e) {
			System.out.println("Error ran out of DG1 segment: " + e.getMessage());
		};
	   	
        
        String message;
		JSONObject json = new JSONObject();
		
		try {
			//MSH
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
			//json.put("SendingFacilityTerritory","ON");
			json.put("SendingFacilityAmbNumber",mshSegment.getSendingFacility());
			json.put("AbstractId",mshSegment.getMessageControlId());

			//PID
			json.put("PatientID", pidSegment.getPatientID());
			json.put("PatientMRN", pidSegment.getPatientID());
			json.put("PatientIDList", pidSegment.getPatientIDList());
			json.put("PatientName", pidSegment.getPatientName());
			json.put("DateOfBirth", pidSegment.getDateOfBirth());
			json.put("AdministrativeSex", pidSegment.getAdministrativeSex());
			json.put("Race", pidSegment.getRace());
			json.put("PatientAddress", pidSegment.getPatientAddress());
			json.put("PrimaryLanguage", pidSegment.getPrimaryLanguage());
			json.put("PatientAccountNumber", pidSegment.getPatientAccountNumber());
			json.put("PatientHealthNumber", pidSegment.getPatientHealthNumber());
			json.put("DeathDateTime", pidSegment.getDeathDateTime());
			json.put("DeathIndicator", pidSegment.getDeathIndicator());
			json.put("LastUpdateTime", pidSegment.getLastUpdateTime());
					
			//PV1
			json.put("AdmissionType", pv1Segment.getAdmissionType());
			json.put("AdmissionDateTime", pv1Segment.getAdmissionDateTime());
			json.put("InitialAssessmentDateTime", pv1Segment.getInitialAssessmentDateTime());
			json.put("RegistrationDateTime", pv1Segment.getRegistrationDateTime());
			json.put("TriageDateTime", pv1Segment.getTriageDateTime());
			json.put("ClinicalDecisionUnitDateTimeIn", pv1Segment.getClinicalDecisionUnitDateTimeIn());	
			json.put("ClinicalDecisionUnitDateTimeOut", pv1Segment.getClinicalDecisionUnitDateTimeOut());	
			json.put("UnitTransferDateTime", pv1Segment.getUnitTransferDateTime());
			json.put("DischargeDateTime", pv1Segment.getDischargeDateTime());
			json.put("LeftEDDateTime", pv1Segment.getLeftedDateTime());
			json.put("DischargeDisposition", pv1Segment.getDischargeDisposition());
			json.put("PrimaryCareProvider", pv1Segment.getPrimaryCareProvider());
			json.put("AssignedPatientLocation", pv1Segment.getAssignedPatientLocation());
			json.put("AttendingProvider", pv1Segment.getAttendingProvider());
			json.put("ConsultingProvider", pv1Segment.getConsultingProvider());
			json.put("HospitalService", pv1Segment.getHospitalService());
			json.put("PatientStatus", pv1Segment.getPatientStatus());
			json.put("PatientType", pv1Segment.getPatientType());
			json.put("TriageLevel", pv1Segment.getTriageLevel());
			json.put("AdmittingProvider",pv1Segment.getAdmittingProvider());
			

			json.put("msgUrl",bucketName + "/" + keyName);

			//EVN

			json.put("CoderNumber", evnSegment.getOperatorId());

			//PV2
			json.put("ReasonForVisit", pv2Segment.getAdmitReason());
			json.put("ModeOfArrivalCode", pv2Segment.getModeOfArrivalCode());

			//DG1
			json.put("ChiefComplaint", dG1Segment.getChiefComplaint());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		message = json.toString();
        System.out.println("HL7 Message" + message);
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

	public static String getToday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTimeFormatter inboundFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().toString(), inboundFormatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateInitialAssessmentTime(String inputDateTime) {
		try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
		} catch (Exception e) {
			System.out.println("Error generating initial assessment time: " + e.getMessage());
			return "";
		} // (15 to 300 minutes
    }

    private String generateRegistrationDateTime(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateTriageDateTime(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateClinicalDecisionUnitDateTimeOut(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateUnitTransferDateTime(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateLeftEDDateTime(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    private String generateDischargeDateTime(String inputDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, formatter);
        Random random = new Random();
        int minutesToAdd = 15 + random.nextInt(286); // 15 to 300 minutes
        LocalDateTime newDateTime = dateTime.plusMinutes(minutesToAdd);
        return newDateTime.format(formatter);
    }

    public static String getRandomLanguage() {
        String[] languages = {"English", "French", "Spanish", "Mandarin", "Hindi", "Arabic", "Portuguese", "Bengali", "Russian", "Japanese", "Punjabi", "German", "Javanese", "Wu", "Malay"};
        Random random = new Random();
        int chance = random.nextInt(100);
        
        if (chance < 80) return "English";
        if (chance < 85) return "French";
        if (chance < 90) return "Spanish";
        
        return languages[3 + random.nextInt(12)];
    }

}
