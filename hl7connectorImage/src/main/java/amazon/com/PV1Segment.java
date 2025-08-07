package amazon.com;

public class PV1Segment {
	
	private String admissionType;
	private String admissionDateTime;
	private String initialAssessmentDateTime;
	private String admittingProvider;
	private String assignedPatientLocation;
	private String attendingProvider;
	private String consultingProvider;
	private String leftedDateTime;
	private String dischargeDateTime;
	private String dischargeDisposition;
	private String primaryCareProvider;
	private String hospitalService;
	private String patientStatus;
	private String patientType;
	private String registrationDateTime;
	private String unitTransferDateTime;
	private String triageDateTime;
	private String clinicalDecisionUnitDateTimeIn;
	private String clinicalDecisionUnitDateTimeOut;
	private String triageLevel;

	public PV1Segment(String admissionType, String admissionDateTime, String initialAssessmentDateTime, String admittingProvider, String assignedPatientLocation, String attendingProvider, String consultingProvider, String leftedDateTime, String dischargeDateTime, String dischargeDisposition, String primaryCareProvider, String hospitalService, String patientStatus, String patientType, String registrationDateTime, String unitTransferDateTime, String triageDateTime, String clinicalDecisionUnitDateTimeIn, String clinicalDecisionUnitDateTimeOut, String triageLevel) {
		this.admissionType = admissionType;
		this.admissionDateTime = admissionDateTime;
		this.initialAssessmentDateTime = initialAssessmentDateTime;
		this.admittingProvider = admittingProvider;
		this.assignedPatientLocation = assignedPatientLocation;
		this.attendingProvider = attendingProvider;
		this.consultingProvider = consultingProvider;
		this.leftedDateTime = leftedDateTime;
		this.dischargeDateTime = dischargeDateTime;
		this.dischargeDisposition = dischargeDisposition;
		this.primaryCareProvider = primaryCareProvider;
		this.hospitalService = hospitalService;
		this.patientStatus = patientStatus;
		this.patientType = patientType;
		this.registrationDateTime = registrationDateTime;
		this.unitTransferDateTime = unitTransferDateTime;
		this.triageDateTime = triageDateTime;
		this.clinicalDecisionUnitDateTimeIn = clinicalDecisionUnitDateTimeIn;
		this.clinicalDecisionUnitDateTimeOut = clinicalDecisionUnitDateTimeOut;
		this.triageLevel = triageLevel;
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
		this.admissionDateTime = admissionDateTime;
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