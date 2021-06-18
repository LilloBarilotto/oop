package it.polito.po.test;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TestReq1and2.class);
        suite.addTestSuite(TestReq3.class);
        suite.addTestSuite(TestReq4.class);
        //$JUnit-END$
        return suite;
    }
}
