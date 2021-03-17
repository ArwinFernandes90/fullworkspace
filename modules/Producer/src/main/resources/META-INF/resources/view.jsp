<%@ include file="/init.jsp" %>

<%@page import="com.liferay.portal.kernel.model.Country"%>
<%@page import="com.liferay.portal.kernel.service.CountryServiceUtil"%>
<%@page import="java.util.List"%>
<!-- <script>
define._amd=define.amd;
define.amd=false;
</script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- <script>
define.amd=define._amd;
</script> -->
<%-- <script src="<%=renderRequest.getContextPath()%>/js/jquery-3.5.1.min.js"></script> --%>
<portlet:actionURL var="actionUrl" name="processEvent"/>
<portlet:resourceURL var="getCountriesUrl">
<portlet:param name="getallCountries" value="getallCountries"/>
</portlet:resourceURL>
<script type="text/javascript">
$(document).ready(function () {
alert(" DOC .ready ");
getallCountries();
});
function getallCountries(){
alert("Hello");
AUI().use('aui-io-request', function(A){
A.io.request('${getCountriesUrl}', {
method: 'post',
dataType: 'json',
data: {
configName: "Hello",
configValue: "Test" 
},
on: {
success: function() {
var data=this.get('responseData'); //This will give the complete json obj
var arrFrmJsnObj=this.get('responseData').finalJson; //This will give json array from Json obj
$.each(arrFrmJsnObj, function(index, value) {
$("#<portlet:namespace/>categories").append('<option value="'+value.name+'">' + value.name+ '</option>');
});
}
}
});
});
}
</script>

		<%List<Country> countryList=CountryServiceUtil.getCountries();
		System.out.println("countryList"+countryList.toString());
		%>
		
		
		<form action="<%=actionUrl%>" name="studentForm" method="POST">
			SampleText: <input type ="text" name ="<portlet:namespace/>sampleText" id ="<portlet:namespace/>sampleText">
			<br />
			Country : <select name="country">
			<%for(int i=0;i<countryList.size();i++){
			%>
			<option value="<%=countryList.get(i).getCountryId()%>"><%=countryList.get(i).getName()%></option>
			<%
			}
			%>
			</select>
			Country out of form : <select id="<portlet:namespace/>categories" name="categories"> </select>
			<input type = "submit" value = "Submit" />
		</form>
