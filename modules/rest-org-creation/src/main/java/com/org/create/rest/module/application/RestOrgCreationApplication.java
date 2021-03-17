package com.org.create.rest.module.application;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.CountryConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.RegionConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
/**
* @author arwin.fernandes
*/

@Component(
property = {
JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/org",
JaxrsWhiteboardConstants.JAX_RS_NAME + "=restws.ORG",
"auth.verifier.guest.allowed=true",
"oauth2.scopechecker.type=none"
},
service = Application.class
)
public class RestOrgCreationApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
		}

		@GET
		@Path("/working")
		@Produces("text/plain")
		public String working() {
		return "Org services has been deployed and running!!!!!!";
		}

		@GET
		@Path("/organizations")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String getOrganizationDetails() {
		System.out.println("Inside org method");
		JSONArray jsonResponseArray= JSONFactoryUtil.createJSONArray();
		JSONObject jsonOb= JSONFactoryUtil.createJSONObject();
		try {
		List<Organization> organization= OrganizationLocalServiceUtil.getOrganizations(0,OrganizationLocalServiceUtil.getOrganizationsCount());
		for(Organization subOrg :organization) {
		jsonResponseArray.put(subOrg);
		}

		}catch (Exception e) {
		// TODO: handle exception
		}

		return jsonResponseArray.toString();
		}

		/*
		* @POST
		* 
		* @Path("/genericFuntionality")
		* 
		* @Consumes(MediaType.APPLICATION_JSON)
		* 
		* @Produces(MediaType.APPLICATION_JSON) public String
		* getGenericFunction(@QueryParam("operationType") String operationType,
		* 
		* @QueryParam("application") String application,
		* 
		* @QueryParam("orgType") String orgType,
		* 
		* @QueryParam("orgName") String orgName,
		* 
		* @QueryParam("vendorCode") String vendorCode,
		* 
		* @QueryParam("country") String country,
		* 
		* @QueryParam("region") String region,
		* 
		* @QueryParam("parentOrg") String parentOrg,
		* 
		* @QueryParam("comments") String comments,
		* 
		* @QueryParam("tags") String[] tags )
		*/
		@POST
		@Path("/genericFuntionality")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String getGenericFunction(OrganisationDetails organisationDetails){
		System.out.println("Inside getGenericFunction");
		List responseList = new ArrayList<>();


		String operationType="";
		String application="";
		String orgType="";
		String orgName="";
		String vendorCode="";
		String country="";
		String region="";
		String parentOrg="";
		String comments="";
		String[] tags = null;
		JSONObject obj=JSONFactoryUtil.createJSONObject();
		long companyId=20116;
		StatusResponse statusResponse=new StatusResponse();
		try {
		ServiceContext serviceContext=ServiceContextThreadLocal.getServiceContext();
		serviceContext.setCompanyId(companyId);
		serviceContext.setAssetTagNames(tags);
		long value=0;
		//orgName="Hello";
		comments="testing for workflow";
		System.out.println("OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID"+OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
		System.out.println("CountryConstants.DEFAULT_COUNTRY_ID"+CountryConstants.DEFAULT_COUNTRY_ID);
		System.out.println("RegionConstants.DEFAULT_REGION_ID"+RegionConstants.DEFAULT_REGION_ID);
		System.out.println("ListTypeConstants.ORGANIZATION_STATUS_DEFAULT"+ListTypeConstants.ORGANIZATION_STATUS_DEFAULT);
		Organization organization=OrganizationLocalServiceUtil.addOrganization(20164L, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, orgName, 
		OrganizationConstants.TYPE_ORGANIZATION, RegionConstants.DEFAULT_REGION_ID,CountryConstants.DEFAULT_COUNTRY_ID,
		ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, comments,false, serviceContext);
		//if(organization.getOrganizationId()!=0) {
		if(Validator.isNotNull(organization)) {
		statusResponse.setStatusMessage(StatusCode.SUCCESSMESSAGE);
		statusResponse.setStatusCode(StatusCode.SUCCESS);
		obj.put("SUCCESSMESSAGE", StatusCode.SUCCESSMESSAGE);
		obj.put("SUCCESSCODE", StatusCode.SUCCESS);
		responseList.add(statusResponse);
		}
		else {
		statusResponse.setStatusMessage(StatusCode.FAILUREMESSAGE);
		statusResponse.setStatusCode(StatusCode.FAILURE);
		obj.put("SUCCESSMESSAGE", StatusCode.FAILUREMESSAGE);
		obj.put("SUCCESSCODE", StatusCode.FAILURE);
		responseList.add(statusResponse);
		}
		String customSiteName=organization.getName();
		int publicLayoutPrototypeId=0;
		int privateLayoutPrototypeId=0;
		Group grp=null;
		try {
		grp=GroupLocalServiceUtil.getGroup(serviceContext.getThemeDisplay().getCompanyId(), customSiteName);
		//grp=GroupLocalServiceUtil.getGroup(companyId, customSiteName);
		}catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		}
		if(grp== null) {
		Role adminRole= RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		List<User> adminUsers= UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId());
		PrincipalThreadLocal.setName(adminUsers.get(0).toString());
		PermissionChecker permissionChecker=null;
		try {
		permissionChecker=PermissionCheckerFactoryUtil.create(adminUsers.get(0));
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}catch (Exception e) {
		System.out.print("Exception in site creation");
		System.out.println(e.getMessage());
		e.printStackTrace();
		}
		}
		}catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		}
		return obj.toString();
		}

		protected List<Organization> getAllSubOrganization(Organization org){
		List<Organization> allSubOrg=new ArrayList<Organization>();
		List<Organization> currentLevelSubOrg=new ArrayList<Organization>();
		if(currentLevelSubOrg.size()>0) {
		Iterator<Organization> itr=currentLevelSubOrg.iterator();
		while(itr.hasNext()) {
		allSubOrg.addAll(getAllSubOrganization(itr.next()));
		}
		return allSubOrg;
		}
		else {
		allSubOrg.add(org);
		return allSubOrg;
		}


		}

}