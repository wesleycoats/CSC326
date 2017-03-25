package edu.ncsu.csc.itrust.unit.model.obstetricsVisit;

import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisit;
import edu.ncsu.csc.itrust.model.obstetricsVisit.ObstetricsVisitValidator;
import junit.framework.TestCase;

public class ObstetricsVisitValidatorTest extends TestCase {
    
    private ObstetricsVisit ov;
    private ObstetricsVisitValidator ovv;
    
    @Override
    public void setUp(){
    	ov = new ObstetricsVisit();
    	ovv = new ObstetricsVisitValidator();
    }
    
    @Test
    public void testCorrectValidation(){
		ov.setPatientMID(2l);
		ov.setVisitID(3l);
		ov.setWeeksPregnant(4);
    	try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.fail();
    	}
    	Assert.assertTrue(true);
    }
    
    @Test
    public void testValidationNullValues(){    	
    	try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.assertTrue(true);
    		return;
    	}
    	Assert.fail();
    }
    

    @Test
    public void testValidationNullVisitID(){
    	ov = new ObstetricsVisit();
    	ov.setPatientMID(2l);
		ov.setWeeksPregnant(4);
		try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.assertTrue(true);
    		return;
    	}
    	Assert.fail();
    }
    
    @Test
    public void testValidationNegVisitID(){
    	ov = new ObstetricsVisit();
    	ov.setPatientMID(2l);
    	ov.setVisitID(-3l);
		ov.setWeeksPregnant(4);
		try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.assertTrue(true);
    		return;
    	}
    	Assert.fail();
    }
    
    @Test
    public void testNegWeeksPregnant(){
		ov.setPatientMID(2l);
		ov.setVisitID(3l);
		ov.setWeeksPregnant(-4);
		try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.assertTrue(true);
    		return;
    	}
    	Assert.fail();
    }
    
    @Test
    public void testValidationLongPregnancy(){
    	ov = new ObstetricsVisit();
    	ov.setPatientMID(2l);
    	ov.setVisitID(3l);
		ov.setWeeksPregnant(53);
		try{ 
    		ovv.validate(ov);
    	} catch( FormValidationException e){
    		Assert.assertTrue(true);
    		return;
    	}
    	Assert.fail();
    }
}