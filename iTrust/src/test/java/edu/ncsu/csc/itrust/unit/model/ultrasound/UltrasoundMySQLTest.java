package edu.ncsu.csc.itrust.unit.model.ultrasound;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ConverterDAO;
import edu.ncsu.csc.itrust.model.prescription.PrescriptionMySQL;
import edu.ncsu.csc.itrust.model.ultrasound.Ultrasound;
import edu.ncsu.csc.itrust.model.ultrasound.UltrasoundMySQL;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

/**
 * Tests the UltrasoundMySQL file to make sure its working correctly
 * @author bmhogan
 */
public class UltrasoundMySQLTest extends TestCase {
	private UltrasoundMySQL sql;
	
	@Spy
	private UltrasoundMySQL mockSql;
	private DataSource ds;

	@Override
    public void setUp() throws DBException, FileNotFoundException, SQLException, IOException{
        ds = ConverterDAO.getDataSource();
        sql = new UltrasoundMySQL(ds);
        mockSql = Mockito.spy(new UltrasoundMySQL(ds));
        TestDataGenerator gen = new TestDataGenerator();
        gen.clearAllTables();
        gen.ultrasound();
    }
	
    @Test
    public void testNoDataSource(){
        try {
            new PrescriptionMySQL();
            Assert.fail();
        } catch (DBException e){
            // yay, we passed
        }
    }
    
    @Test
    public void testGetAll() {
    	List<Ultrasound> usList = new ArrayList<Ultrasound>();
    	try {
    		usList = sql.getAll();
    		assertEquals(3, usList.size());
    		// Make sure that the Ultrasounds were correctly pulled
    		Ultrasound us1 = usList.get(0);
    		Ultrasound us2 = usList.get(1);
    		Ultrasound us3 = usList.get(2);
    		assertEquals(1L, us1.getMID());
    		assertEquals(1L, us2.getMID());
    		assertEquals(5L, us3.getMID());
    		
    		assertEquals(8.9, us1.getHL(), 0.005);
    		assertEquals(3.4, us2.getBPD(), 0.005);
    		assertEquals(4, us3.getFL(), 0.005);
    	} catch (Exception e) {
    		fail();
    	}
    }
    
    @Test
    public void testGetByMID() {
    	List<Ultrasound> usList1;
    	List<Ultrasound> usList2;
    	List<Ultrasound> usList3;
    	try {
    		usList1 = sql.getByMID(1L);
    		usList2 = sql.getByMID(5L);
    		
    		assertEquals(2, usList1.size());
    		assertEquals(1, usList2.size());
    		
    		usList3 = sql.getByMID(3L);
    		assertTrue(usList3.isEmpty());
    	} catch (Exception e) {
    		fail();
    	}
    }
    
    @Test
    public void testAdd() {
    	LocalDateTime d = LocalDateTime.of(2017, 03, 23, 0, 0);
    	Ultrasound newUS = new Ultrasound(5, d, 1, 1, 1, 1, 1, 1, 1, 1);
    	List<Ultrasound> usList;
    	
    	try {
    		usList = sql.getAll();
    		assertEquals(3, usList.size());
    		
    		assertTrue(sql.addUltrasound(newUS));
    		usList = sql.getAll();
    		assertEquals(4, usList.size());
    		Ultrasound four = usList.get(3);
    		assertEquals(5L, four.getMID());
    		assertEquals(d, four.getDate());
    		assertEquals("23/3/2017", four.getDateString());
    	} catch (Exception e) {
    		fail();
    	}
    }
    
    @Test
    public void testUpdate() {
    	LocalDateTime d = LocalDateTime.of(2015, 10, 22, 0, 0);
    	Ultrasound newUS = new Ultrasound(1, d, 45, 1, 1, 1, 1, 1, 1, 1);
    	List<Ultrasound> usList;
    	try {
    		usList = sql.getAll();
    		assertEquals(3, usList.size());
    		
    		// Now try to update the first ultrasound in the datbase, at usList.get(0)
    		// It has the same MID and date as newUS
    		Ultrasound former = usList.get(0);
    		assertTrue(sql.update(newUS));
    		
    		// The first Ultrasound in the datbase is the one that should have been updated
    		usList = sql.getAll();
    		assertEquals(3, usList.size()); // Make sure that a new Ultrasound wasn't added
    		Ultrasound updated = usList.get(0);
    		
    		// Now make sure former and later have the same MID and date but different fields
    		assertEquals(former.getMID(), updated.getMID());
    		assertEquals(former.getDateString(), updated.getDateString());
    		assertEquals(45.0, updated.getCRL(), 0.005);
    		
    		assertNotEquals(former.getCRL(), updated.getCRL());
    	} catch (Exception e) {
    		fail();
    	}
    }
}
