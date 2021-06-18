package it.polito.po.test;
import junit.framework.TestCase;
import java.util.*;
import reseller.*;

public class TestReq1and2 extends TestCase {
    static final String EPSILON = "epsilon";
    static final String Z = "z";
     static final String Y = "y";
     static final String X = "x";
     static final String LAMBDA = "lambda";
     static final String DELTA = "delta";
    final static String BETA = "beta";
    final static String ALPHA = "alfa";
    final static String GAMMA = "gamma";

    private Reseller r = null;
	
	public void setUp() throws Exception {
		super.setUp();
		r = new Reseller();
        r.setStock(20, BETA, GAMMA, ALPHA, DELTA, LAMBDA, EPSILON);
		r.addSupplier(Z, GAMMA, LAMBDA);
		r.addSupplier(X, LAMBDA);
		r.addSupplier(Y, EPSILON);
	}
	
	public void testStock1() {
		Integer n = r.getStock(DELTA);
		assertNotNull("Missing stock value for product " + DELTA,n);
		assertEquals("Wrong stock value for product delta", n.intValue(), 20);
	}
	public void testStock2() {
		Integer nI = r.getStock("omicron");
		assertNull("There should not be any stock for product omicron",nI);
	}
	public void testSupplier1() {
	    List<String> list = r.getProductsWithoutSuppliers();
		assertNotNull(list);
		assertEquals(Arrays.asList(ALPHA,BETA,DELTA),list);
	}
	public void testSupplier2() {
		r.addSupplier("w", ALPHA,BETA,DELTA);
		List<String> list = r.getProductsWithoutSuppliers();
		assertNotNull(list);
		assertTrue("All products should have a supplier", list.isEmpty());
	}
	public void testSupplier3() {
	    List<String> list = r.getSupplierNames(LAMBDA);
	    assertNotNull(list);
	    assertEquals(Arrays.asList(X,Z),list);
	}
	public void testSupplier4() {
	    List<String> list = r.getSupplierNames(DELTA);
	    assertNotNull(list);
	    assertTrue("Product " + DELTA + " should have no supplier", list.isEmpty());
	}
}
