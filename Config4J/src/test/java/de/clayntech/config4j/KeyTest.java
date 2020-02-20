package de.clayntech.config4j;

import de.clayntech.config4j.impl.SimpleConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class KeyTest {

    @Test
    public void testBasicKey() {
        Configuration conf = new SimpleConfiguration();
        Key<String> key = Key.createBasicKey("Key");
        Assert.assertNull(conf.get(key));
        conf.set(key, "Val");
        Assert.assertEquals("Val", conf.get(key));
    }
}
