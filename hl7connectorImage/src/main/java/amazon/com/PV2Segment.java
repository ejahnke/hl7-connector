package amazon.com;

public class PV2Segment {
	
	private String AdmitReason;
    private String ModeOfArrivalCode;

    public PV2Segment(String AdmitReason, String ModeOfArrivalCode) {
        this.AdmitReason = AdmitReason;
        this.ModeOfArrivalCode = ModeOfArrivalCode;
    }

    public String getAdmitReason() {
        return AdmitReason;
    }

    public void setAdmitReason(String AdmitReason) {
        this.AdmitReason = AdmitReason;
    }

    public String getModeOfArrivalCode() {
        return ModeOfArrivalCode;
    }

    public void setModeOfArrivalCode(String ModeOfArrivalCode) {
        this.ModeOfArrivalCode = ModeOfArrivalCode;
    }

    
}