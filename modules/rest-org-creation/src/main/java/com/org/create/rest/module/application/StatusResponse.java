package com.org.create.rest.module.application;

public class StatusResponse {
	private boolean statusType;
	private String statusMessage;
	private int statusCode;
	private String statusReason;

	public boolean isStatusType() {
	return statusType;
	}
	public void setStatusType(boolean statusType) {
	this.statusType = statusType;
	}
	public String getStatusMessage() {
	return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
	this.statusMessage = statusMessage;
	}
	public int getStatusCode() {
	return statusCode;
	}
	public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
	}
	public String getStatusReason() {
	return statusReason;
	}
	public void setStatusReason(String statusReason) {
	this.statusReason = statusReason;
	}
}
