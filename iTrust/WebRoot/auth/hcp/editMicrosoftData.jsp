<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="java.text.ParseException"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditMicrosoftDataAction"%>
<%@page import="edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Microsoft Data";
%>

<%@include file="/header.jsp" %>

<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	long pid = Long.parseLong(pidString);
	if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
		out.println("pidstring is null");
		response.sendRedirect("/iTrust/aut/getPatientID.jsp?forward=hcp/editMicrosoftData.jsp");
		return;
	}
	MicrosoftBandDAO microsoftDAO = new MicrosoftBandDAO(prodDAO);
	MicrosoftBandBean old = new MicrosoftBandBean();

	String date = (String)session.getAttribute("startDate");
	if(date == null) date = "";
	java.util.Date d1 = null;
	Date d2 = null;
	MicrosoftBandBean microsoftForm = new MicrosoftBandBean();
	if(date.length() == 10) {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.setLenient(false);
			d1 = format.parse(date);
			d2 = new java.sql.Date(d1.getTime());
			microsoftForm.setDate(d2);
			microsoftForm.setPatient(pid);
			microsoftForm.setID(pidString + d2.toString());
		} catch(ParseException e){
			boolean invalidDate = true;
		}
		
	}
	%>
	<form action="editMicrosoftData.jsp" method="post">
		<input type="submit" name="upload" value="Upload Data from File"/>
		
		<input type="submit" name="report" value="Run Summary Report"/>
		<br />
		<br />
		<p>or Select Date to Update Data Manually:</p>
		<input name="startDate"
			value="<%=StringEscapeUtils.escapeHtml("" + (date))%>" size="10">
		<input type=button value="Select Date"
			onclick="displayDatePicker('startDate');">
		<input type="submit" name="confirm" value="Confirm"/>
	</form>
	<br />
	<br />
	<%
	
	if(request.getParameter("upload") != null) {
		response.sendRedirect("/iTrust/auth/hcp/uploadMicrosoftBandFile.jsp");
		return;
	} else if(request.getParameter("report") != null) {
		response.sendRedirect("/iTrust/auth/hcp/graphMicrosoftData.jsp");
		return;
	} else if(request.getParameter("confirm") != null) {
		date = request.getParameter("startDate");
		date = date.trim();
		session.setAttribute("startDate", date);
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.setLenient(false);
			d1 = format.parse(date);
			d2 = new java.sql.Date(d1.getTime());
			microsoftForm.setDate(d2);
			microsoftForm.setPatient(pid);
			microsoftForm.setID(pidString + d2.toString());
		} catch(ParseException e){
			boolean invalidDate = true;
		}
		
		old = microsoftDAO.getWorkoutByID(pidString + d2.toString());
		if(old == null) old = new MicrosoftBandBean();
%>
<form action="editMicrosoftData.jsp" method="post">
<br />
<div align=center>
	    <table class="fTable" align=center>
	      <tr><th colspan=2 >Microsoft Band Workout Data</th></tr>
	      <tr><td class="subHeaderVertical">
		  Calories Burned:
		</td><td>
		<input name="calories" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getCalories()))%>">
		</td></tr>
		
		<tr ><td class="subHeaderVertical">
		  Steps:
		</td><td>
		  <input name="steps" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getSteps()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Floors:
		</td><td>
		  <input name="floors" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getFloors()))%>">
	      </td></tr>
		
		<tr ><td class="subHeaderVertical">
		  Total Miles Moved:
		</td><td>
		  <input name="dist" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getDistance()))%>">
	      </td></tr>
	    
	    <tr ><td class="subHeaderVertical">
		  Lowest Heart Rate:
		</td><td>
		  <input name="hr_lowest" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getHRLowest()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Average Heart Rate:
		</td><td>
		  <input name="hr_average" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getHRAverage()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Highest Heart Rate:
		</td><td>
		  <input name="hr_highest" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getHRHighest()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Minutes of UV Exposure:
		</td><td>
		  <input name="min_uv_exposure" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getMinUVExposure()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Active Hours:
		</td><td>
		  <input name="activity_hours" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getActivityHours()))%>">
	      </tr>
		    </table>
		  </td></tr>
		  <tr>
		  	<td colspan=2 align=center>
		  	<br />
      <input type="submit" name="submitButton" style="font-size: 16pt; font-weight: bold;" value="Submit!" onclick=<%session.setAttribute("startDate", request.getParameter("startDate")); %>>
		  	</td>
		  </tr>
      </table>
      <br />
</form>
<br />
<%	
	}

		if(request.getParameter("submitButton") != null && d2 != null) {
			microsoftForm.setDate(d2);
			microsoftForm.setCalories(Integer.parseInt(request.getParameter("calories")));
			microsoftForm.setSteps(Integer.parseInt(request.getParameter("steps")));
			microsoftForm.setFloors(Integer.parseInt(request.getParameter("floors")));
			microsoftForm.setDistance(Float.parseFloat(request.getParameter("dist")));
			microsoftForm.setHRLowest(Integer.parseInt(request.getParameter("hr_lowest")));
			microsoftForm.setHRAverage(Integer.parseInt(request.getParameter("hr_average")));
			microsoftForm.setHRHighest(Integer.parseInt(request.getParameter("hr_highest")));
			microsoftForm.setMinUVExposure(Integer.parseInt(request.getParameter("min_uv_exposure")));
			microsoftForm.setActivityHours(Integer.parseInt(request.getParameter("activity_hours")));
			
			try {
				old = microsoftDAO.getWorkoutByID(microsoftForm.getID());
				if(old != null) {
					EditMicrosoftDataAction editor = new EditMicrosoftDataAction(prodDAO);
					editor.updateInformation(microsoftForm);
		%>
					<div align=center>
						<span class="iTrustMessage">Information Successfully Updated</span>
					</div>
		<%
				} else {
					microsoftDAO.addNewWorkout(microsoftForm);
		%>
					<div align=center>
						<span class="iTrustMessage">Information Successfully Added</span>
					</div>
		<%
				}
			} catch(FormValidationException e) {
				%>
				<div align=center>
					<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage()) %></span>
				</div>
	<%
			} finally {
				//nothing needed
				
			}
		} else {
//			FitBitDAO microsoftDAO = new FitBitDAO(prodDAO);
//			microsoftForm = microsoftDAO.getWorkoutByID(microsoftForm.getID());
//			used if transaction was added
		}
	
		
%>
<%@include file="/footer.jsp" %>
