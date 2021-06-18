package example;
import java.util.*;
import reseller.*;

public class Example {
	
static void print(Object o) {System.out.println(o);}

static void printLists(Reseller r, String productName) {
	print("product "+ productName + " customer orders " + r.getCOrders(productName) 
	+ " supplier orders " + r.getSOrders(productName) + " stock " + r.getStock(productName));
}
static void printParameters(Reseller r, String productName) {
	print("product "+ productName + " customerNeeds " + r.getCustomerNeeds(productName) 
	+ " expectedSupplies " + r.getExpectedSupplies(productName));
}
static void printStock(Reseller r, String productName) {
	print(r.getStock(productName));
}
	
public static void main(String[] args) {
	List<String> list;
	Reseller r = new Reseller();
	r.setStock(10, "p1", "p2", "p3", "p4", "p5", "p6");
	r.addSupplier("s1", "p1", "p2");
	r.addSupplier("s2", "p1", "p3");
	r.addSupplier("s3", "p4");
	list = r.getProductsWithoutSuppliers(); print(list); //[p5, p6]
	list = r.getSupplierNames("p1"); print(list); //[s1, s2]

	r.enterCOrder("c1", "p1", 6);
	printLists(r, "p1"); //product p1 customer orders [c1,6,f] supplier orders [] stock 4
	printParameters(r, "p1"); // product p1 customerNeeds 0 expectedSupplies 0
	r.enterCOrder("c2", "p1", 10);
	printLists(r, "p1"); //product p1 customer orders [c1,6,f;c2,10,p] supplier orders [s1,20,p] stock 4
	printParameters(r, "p1"); // product p1 customerNeeds 10 expectedSupplies 20
	r.enterCOrder("c1", "p1", 6);
	printLists(r, "p1"); //product p1 customer orders [c1,6,f;c2,10,p;c1,6,p] supplier orders [s1,20,p] stock 4
	r.enterCOrder("c1", "p1", 10);
	printLists(r, "p1"); //product p1 customer orders [c1,6,f;c2,10,p;c1,6,p;c1,10,p] supplier orders [s1,20,p;s2,20,p] stock 4
	printParameters(r, "p1"); // product p1 customerNeeds 26 expectedSupplies 40
	r.delivery("s1", "p1"); 
	printLists(r, "p1"); //product p1 customer orders [c1,6,f;c2,10,f;c1,6,f;c1,10,p] supplier orders [s1,20,f;s2,20,p] stock 8
	r.delivery("s2", "p1"); 
	printLists(r, "p1"); //product p1 customer orders [c1,6,f;c2,10,f;c1,6,f;c1,10,f] supplier orders [s1,20,f;s2,20,f] stock 18

	//R4
	SortedMap<String, Long> map41 = r.nOrdersPerCustomer();
	print(map41); //{c1=3, c2=1}
	SortedMap<String, Integer> map42 = r.ratioSOrdersCOrdersPerProduct();
	print(map42); //{p1=50} 2 supplier orders against 4 customer orders: the ratio is 50%
	r.enterCOrder("c4", "p2", 20);
	map42 = r.ratioSOrdersCOrdersPerProduct();
	print(map42); //{p1=50, p2=100}
}

}
