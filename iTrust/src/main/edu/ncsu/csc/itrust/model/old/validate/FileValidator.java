package edu.ncsu.csc.itrust.model.old.validate;

import java.io.InputStream;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;           
import javax.servlet.http.Part;

import edu.ncsu.csc.itrust.model.fitBit.FitBitBean;
import edu.ncsu.csc.itrust.model.fitBit.FitBitDAO;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@FacesValidator(value="FileValidator")
public class FileValidator implements Validator {
	
	java.util.Date d1 = null;
	Date d2 = null;
	
	DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	public DAOFactory prodDAO;
	
	@Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
		Part file = (Part) value;
		
		//set date format to not be lenient
		format.setLenient(false);
        
		//filename
        String fileName = file.getName().toUpperCase();
        
        //parsing vars
        String Line = "";
        String Token = "";
        
        //database stuff
		FitBitDAO fitbitDAO = new FitBitDAO(prodDAO);
		FitBitBean fitbitForm = new FitBitBean();
		
		//Scanner setup
		//line scanner

		Scanner ls = null;
		
		//token scanner
		Scanner ts = null;
		
		InputStream is;
		
		try{
			is = file.getInputStream();
		} catch (Exception ex) {
            throw new ValidatorException(new FacesMessage("Invalid file"), ex);
        }
		
		ls = new Scanner(is);
		ts = new Scanner(Line);
		
		ts.useDelimiter(",");
		
		/**
		if(fileName.endsWith(".CSV")){
			ls = new Scanner(is);
			ts = new Scanner(Line);
		} else {
			//invalid file type
			//not sure what we want to do here
			System.out.println("invalid file type");
		}
		*/
		
		//parse & update database
		while(ls.hasNextLine()){
			Line = ls.nextLine();
			if(ts.hasNext()){
				Token = ts.next();
			}
			if((Token.contains("/"))){
				//update day in database

				try {
					d1 = format.parse(Token);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				d2 = new java.sql.Date(d1.getTime());
				fitbitForm.setDate(d2);	
				for(int i = 0; i < 9; i++){
					
					if(ts.hasNext()){
						Token = ts.next();
					}
					
					//deal with numbers in quotes
					if(Token.charAt(0) == '"'){
						String firstPart = Token.substring(1);
						Token = ts.useDelimiter(",").next();
						Token = firstPart + Token.substring(0, Token.length() - 1);
						Token = Token.substring(1, Token.length() - 1);
					}
					
					//update database
					if(i == 0){

						fitbitForm.setCalBurned(Integer.parseInt(Token));
					}else if(i == 1){
						fitbitForm.setSteps(Integer.parseInt(Token));
					}else if(i == 2){
						fitbitForm.setDistance(Double.parseDouble(Token));
					}else if(i == 3){
						fitbitForm.setFloors(Integer.parseInt(Token));
					}else if(i == 4){
						fitbitForm.setMinSeden(Integer.parseInt(Token));
					}else if(i == 5){
						fitbitForm.setMinLightActive(Integer.parseInt(Token));
					}else if(i == 6){
						fitbitForm.setMinFairActive(Integer.parseInt(Token));
					}else if(i == 7){
						fitbitForm.setMinVeryActive(Integer.parseInt(Token));
					}else if(i == 8){
						fitbitForm.setActivityCal(Integer.parseInt(Token));
					}
				}
			} else {
				//invalid file format OR junk at beginning of file
			}
			
			ls.close();
			ts.close();
			
		}
        
	}

}
