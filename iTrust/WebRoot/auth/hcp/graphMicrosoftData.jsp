<%@page import="java.io.DataInputStream"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.ncsu.csc.itrust.model.old.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandDAO"%>
<%@page import="edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean"%>


<%@include file="/global.jsp" %>

<%
	pageTitle = "iTrust - Graph Microsoft Band Data";
%>

<%@include file="/header.jsp" %>

<%
	MicrosoftBandDAO microsoftDAO = new MicrosoftBandDAO(prodDAO);
	long patient = Long.parseLong((String)session.getAttribute("pid"));
	String date1 = "";
	String date2 = "";
	java.util.Date utilD1 = null;
	java.util.Date utilD2 = null;
	Date d1 = null;
	Date d2 = null;
	List<MicrosoftBandBean> list = new ArrayList<MicrosoftBandBean>();
	int[] stepList;
	double[] avgList;
	String[] dates;
	
	%>
	<form action="graphMicrosoftData.jsp" method="post">
	<b>Choose a Chart Type: </b>
	<select name="graphType">
		<option value="1">Line Graph - Step Count</option>
		<option value="2">Line Graph - Delta Step Count</option>
		<option value="3">Chart - Weekly Step Count</option>
 	</select>
  	<br />
	<br />
	<b>Start Date: </b>
	<input name="firstDate"
			value="<%=StringEscapeUtils.escapeHtml("" + (date1))%>" size="10">
		<input type=button value="Select First Date"
			onclick="displayDatePicker('firstDate');">
	<b>End Date: </b>
	<input name="secondDate"
			value="<%=StringEscapeUtils.escapeHtml("" + (date2))%>" size="10">
		<input type=button value="Select Second Date"
			onclick="displayDatePicker('secondDate');">
		<br />
		<br />
	<input type="submit" name="graph" value="Generate Graph!"/>
	</form>
	<%
	
	if(request.getParameter("graph") != null) {
		
		date1 = request.getParameter("firstDate");
		date1 = date1.trim();
		date2 = request.getParameter("secondDate");
		date2 = date2.trim();
		try {
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.setLenient(false);
			utilD1 = format.parse(date1);
			utilD2 = format.parse(date2);
			if(utilD2.before(utilD1)) {
				out.println("Start date is after End Date.");
				return;
			}
			d1 = new java.sql.Date(utilD1.getTime());
			d2 = new java.sql.Date(utilD2.getTime());
			list = microsoftDAO.getByDateRange(d1, d2, patient);
		} catch(ParseException e){
			boolean invalidDate = true;
		}
		if(list == null || list.size() == 0) {
			out.println("No workouts found between the given date range.");
			return;
		}
		String type = (String) request.getParameter("graphType");
		switch(type) {
			case "1":
				stepList = new int[list.size()];
				dates = new String[list.size()];
				for(int i = 0; i < list.size(); i++) {
					stepList[i] = list.get(i).getSteps();
					dates[i] = list.get(i).getDate().toString();
				}
		
		%>
<head>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Steps');
        
        <%for(int i = 0; i < stepList.length; i++) {%>
        	data.addRow(['<%=dates[i]%>', <%=stepList[i]%>]);
        <%}%>

        // Set chart options
        var options = {
        		hAxis: {title: 'Date'},
        		vAxis: {title: 'Steps Taken'},
        		'title':'Steps Taken',
                       'width':700,
                       'height':500};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>
		<%
		break;
			case "2":
				int sum = 0;
				double average;
				avgList = new double[list.size()];
				dates = new String[list.size()];
				for(int i = 0; i < list.size(); i++) {
					sum += list.get(i).getSteps();
				}
				average = (double) sum / (double) list.size();
				for(int i = 0; i < list.size(); i++) {
					avgList[i] = list.get(i).getSteps() - average;
					dates[i] = list.get(i).getDate().toString();
				}
%>
<head>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('number', 'Steps');
        
        <%for(int i = 0; i < avgList.length; i++) {%>
        	data.addRow(['<%=dates[i]%>', <%=avgList[i]%>]);
        <%}%>

        // Set chart options
        var options = {
        		hAxis: {title: 'Date'},
        		vAxis: {title: 'Delta Steps Taken'},
        		'title':'Delta Steps Taken',
                       'width':700,
                       'height':500};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
</head>
<%
				break;
			case "3":
				Date last = d1;
				Date next = new Date(last.getTime() + (7 * TimeUnit.DAYS.toMillis(1)));
%>
		<table>
			  <tr>
			    <th>Date Range</th>
			    <th>Average Step taken</th>
			  </tr>
<%
				while(!next.after(d2)) {
					list = microsoftDAO.getByDateRange(last, next, patient);
					int summ = 0;
					double avg;
					dates = new String[list.size()];
					for(int i = 0; i < list.size(); i++) {
						summ += list.get(i).getSteps();
					}
					avg = (double) summ / (double) list.size();
%>
			  <tr>
			    <td><%= StringEscapeUtils.escapeHtml("" + (last.toString()) + " to " +  (next.toString())) %></td>
			    <td><%= StringEscapeUtils.escapeHtml("" + (avg)) %></td>
			  </tr>
<%
					last = next;
					next = new Date(last.getTime() + (7 * TimeUnit.DAYS.toMillis(1)));
				}
				if(last.before(d2)) {
					list = microsoftDAO.getByDateRange(last, d2, patient);
					int summ = 0;
					double avg;
					dates = new String[list.size()];
					for(int i = 0; i < list.size(); i++) {
						summ += list.get(i).getSteps();
					}
					avg = (double) summ / (double) list.size();
%>
			  <tr>
			    <td><%= StringEscapeUtils.escapeHtml("" + (last.toString()) + " to " +  (d2.toString())) %></td>
			    <td><%= StringEscapeUtils.escapeHtml("" + (avg)) %></td>
			  </tr>
<%
				}
%>
		</table>
<%
				break;
			default:
				out.println("no graph type selected");
		}
	}
%>

<body>
  <div id="chart_div"></div>
</body>

<%@include file="/footer.jsp" %>
