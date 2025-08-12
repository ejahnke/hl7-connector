package amazon.com;

public class PV1Segment {
	private String assignedPatientLocation;
	private String admissionType;
	private String triageLevel;
	private String attendingProvider;
	private String consultingProvider;
	private String hospitalService;
	private String admittingProvider;
	private String patientType;
	private String dischargeDisposition;
	private String patientStatus;
	private String admissionDateTime;
	private String primaryCareProvider;
	//generated fields below
	private String initialAssessmentDateTime;
	private String leftedDateTime;
	private String dischargeDateTime;
	private String registrationDateTime;
	private String unitTransferDateTime;
	private String triageDateTime;
	private String clinicalDecisionUnitDateTimeIn;
	private String clinicalDecisionUnitDateTimeOut;
	

	public PV1Segment(String assignedPatientLocation, String admissionType, String triageLevel, String attendingProvider, String consultingProvider, String hospitalService, String admittingProvider, String patientType, String dischargeDisposition, String patientStatus, String admissionDateTime, String primaryCareProvider, String initialAssessmentDateTime, String leftedDateTime, String dischargeDateTime, String registrationDateTime, String unitTransferDateTime, String triageDateTime, String clinicalDecisionUnitDateTimeIn, String clinicalDecisionUnitDateTimeOut) {
		this.assignedPatientLocation = assignedPatientLocation;
		this.admissionType = admissionType;
		this.triageLevel = triageLevel;
		this.attendingProvider = attendingProvider;
		this.consultingProvider = consultingProvider;
		this.hospitalService = hospitalService;
		this.admittingProvider = admittingProvider;
		this.patientType = patientType;
		this.dischargeDisposition = dischargeDisposition;
		this.patientStatus = patientStatus;
		this.admissionDateTime = admissionDateTime;
		this.primaryCareProvider = primaryCareProvider;
		//genereated fields below
		this.initialAssessmentDateTime = initialAssessmentDateTime;
		this.leftedDateTime = leftedDateTime;
		this.dischargeDateTime = dischargeDateTime;
		this.registrationDateTime = registrationDateTime;
		this.unitTransferDateTime = unitTransferDateTime;
		this.triageDateTime = triageDateTime;
		this.clinicalDecisionUnitDateTimeIn = clinicalDecisionUnitDateTimeIn;
		this.clinicalDecisionUnitDateTimeOut = clinicalDecisionUnitDateTimeOut;
		
	}

	public String getAdmissionType() {
		return admissionType;
	}

	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}

	public String getAdmissionDateTime() {
		return admissionDateTime;
	}

	public void setAdmissionDateTime(String admissionDateTime) {
		if (admissionDateTime.length() > 0) 
			this.admissionDateTime = admissionDateTime ;
		else this.admissionDateTime = MyEncoder.getToday();
	}

	public String getInitialAssessmentDateTime() {
		return initialAssessmentDateTime;
	}

	public void setInitialAssessmentDateTime(String initialAssessmentDateTime) {
		this.initialAssessmentDateTime = initialAssessmentDateTime;
	}

	public String getAdmittingProvider() {
		return admittingProvider;
	}

	public void setAdmittingProvider(String admittingProvider) {
		this.admittingProvider = admittingProvider;
	}

	public String getAssignedPatientLocation() {
		return assignedPatientLocation;
	}

	public void setAssignedPatientLocation(String assignedPatientLocation) {
		this.assignedPatientLocation = assignedPatientLocation;
	}

	public String getAttendingProvider() {
		return attendingProvider;
	}

	public void setAttendingProvider(String attendingProvider) {
		this.attendingProvider = attendingProvider;
	}

	public String getConsultingProvider() {
		return consultingProvider;
	}

	public void setConsultingProvider(String consultingProvider) {
		this.consultingProvider = consultingProvider;
	}

	public String getLeftedDateTime() {
		return leftedDateTime;
	}

	public void setLeftedDateTime(String leftedDateTime) {
		this.leftedDateTime = leftedDateTime;
	}

	public String getDischargeDateTime() {
		return dischargeDateTime;
	}

	public void setDischargeDateTime(String dischargeDateTime) {
		this.dischargeDateTime = dischargeDateTime;
	}

	public String getDischargeDisposition() {
		return dischargeDisposition;
	}

	public void setDischargeDisposition(String dischargeDisposition) {
		this.dischargeDisposition = dischargeDisposition;
	}

	public String getPrimaryCareProvider() {
		return primaryCareProvider;
	}

	public void setPrimaryCareProvider(String primaryCareProvider) {
		this.primaryCareProvider = primaryCareProvider;
	}

	public String getHospitalService() {
		return hospitalService;
	}

	public void setHospitalService(String hospitalService) {
		this.hospitalService = hospitalService;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(String registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public String getUnitTransferDateTime() {
		return unitTransferDateTime;
	}

	public void setUnitTransferDateTime(String unitTransferDateTime) {
		this.unitTransferDateTime = unitTransferDateTime;
	}

	public String getTriageDateTime() {
		return triageDateTime;
	}

	public void setTriageDateTime(String triageDateTime) {
		this.triageDateTime = triageDateTime;
	}

	public String getClinicalDecisionUnitDateTimeIn() {
		return clinicalDecisionUnitDateTimeIn;
	}

	public void setClinicalDecisionUnitDateTimeIn(String clinicalDecisionUnitDateTimeIn) {
		this.clinicalDecisionUnitDateTimeIn = clinicalDecisionUnitDateTimeIn;
	}

	public String getClinicalDecisionUnitDateTimeOut() {
		return clinicalDecisionUnitDateTimeOut;
	}

	public void setClinicalDecisionUnitDateTimeOut(String clinicalDecisionUnitDateTimeOut) {
		this.clinicalDecisionUnitDateTimeOut = clinicalDecisionUnitDateTimeOut;
	}

	public String getTriageLevel() {
		return triageLevel;
	}

	public void setTriageLevel(String triageLevel) {
		this.triageLevel = triageLevel;
	}
}