package amazon.com;

public class EVNSegment {
	
	private String EventTypeCode;
    private String EventDateTime;
    private String OperatorId;

    public EVNSegment(String EventTypeCode, String EventDateTime, String OperatorId) {
        this.EventDateTime = EventDateTime;
        this.EventTypeCode = EventTypeCode;
        this.OperatorId = OperatorId;
    }

    public String getEventTypeCode() {
        return EventTypeCode;
    }

    public void setEventTypeCode(String EventTypeCode) {
        this.EventTypeCode = EventTypeCode;
    }

    public String getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(String EventDateTime) {
        this.EventDateTime = EventDateTime;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String OperatorId) {
        this.OperatorId = OperatorId;
    }

}