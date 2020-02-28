package generic;

import de.clayntech.config4j.impl.util.GsonConverter;
import de.clayntech.config4j.impl.util.JsonConverter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class GsonConverterTest {
    @Test
    public void testGsonConverter() {
        JsonConverter conv = GsonConverter.newInstance();
        Properties prop1 = new Properties();
        prop1.setProperty("Key", "Val");
        Properties prop2 = conv.fromJson(conv.toJson(prop1), Properties.class);
        Assert.assertEquals(prop1, prop2);
    }
}
