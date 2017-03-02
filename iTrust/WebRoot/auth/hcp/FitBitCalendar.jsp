<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="java.text.ParseException"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.model.fitBit.FitBitBean"%>
<%@page import="edu.ncsu.csc.itrust.action.EditFitBitDataAction"%>
<%@page import="edu.ncsu.csc.itrust.model.fitBit.FitBitDAO"%>
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
pageTitle = "iTrust - FitBit Calendar";
%>

<%@include file="/header.jsp" %>

<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	long pid = Long.parseLong(pidString);
	if (pidString == null || pidString.equals("") || 1 > pidString.length()) {
		out.println("pidstring is null");
		response.sendRedirect("/iTrust/aut/getPatientID.jsp?forward=hcp/FitBitCalendar.jsp");
		return;
	}
	FitBitDAO fitbitDAO = new FitBitDAO(prodDAO);
	FitBitBean old = new FitBitBean();

	String date = (String)session.getAttribute("startDate");
	if(date == null) date = "";
	java.util.Date d1 = null;
	Date d2 = null;
	FitBitBean fitbitForm = new FitBitBean();
	if(date.length() == 10) {
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.setLenient(false);
			d1 = format.parse(date);
			d2 = new java.sql.Date(d1.getTime());
			fitbitForm.setDate(d2);
			fitbitForm.setPatient(pid);
			fitbitForm.setID(pidString + d2.toString());
		} catch(ParseException e){
			boolean invalidDate = true;
		}
		
	}
	%>
	<form action="FitBitCalendar.jsp" method="post">
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
		response.sendRedirect("/iTrust/auth/hcp/fileUpload.xhtml");
		return;
	} else if(request.getParameter("report") != null) {
		response.sendRedirect("/iTrust/auth/hcp/makeFitBitReport.xhtml");
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
			fitbitForm.setDate(d2);
			fitbitForm.setPatient(pid);
			fitbitForm.setID(pidString + d2.toString());
		} catch(ParseException e){
			boolean invalidDate = true;
		}
		old = fitbitDAO.getWorkoutByID(pidString + d2.toString());
		if(old == null) old = new FitBitBean();
%>
<form action="FitBitCalendar.jsp" method="post">
<br />
<div align=center>
	    <table class="fTable" align=center>
	      <tr><th colspan=2 >FitBit Workout Data</th></tr>
	      <tr><td class="subHeaderVertical">
		  Calories Burned:
		</td><td>
		<input name="cal_burned" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getCalBurned()))%>">
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
		  Distance:
		</td><td>
		  <input name="dist" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getDistance()))%>">
	      </td></tr>
	    
	    <tr ><td class="subHeaderVertical">
		  Minutes Sedentary:
		</td><td>
		  <input name="min_seden" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getMinSeden()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Minutes Lightly Active:
		</td><td>
		  <input name="min_light_active" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getMinLightActive()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Minutes Fairly Active:
		</td><td>
		  <input name="min_fair_active" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getMinFairActive()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Minutes Very Active:
		</td><td>
		  <input name="min_very_active" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getMinVeryActive()))%>">
	      </td></tr>
	      
	    <tr ><td class="subHeaderVertical">
		  Activity Calories:
		</td><td>
		  <input name="activity_cal" type="text" value="<%=StringEscapeUtils.escapeHtml("" + (old.getActivityCal()))%>">
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
			fitbitForm.setDate(d2);
			fitbitForm.setCalBurned(Integer.parseInt(request.getParameter("cal_burned")));
			fitbitForm.setSteps(Integer.parseInt(request.getParameter("steps")));
			fitbitForm.setFloors(Integer.parseInt(request.getParameter("floors")));
			fitbitForm.setDistance(Double.parseDouble(request.getParameter("dist")));
			fitbitForm.setMinSeden(Integer.parseInt(request.getParameter("min_seden")));
			fitbitForm.setMinLightActive(Integer.parseInt(request.getParameter("min_light_active")));
			fitbitForm.setMinFairActive(Integer.parseInt(request.getParameter("min_fair_active")));
			fitbitForm.setMinVeryActive(Integer.parseInt(request.getParameter("min_very_active")));
			fitbitForm.setActivityCal(Integer.parseInt(request.getParameter("activity_cal")));
			
			try {
				old = fitbitDAO.getWorkoutByID(fitbitForm.getID());
				if(old != null) {
					EditFitBitDataAction editor = new EditFitBitDataAction(prodDAO);
					editor.updateInformation(fitbitForm);
		%>
					<div align=center>
						<span class="iTrustMessage">Information Successfully Updated</span>
					</div>
		<%
				} else {
					fitbitDAO.addNewWorkout(fitbitForm);
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
//			FitBitDAO fitbitDAO = new FitBitDAO(prodDAO);
//			fitbitForm = fitbitDAO.getWorkoutByID(fitbitForm.getID());
//			used if transaction was added
		}
	
		
%>
<%@include file="/footer.jsp" %>
