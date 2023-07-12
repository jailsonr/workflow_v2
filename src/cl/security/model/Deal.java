package cl.security.model;

import java.util.Objects;

public class Deal {
	private int dealId;
	private int transactionId;
	private int kdbTableId;
	private int retries;
	private int version;
	private String action;
	private String dealStatus;
	private String inputMode;
	private String typeOfEvent;
	private String status;

	public Deal() {

	}

	public Deal(int dealId, int transactionId, int kdbTableId, int retries, int version, String action,
			String dealStatus, String inputMode, String typeOfEvent, String status) {
		this.dealId = dealId;
		this.transactionId = transactionId;
		this.kdbTableId = kdbTableId;
		this.retries = retries;
		this.version = version;
		this.action = action;
		this.dealStatus = dealStatus;
		this.inputMode = inputMode;
		this.typeOfEvent = typeOfEvent;
		this.status = status;
	}

	public int getDealId() {
		return dealId;
	}

	public void setDealId(int dealId) {
		this.dealId = dealId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getKdbTableId() {
		return kdbTableId;
	}

	public void setKdbTableId(int kdbTableId) {
		this.kdbTableId = kdbTableId;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getInputMode() {
		return inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	public String getTypeOfEvent() {
		return typeOfEvent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, dealId, dealStatus, inputMode, kdbTableId, retries, status, transactionId,
				typeOfEvent, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deal other = (Deal) obj;
		return Objects.equals(action, other.action) && dealId == other.dealId
				&& Objects.equals(dealStatus, other.dealStatus) && Objects.equals(inputMode, other.inputMode)
				&& kdbTableId == other.kdbTableId && retries == other.retries && Objects.equals(status, other.status)
				&& transactionId == other.transactionId && Objects.equals(typeOfEvent, other.typeOfEvent)
				&& version == other.version;
	}

	@Override
	public String toString() {
		return "Deal [dealId=" + dealId + ", transactionId=" + transactionId + ", kdbTableId=" + kdbTableId
				+ ", retries=" + retries + ", version=" + version + ", action=" + action + ", dealStatus=" + dealStatus
				+ ", inputMode=" + inputMode + ", typeOfEvent=" + typeOfEvent + ", status=" + status + "]";
	}

}
