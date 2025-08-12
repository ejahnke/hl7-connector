package amazon.com;

public class PIDSegment {
	
	private String PatientID;
	private String PatientIDList;	
	private String PatientName;
	private String DateOfBirth;	
	private String AdministrativeSex;
	private String Race;		
	private String PatientAddress;		
	private String PrimaryLanguage;		
	private String PatientAccountNumber;		
	private String PatientHealthNumber;		
	private String DeathDateTime;
    private String DeathIndicator;
    private String LastUpdateTime;

    public PIDSegment(String PatientID, String PatientIDList, String PatientName, String DateOfBirth, String AdministrativeSex, String Race, String PatientAddress, String PrimaryLanguage, String PatientAccountNumber, String PatientHealthNumber, String DeathDateTime, String DeathIndicator, String LastUpdateTime) {
        this.AdministrativeSex = AdministrativeSex;
        this.DateOfBirth = DateOfBirth;
        this.DeathDateTime = DeathDateTime;
        this.DeathIndicator = DeathIndicator;
        this.PatientAccountNumber = PatientAccountNumber;
        this.PatientAddress = PatientAddress;
        this.PatientHealthNumber = PatientHealthNumber;
        this.PatientID = PatientID;
        this.PatientIDList = PatientIDList;
        this.PatientName = PatientName;
        this.PrimaryLanguage = PrimaryLanguage;
        this.Race = Race;
        this.LastUpdateTime = LastUpdateTime;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }

    public String getPatientIDList() {
        return PatientIDList;
    }

    public void setPatientIDList(String PatientIDList) {
        this.PatientIDList = PatientIDList;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String PatientName) {
        this.PatientName = PatientName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public String getAdministrativeSex() {
        return AdministrativeSex;
    }

    public void setAdministrativeSex(String AdministrativeSex) {
        this.AdministrativeSex = AdministrativeSex;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String Race) {
        this.Race = Race;
    }

    public String getPatientAddress() {
        return PatientAddress;
    }

    public void setPatientAddress(String PatientAddress) {
        this.PatientAddress = PatientAddress;
    }

    public String getPrimaryLanguage() {
        return PrimaryLanguage;
    }

    public void setPrimaryLanguage(String PrimaryLanguage) {
        if (PrimaryLanguage.length() > 0) {
            this.PrimaryLanguage = PrimaryLanguage;
        } else this.PrimaryLanguage = MyEncoder.getRandomLanguage();
        
    }

    public String getPatientAccountNumber() {
        return PatientAccountNumber;
    }

    public void setPatientAccountNumber(String PatientAccountNumber) {
        this.PatientAccountNumber = PatientAccountNumber;
    }

    public String getPatientHealthNumber() {
        return PatientHealthNumber;
    }

    public void setPatientHealthNumber(String PatientHealthNumber) {
        this.PatientHealthNumber = PatientHealthNumber;
    }

    public String getDeathDateTime() {
        return DeathDateTime;
    }

    public void setDeathDateTime(String DeathDateTime) {
        this.DeathDateTime = DeathDateTime;
    }

    public String getDeathIndicator() {
        return DeathIndicator;
    }

    public void setDeathIndicator(String DeathDateTime) {
        if (DeathDateTime.length() > 0) {
            this.DeathIndicator = "Y";
        } else this.DeathIndicator = "";
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(String LastUpdateDateTime) {
        this.LastUpdateTime = LastUpdateDateTime;
    }

}