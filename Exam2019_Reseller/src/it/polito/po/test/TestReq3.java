package it.polito.po.test;
import junit.framework.TestCase;
import reseller.*;
import static it.polito.po.test.TestReq1and2.*;

public class TestReq3 extends TestCase {
	private Reseller r = null;
	
   public void setUp() throws Exception {
        r = new Reseller();
        r.setStock(20, BETA, GAMMA, ALPHA, DELTA, LAMBDA, EPSILON);
        r.addSupplier(Z, GAMMA, ALPHA);
        r.addSupplier(X, ALPHA);
        r.addSupplier(Y, EPSILON);
    }
	   
	public void testEnterCOrder1() { // 1 solo ordine minore dello stock
		r.enterCOrder("a", ALPHA, 10);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,10,f]",s1.replaceAll("\\s*;\\s*",";"));
		assertEquals("[]",s2.replaceAll("\\s*;*\\s*",""));
		int n = r.getStock(ALPHA);
		assertEquals(10,n);
	}
	
	public void testEnterCOrder2() { // 2 ordini minori dello stock
		r.enterCOrder("a", ALPHA, 10);
		r.enterCOrder("b", ALPHA, 5);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals( "[a,10,f;b,5,f]",s1.replaceAll("\\s*;\\s*",";"));
		assertEquals( "[]",s2.replaceAll("\\s*;*\\s*",""));
		int n = r.getStock(ALPHA);
		assertEquals(n, 5);
	}
	
	public void testEnterCOrder3() { // 1 solo ordine maggiore dello stock
		r.enterCOrder("a", ALPHA, 30);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,30,p]",s1.replaceAll("\\s*;\\s*",""));
		assertEquals("[x,50,p]",s2.replaceAll("\\s*;\\s*",""));
	}
	
	public void testEnterCOrder4() {
		r.enterCOrder("a", ALPHA, 30); // 2 ordini, il primo e' maggiore dello stock, il secondo e' coperto
		r.enterCOrder("a", ALPHA, 15);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,30,p;a,15,p]",s1.replaceAll("\\s*;\\s*",";"));
		assertEquals("[x,50,p]",s2.replaceAll("\\s*;\\s*",""));
	}
	
	public void testEnterCOrder5() { // 2 ordini, il primo e' maggiore dello stock, il secondo e' scoperto
									 // 2 ordini fornitore per x e z che sono i fornitori di alfa
		r.enterCOrder("a", ALPHA, 25);
		r.enterCOrder("a", ALPHA, 45);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,25,p;a,45,p]",s1.replaceAll("\\s*;\\s*",";"));
		assertEquals("[x,45,p;z,65,p]",s2.replaceAll("\\s*;\\s*",";"));
	}
	
	public void testDelivery3() { // 1 solo ordine maggiore dello stock
		r.enterCOrder("a", ALPHA, 30);
		r.delivery("x", ALPHA);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,30,f]",s1.replaceAll("\\s*;\\s*",""));
		assertEquals("[x,50,f]",s2.replaceAll("\\s*;\\s*",""));
		int n = r.getStock(ALPHA);
		assertEquals(40,n);
	}
	
	public void testDelivery5a() {
		r.enterCOrder("a", ALPHA, 25);
		r.enterCOrder("a", ALPHA, 45);
		r.delivery("x", ALPHA);
		r.delivery("z", ALPHA);
		String s1 = r.getCOrders(ALPHA);
		String s2 = r.getSOrders(ALPHA);
		assertEquals("[a,25,f;a,45,f]",s1.replaceAll("\\s*;\\s*",";"));
		assertEquals("[x,45,f;z,65,f]",s2.replaceAll("\\s*;\\s*",";"));
		int n = r.getStock(ALPHA);
		assertEquals(60,n);		
		
	}
	
	public void testGetCustomerNeeds() {
		r.enterCOrder("a", ALPHA, 25);
		r.enterCOrder("a", ALPHA, 45);
		int n = r.getCustomerNeeds(ALPHA);
		assertEquals("Wrong customer needs for product " + ALPHA, 70,n);
	}
	
	public void testGetExpectedSupplies() {
		r.enterCOrder("a", ALPHA, 25);
		r.enterCOrder("a", ALPHA, 45);
		int n = r.getExpectedSupplies(ALPHA);
		assertEquals("Wrong expected supplies for product " + ALPHA, 110,n);
	}
}
