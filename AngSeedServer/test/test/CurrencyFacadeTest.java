/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import data.CurrencyNationalBanken;
import deploy.DeploymentConfiguration;
import facades.CurrencyFacade;
import javax.persistence.Persistence;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;

/**
 *
 * @author sebastiannielsen
 */
public class CurrencyFacadeTest {
    
    private CurrencyFacade facade;
    Thread t;
    public CurrencyFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DeploymentConfiguration.setTestModeOn();
    }
    
    @Before
    public void setUp() throws InterruptedException {
        facade = new CurrencyFacade();
        t = new Thread(new CurrencyNationalBanken());
        t.start();
        t.sleep(4000);
    }
    
    @After
    public void tearDown(){
        t.interrupt();
    }
    
    @Test
    public void testGetDailyRates(){
        int size = facade.getDailyRates().size();
        assertEquals(33, size);
    }
}
