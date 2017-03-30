package edu.ncsu.csc.itrust.unit.model.fitBit;

import java.sql.Date;

import org.junit.Assert;

import edu.ncsu.csc.itrust.model.fitBit.FitBitBean;
import junit.framework.TestCase;

public class FitBitBeanTest extends TestCase{
	private static final Date d = Date.valueOf("2017-01-01");
	
    public void testFields(){
    	FitBitBean fbb = new FitBitBean();
        
        fbb.setPatient(90L);
        Assert.assertEquals(90L, fbb.getPatient());
        
        fbb.setActivityCal(15);
        Assert.assertEquals(15, fbb.getActivityCal());
        
        fbb.setCalBurned(205);
        Assert.assertEquals(205, fbb.getCalBurned());
        
        fbb.setFloors(3);
        Assert.assertEquals(3, fbb.getFloors());
        
        fbb.setMinSeden(85);
        Assert.assertEquals(85, fbb.getMinSeden());
        
        fbb.setMinLightActive(170);
        Assert.assertEquals(170, fbb.getMinLightActive());
        
        fbb.setMinFairActive(70);
        Assert.assertEquals(70, fbb.getMinFairActive());
        
        fbb.setMinVeryActive(400);
        Assert.assertEquals(400, fbb.getMinVeryActive());
        
        fbb.setID("hi");
        Assert.assertEquals("hi", fbb.getID());
        
        fbb.setSteps(394);
        Assert.assertEquals(394, fbb.getSteps());
        
        fbb.setDate(d);
        Assert.assertEquals(d, fbb.getDate());
        
        fbb.setDistance(13.37);
        Assert.assertEquals(13.37, fbb.getDistance(), 0.001);
        
        Assert.assertTrue(fbb.equals(fbb));
        
        FitBitBean other = new FitBitBean();
        Assert.assertFalse(fbb.equals(other));
        Assert.assertFalse(fbb.equals(d));
        
        Assert.assertEquals(0, fbb.hashCode());
    }
}