package it.polito.po.test;
import junit.framework.TestCase;
import java.util.*;
import reseller.*;
import static it.polito.po.test.TestReq1and2.*;

public class TestReq4 extends TestCase {
    private Reseller r = null;
	
   public void setUp() throws Exception {
        r = new Reseller();
        r.setStock(20, BETA, GAMMA, ALPHA, DELTA, LAMBDA, EPSILON);
        r.addSupplier(Z, GAMMA, ALPHA);
        r.addSupplier(X, ALPHA);
        r.addSupplier(Y, EPSILON);
    }
	
	public void testNOrdersPerCustomer1() {
		r.enterCOrder("b", ALPHA, 25);
		r.enterCOrder("c", ALPHA, 45);
		r.enterCOrder("a", ALPHA, 10);
		r.enterCOrder("a", ALPHA, 20);
		SortedMap<String, Long> map = r.nOrdersPerCustomer();
		assertNotNull(map);
		int n = map.size();
		assertEquals(3, n);
	}
	
	public void testNOrdersPerCustomer2() {
		final String B = "b";
        final String C = "c";
        final String A = "a";
        r.enterCOrder(B, ALPHA, 25);
        r.enterCOrder(C, ALPHA, 45);
        r.enterCOrder(A, ALPHA, 10);
		r.enterCOrder(A, ALPHA, 20);
		SortedMap<String, Long> map = r.nOrdersPerCustomer();
        assertNotNull(map);
		String s = "{a=2, b=1, c=1}";
		assertEquals(s, map.toString());
	}
	
	public void testRatioSOrdersCOrdersPerProduct() {
		r.enterCOrder("a", ALPHA, 10);
		r.enterCOrder("b", ALPHA, 5);
		r.enterCOrder("c", ALPHA, 20);
		SortedMap<String, Integer> map = r.ratioSOrdersCOrdersPerProduct();
        assertNotNull(map);
		String s = "{" + ALPHA + "=33}";
		assertEquals(s, map.toString());
	}
	
}
