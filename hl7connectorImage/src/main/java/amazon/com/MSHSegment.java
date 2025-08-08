package amazon.com;

public class MSHSegment {
	
	private String EncodingCharacters;
	private String SendingApplication;	
	private String SendingFacility;
	private String ReceivingApplication;	
	private String ReceivingFacility;
	private String DateTimeOfMessage;		
	private String Security;		
	private String MessageType;		
	private String MessageControlId;		
	private String ProcessingId;		
	private String VersionId;

	public MSHSegment(String encodingCharacters, String sendingApplication, String sendingFacility,
			String receivingApplication, String receivingFacility, String dateTimeOfMessage, String security,
			String messageType, String messageControlId, String processingId, String versionId) {
		super();
		EncodingCharacters = encodingCharacters;
		SendingApplication = sendingApplication;
		SendingFacility = sendingFacility;
		ReceivingApplication = receivingApplication;
		ReceivingFacility = receivingFacility;
		DateTimeOfMessage = dateTimeOfMessage;
		Security = security;
		MessageType = messageType;
		MessageControlId = messageControlId;
		ProcessingId = processingId;
		VersionId = versionId;
	}
	public String getEncodingCharacters() {
		return EncodingCharacters;
	}
	public String getSendingApplication() {
		return SendingApplication;
	}
	public String getSendingFacility() {
		return SendingFacility;
	}
	public String getReceivingApplication() {
		return ReceivingApplication;
	}
	public String getReceivingFacility() {
		return ReceivingFacility;
	}
	public String getDateTimeOfMessage() {
		return DateTimeOfMessage;
	}
	public String getSecurity() {
		return Security;
	}
	public String getMessageType() {
		return MessageType;
	}
	public String getMessageControlId() {
		return MessageControlId;
	}
	public String getProcessingId() {
		return ProcessingId;
	}
	public String getVersionId() {
		return VersionId;
	}

    public void setEncodingCharacters(String EncodingCharacters) {
        this.EncodingCharacters = EncodingCharacters;
    }

    public void setSendingApplication(String SendingApplication) {
        this.SendingApplication = SendingApplication;
    }

    public void setSendingFacility(String SendingFacility) {
        this.SendingFacility = SendingFacility;
    }

    public void setReceivingApplication(String ReceivingApplication) {
        this.ReceivingApplication = ReceivingApplication;
    }

    public void setReceivingFacility(String ReceivingFacility) {
        this.ReceivingFacility = ReceivingFacility;
    }

    public void setDateTimeOfMessage(String DateTimeOfMessage) {
        this.DateTimeOfMessage = DateTimeOfMessage;
    }

    public void setSecurity(String Security) {
        this.Security = Security;
    }

    public void setMessageType(String MessageType) {
        this.MessageType = MessageType;
    }

    public void setMessageControlId(String MessageControlId) {
        this.MessageControlId = MessageControlId;
    }

    public void setProcessingId(String ProcessingId) {
        this.ProcessingId = ProcessingId;
    }

    public void setVersionId(String VersionId) {
        this.VersionId = VersionId;
    }
}
	
