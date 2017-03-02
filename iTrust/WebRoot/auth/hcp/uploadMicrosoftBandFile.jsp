<%@page import="java.io.DataInputStream"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.InputStream"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.action.AddMicrosoftFileAction"%> 
<%@page import="edu.ncsu.csc.itrust.exception.CSVFormatException"%>
<%@page import="edu.ncsu.csc.itrust.exception.AddMicrosoftBandFileException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="org.apache.commons.fileupload.DefaultFileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@page import="org.apache.commons.fileupload.FileItem" %>

<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Upload Microsoft Band File";
%>

<%@include file="/header.jsp" %>

<%
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	AddMicrosoftFileAction amfa = null;

	if(!isMultipart){
%>
	<p>Upload a CSV file. The first line of the file must contain header fields. 
	<ul>
		<li>REQUIRED FIELDS</li>
		<li><b>Date</li>
		<li>Steps</li>
		<li>Calories</li>
		<li>HR_Lowest</li>
		<li>HR_Highest</li>
		<li>HR_Average</li>
		<li>Total_miles_Moved</li>
		<li>Active_Hours</li>
		<li>Floors_Climbed</li>
		<li>UV_Exposure_Minutes</li>
	</ul>
	</p>
		<form name="mainForm" enctype="multipart/form-data" action="uploadMicrosoftBandFile.jsp" method="post">
			<table class="fTable mainTable" align="center">
				<tr><th colspan="3">Choose File</th></tr>		
				<tr><td>
					<input name="microsoftFile" type="file"/>
				</td>
				<td>
					<input type="submit" value="Send File" id="sendFile" name="sendFile">
				</td>
			</tr></table>
		</form>
		<%
			}else{
				String headerMessage = "Upload Successful";
				
				String error = "";
				Boolean fatal = false;
				List<String> results = new ArrayList<String>();	
				InputStream fileStream = null;
				boolean ignore = true;
				FileItemFactory factory = new DefaultFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				
				while (iter.hasNext()) {
				    FileItem item = (FileItem) iter.next();
				    fileStream = items.get(0).getInputStream();
				}

				if(fileStream!=null){
					try{
						amfa = new AddMicrosoftFileAction(fileStream, prodDAO, Long.parseLong((String)session.getAttribute("pid")));
					}catch(CSVFormatException e){
						fatal = true;
						error = e.getMessage();
					}catch(AddMicrosoftBandFileException e){
						fatal = true;
						error = e.getMessage();
					}
				} else{
					fatal = true;
					error = "Please choose a file to upload.";
				}
				
			
				String headerColor = "#00CCCC";
				if(fatal || amfa == null){
					headerMessage = "File upload was unsuccessful. "+ error;
					headerColor = "#ff3333";
				}else if(amfa.getErrors().hasErrors()){
					headerMessage = "File upload was successful, but some patients could not be added. Please review the following errors:<br />";
					List<String> errors=amfa.getErrors().getMessageList();
					for(int i=0;i<errors.size();i++){
						headerMessage=headerMessage+StringEscapeUtils.escapeHtml("" + (errors.get(i)))+"<br />";
				}
					headerColor = "#ff3333";
				}
		%>
	
	<div align=center>
	<span class="iTrustMessage" style="color:<%=headerColor %>"><%= headerMessage %></span>
	<br />
	
	<%
	if(amfa != null){
	%>
		<table class="fTable">
				<tr>
					<th>Patient</th>
					<th>Date</th>
					<th>Workout ID</th>
					<th>Calories</th>
				</tr>

		<%
		for(int i = 0; i < amfa.getPatients().size(); i++){
			%>
			<tr>
				<td><%= StringEscapeUtils.escapeHtml("" + (amfa.getPatients().get(i).getPatient())) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + (amfa.getPatients().get(i).getDate())) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + (amfa.getPatients().get(i).getID())) %></td>
				<td><%= StringEscapeUtils.escapeHtml("" + (amfa.getPatients().get(i).getCalories())) %></td>
			</tr>
			<%
		}
		%>
	
	</table>
	<%
	}
	%>
	
<% } %>

</div>
<br />


<%@include file="/footer.jsp" %>
