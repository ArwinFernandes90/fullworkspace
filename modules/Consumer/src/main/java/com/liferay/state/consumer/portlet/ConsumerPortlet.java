package com.liferay.state.consumer.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.state.consumer.constants.ConsumerPortletKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
		"javax.portlet.name=" + ConsumerPortletKeys.Consumer,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supported-processing-event=producemessage;http://country.com"
	},
	service = Portlet.class
)
public class ConsumerPortlet extends MVCPortlet {
	

		@Override
		public void doView(RenderRequest renderRequest,
		RenderResponse renderResponse) throws IOException, PortletException {
			// Code written here would be exucuted before view.jsp is called
			System.out.println("renderRequest IN Consumer::::");
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			themeDisplay.getScopeGroupId();
			PortletURL redirectURL=PortletURLUtil.getCurrent(renderRequest, renderResponse);
			super.doView(renderRequest, renderResponse);
		}



		@ProcessEvent(qname="{http://country.com}producemessage")
		public void consumeCountry(EventRequest eventRequest, EventResponse eventResponse) {
			Event event=eventRequest.getEvent();
			String txtFrmProducer=(String) event.getValue();
			eventRequest.setAttribute("countryName", txtFrmProducer); 
		}
		
		
		@Override
		public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {
			String configName= ParamUtil.getString(resourceRequest, "ConfigName");
			String configValue= ParamUtil.getString(resourceRequest, "ConfigValue");
			System.out.println("configName"+configName+"configValue"+configValue);
			String ajaxAction= ParamUtil.getString(resourceRequest, "showUserDetails");
			//String ajaxAction=resourceRequest.getParameter("showUserDetails");
			System.out.println("ajaxAction"+ajaxAction);
			JSONObject userDetails =null;
			JSONArray jArr=JSONFactoryUtil.createJSONArray();
			JSONObject finalUserDetails =JSONFactoryUtil.createJSONObject();
			for(int i=0;i<5;i++)
			{
			userDetails =JSONFactoryUtil.createJSONObject();
			userDetails.put("name", "Arwin Fernandes");
			userDetails.put("age", "25");
			userDetails.put("company","accenture");
			jArr.put(userDetails);
			}
			System.out.println("Ajax call is performed"+jArr.toJSONString());
			finalUserDetails.put("finalJson", jArr);
			PrintWriter pw = resourceResponse.getWriter(); 
			/*
			* resourceResponse.setContentType("application/json");
			* resourceResponse.setCharacterEncoding("UTF-8");
			* resourceResponse.getWriter().write(finalUserDetails.toString());
			* resourceResponse.flushBuffer(); //flush the written json string
			*/ 
			pw.print(finalUserDetails.toString());
			pw.flush();
			pw.close();
			super.serveResource(resourceRequest, resourceResponse);
		}

}