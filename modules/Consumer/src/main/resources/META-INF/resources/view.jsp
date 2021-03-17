<%@ include file="/init.jsp" %>
<%-- <script src="<%=renderRequest.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
--%>
<!-- <script>
define._amd=define.amd;
define.amd=false;
</script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- <script>
define.amd=define._amd;
</script> -->
<h1> Consumer country value</h1>
<% String countryName=(String)request.getAttribute("countryName");
if(countryName.isEmpty()){%>
Country name typed in is:::: ${countryName}
<%} %>
<portlet:resourceURL var="showUserDetailsUrl">
<portlet:param name="showUserDetails" value="showUserDetails"/>
<</portlet:resourceURL>
<script type="text/javascript">
$(document).ready(function () {
showUserDetails();
});
function showUserDetails(){
AUI().use('aui-io-request', function(A){
A.io.request('${showUserDetailsUrl}', {
method: 'post',
dataType: 'json',
data: {
configName: "Hello",
configValue: "Test" 
},
on: {
success: function() {
var abc=this.get('responseData'); //this will give complete JSON object
var data=this.get('responseData').finalJson; // This will give you the json array
//Iterating using for each loop
$.each(data, function(index, value) {
console.log(value.name);
$("#<portlet:namespace/>name").append('<option value="'+value.name+'">' + value.name+ '</option>');
});
//Iterating using for loop
for (var i = 0; i < data.length; i++) { 
console.log("data values::: "+data[i].name);
$("#<portlet:namespace/>categories").append('<option value="'+data[i].name+'">' + data[i].name+ '</option>');
}
}
}
});
});
}
</script>
<form action="POST">
<button name="test" id="testId" type="button" value="test" onClick="showUserDetails()">button</button>
Country out of form : <select id="<portlet:namespace/>categories" name="categories"> </select>
Name out of form : <select id="<portlet:namespace/>name" name="name"> </select>
</form>
