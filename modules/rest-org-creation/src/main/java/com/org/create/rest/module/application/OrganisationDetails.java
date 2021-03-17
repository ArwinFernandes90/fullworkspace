package com.org.create.rest.module.application;

public class OrganisationDetails {
	private String application;
	private String orgType;
	private String tags;

	public String getTags() {
	return tags;
	}
	public void setTags(String tags) {
	this.tags = tags;
	}
	public String getApplication() {
	return application;
	}
	public void setApplication(String application) {
	this.application = application;
	}
	public String getOrgType() {
	return orgType;
	}
	public void setOrgType(String orgType) {
	this.orgType = orgType;
	}
	@Override
	public String toString() {
	System.out.println("Test");
	return "RestPojo [application=" + application + ", orgType=" + orgType + "]";
	}

}
