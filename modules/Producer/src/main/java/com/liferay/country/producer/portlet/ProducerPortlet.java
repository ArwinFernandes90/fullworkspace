package com.liferay.country.producer.portlet;

import com.liferay.country.producer.constants.ProducerPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.xml.namespace.QName;

import org.osgi.service.component.annotations.Component;

/**
* @author arwin.fernandes
*/

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ProducerPortletKeys.Producer,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-publishing-event=producemessage;http://country.com"
	},
	service = Portlet.class
)
public class ProducerPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest renderRequest,
	RenderResponse renderResponse) throws IOException, PortletException {
		// Code written here would be exucuted before view.jsp is called
		System.out.println("renderRequest IN PRODUCER::::"); 
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletURL redirectURL=PortletURLUtil.getCurrent(renderRequest, renderResponse);
		super.doView(renderRequest, renderResponse);
		}
		@ProcessAction(name="processEvent")
		public void process(ActionRequest request, ActionResponse response) {
		// String sampleText = ParamUtil.getString(request, "sampleText");
		long country=ParamUtil.getLong(request, "category");
		String sampleText=String.valueOf(country);
		QName qName = new QName("http://country.com", "producemessage");
		response.setEvent(qName, sampleText);
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
	throws IOException, PortletException {
		/*
		* String configName= ParamUtil.getString(resourceRequest, "ConfigName"); String
		* configValue= ParamUtil.getString(resourceRequest, "ConfigValue");
		* System.out.println("configName"+configName+"configValue"+configValue);
		*/
		String ajaxAction= ParamUtil.getString(resourceRequest, "getallCountries");
		/*
		* resourceRequest.getParameter is deprecated in Liferay 7.1 + versions
		*/
		//String ajaxAction=resourceRequest.getParameter("getallCountries");
		System.out.println("ajaxAction IN PRODUCER::::"+ajaxAction);
		long userId=0;
		try {
		String screenName=UserLocalServiceUtil.getUser(userId).getScreenName();
		User user=UserLocalServiceUtil.getUserByScreenName(20116, screenName);
		userId=user.getUserId();
		} catch (PortalException e) {
		e.printStackTrace();
		}
		JSONArray jArr=JSONFactoryUtil.createJSONArray();
		JSONObject finalgetallCountries =JSONFactoryUtil.createJSONObject();
		List<Country> countryList=CountryServiceUtil.getCountries();
		finalgetallCountries.put("finalJson", countryList);
		PrintWriter pw = resourceResponse.getWriter(); 
		/*
		* resourceResponse.setContentType("application/json");
		* resourceResponse.setCharacterEncoding("UTF-8");
		* resourceResponse.getWriter().write(finalUserDetails.toString());
		* resourceResponse.flushBuffer(); //flush the written json string
		*/
		pw.print(finalgetallCountries.toString());
		pw.flush();
		pw.close();
		super.serveResource(resourceRequest, resourceResponse);
	}

}