package de.clayntech.config4j.rt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class Runtime4JTest {

    private String key;
    private Runtime4J rt = Runtime4J.getRuntime();

    @Before
    public void setupTest() {
        key = UUID.randomUUID().toString();
    }

    @Test
    public void testSetObject() {
        Assert.assertNull(rt.getObject(key));
        Object obj = new Object();
        rt.setObject(key, obj);
        Assert.assertEquals(obj, rt.getObject(key));
        rt.setObject(key, "Hello World");
        Assert.assertEquals("Hello World", rt.getObject(key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetObjectReserve() {
        Assert.assertNull(rt.getObject(key));
        String obj = "Hello World";
        rt.setObject(key, obj, true);
        Assert.assertEquals(obj, rt.getObject(key));
        rt.setObject(key, 1234);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReserveType() {
        rt.setObject(key, 1234);
        rt.reserveType(key, String.class);
        Assert.assertNull(rt.getObject(key));
        rt.setObject(key, "Hello World");
        Assert.assertEquals("Hello World", rt.getObject(key));
        rt.setObject(key, 1234);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReserveTypeAlreadyReserved() {
        rt.reserveType(key, Integer.class);
        rt.reserveType(key, String.class);
    }
}
