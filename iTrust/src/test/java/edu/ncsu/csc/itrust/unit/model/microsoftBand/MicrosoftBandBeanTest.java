package edu.ncsu.csc.itrust.unit.model.microsoftBand;

import org.junit.Assert;

import edu.ncsu.csc.itrust.model.microsoftBand.MicrosoftBandBean;
import junit.framework.TestCase;

public class MicrosoftBandBeanTest extends TestCase{
    public void testFields(){
        MicrosoftBandBean mbb = new MicrosoftBandBean();
        
        mbb.setPatient(90L);
        Assert.assertEquals(90L, mbb.getPatient());
        
        mbb.setActivityHours(15);
        Assert.assertEquals(15, mbb.getActivityHours());
        
        mbb.setCalories(205);
        Assert.assertEquals(205, mbb.getCalories());
        
        mbb.setFloors(3);
        Assert.assertEquals(3, mbb.getFloors());
        
        mbb.setHRAverage(85);
        Assert.assertEquals(85, mbb.getHRAverage());
        
        mbb.setHRHighest(170);
        Assert.assertEquals(170, mbb.getHRHighest());
        
        mbb.setHRLowest(70);
        Assert.assertEquals(70, mbb.getHRLowest());
        
        mbb.setMinUVExposure(400);
        Assert.assertEquals(400, mbb.getMinUVExposure());
        
        mbb.setID("hi");
        Assert.assertEquals("hi", mbb.getID());
        
        mbb.setSteps(394);
        Assert.assertEquals(394, mbb.getSteps());
    }
}