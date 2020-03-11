package de.clayntech.config4j;

import de.clayntech.config4j.impl.MemoryConfiguration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.impl.key.KeyFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyTest {

    private static final Map<Class<?>, String> validFromValues = new HashMap<>();
    private static final Map<Class<?>, Object> validToValues = new HashMap<>();
    private static final Map<Class<?>, Object> inValidToValues = new HashMap<>();
    private static final Map<Class<?>, String> inValidFromValues = new HashMap<>();
    private static final List<Class<?>> keyClasses = new ArrayList<>();
    private static final Map<Class<?>, Class<?>> matchClasses = new HashMap<>();

    static {
        initInvalidValues();
        initValidValues();
        keyClasses.add(Boolean.class);
        keyClasses.add(Integer.class);
        keyClasses.add(Double.class);
        keyClasses.add(File.class);
        keyClasses.add(URL.class);
        keyClasses.add(String.class);
        matchClasses.put(Boolean.class, Boolean.TYPE);
        matchClasses.put(Integer.class, Integer.TYPE);
        matchClasses.put(Double.class, Double.TYPE);
    }

    private static void initValidValues() {
        //putPrimitiveValue(byte.class,Byte.class,5,"5",validFromValues,validToValues);
        //putPrimitiveValue(short.class,Short.class,5,"5",validFromValues,validToValues);
        putPrimitiveValue(int.class, Integer.class, 5, "5", validFromValues, validToValues);
        //putPrimitiveValue(long.class,Long.class,Long.MAX_VALUE,String.valueOf(Long.MAX_VALUE),validFromValues,validToValues);
        putPrimitiveValue(double.class, Double.class, 5.5, "5.5", validFromValues, validToValues);
        //putPrimitiveValue(float.class,Float.class,5.5f,"5.5",validFromValues,validToValues);
        putPrimitiveValue(boolean.class, Boolean.class, true, "true", validFromValues, validToValues);
    }

    private static void putValue(Class<?> obj, Object val, String parsed, Map<Class<?>, String> fromValues, Map<Class<?>, Object> toValues) {
        fromValues.put(obj, parsed);
        toValues.put(obj, val);
    }

    private static void putPrimitiveValue(Class<?> prim, Class<?> obj, Object val, String parsed, Map<Class<?>, String> fromValues, Map<Class<?>, Object> toValues) {
        fromValues.put(prim, parsed);
        toValues.put(prim, val);
        putValue(obj, val, parsed, fromValues, toValues);
    }

    private static void initInvalidValues() {
        //putPrimitiveValue(byte.class,Byte.class,5,"5",validFromValues,validToValues);
        //putPrimitiveValue(short.class,Short.class,5,"5",validFromValues,validToValues);
        putPrimitiveValue(int.class, Integer.class, 5, "abc", inValidFromValues, inValidToValues);
        //putPrimitiveValue(long.class,Long.class,Long.MAX_VALUE,String.valueOf(Long.MAX_VALUE),validFromValues,validToValues);
        putPrimitiveValue(double.class, Double.class, 5.5, "abc", inValidFromValues, inValidToValues);
        //putPrimitiveValue(float.class,Float.class,5.5f,"5.5",validFromValues,validToValues);
        putValue(URL.class, null, "asdasdj.de", inValidFromValues, inValidToValues);
    }

    @Test
    public void testAccessKey() {
        Configuration conf = new SimpleConfiguration();
        Key<String> key = Key.createAccessKey("Key");
        Assert.assertNull(conf.get(key));
        conf.set(key, "Val");
        Assert.assertEquals("Val", conf.get(key));
    }

    @Test
    public void testValidValues() {
        Configuration conf = new MemoryConfiguration();
        int i = 0;
        for (Class<?> cls : validFromValues.keySet()) {
            Key key = KeyFactory.createKey("Some Key", cls);
            String stringVal = validFromValues.get(cls);
            Object val = validToValues.get(cls);
            Assert.assertEquals(stringVal, key.toString(val));
            Assert.assertEquals(val, key.fromString(stringVal));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownKeyClass() {
        KeyFactory.createKey("Key", Exception.class);
    }

    @Test
    public void testInValidFromValues() {
        Configuration conf = new MemoryConfiguration();
        int i = 0;
        for (Class<?> cls : inValidFromValues.keySet()) {
            Key key = KeyFactory.createKey("Some Key", cls);
            String val = inValidFromValues.get(cls);
            try {
                key.fromString(val);
                Assert.fail("An exception should be thrown");
            } catch (Exception ex) {

            }
        }
    }

    @Test
    public void testFileKeyNull() {
        Key<File> key = KeyFactory.createKey("Key", File.class);
        Assert.assertEquals("", key.toString(null));
        Assert.assertNull(key.fromString(""));
    }

    @Test
    public void testURLKeyNull() {
        Key<URL> key = KeyFactory.createKey("Key", URL.class);
        Assert.assertEquals("", key.toString(null));
        Assert.assertNull(key.fromString(""));
    }

    @Test
    public void testPrimitiveKey() {
        for (Map.Entry<Class<?>, Class<?>> entry : matchClasses.entrySet()) {
            Key<?> key1 = KeyFactory.createKey("Key", entry.getKey());
            Key<?> key2 = KeyFactory.createKey("Key", entry.getValue());
            Assert.assertEquals(key1, key2);
            Assert.assertEquals(key1, key1);
            Assert.assertNotEquals(key1, null);
            Assert.assertEquals(key1.hashCode(), key2.hashCode());
        }
    }

    @Test
    public void testValuedKey() {
        Configuration conf = new SimpleConfiguration();
        ValuedKey<String> key = KeyFactory.createKey("key", String.class, "Hello World");
        Assert.assertEquals("Hello World", conf.get(key));
        conf.set(key, "Val");
        Assert.assertEquals("Val", conf.get(key));
    }

    @Test
    public void testKeyNotMatching() {
        for (int i = 0; i < keyClasses.size(); ++i) {
            for (int j = 0; j < keyClasses.size(); ++j) {
                if (i != j) {
                    Key<?> key1 = KeyFactory.createKey("Key", keyClasses.get(i));
                    Key<?> key2 = KeyFactory.createKey("Key", keyClasses.get(j));
                    Assert.assertNotEquals(key1, key2);
                }
            }
        }
    }

}
