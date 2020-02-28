package generic;

import de.clayntech.config4j.Configuration;
import de.clayntech.config4j.impl.SimpleConfiguration;
import de.clayntech.config4j.util.Config4JMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class Config4JMapperTest {

    @Test
    public void testConvertForward() {
        Configuration conf = new SimpleConfiguration();
        Object val = new Object();
        Config4JMapper mapper = Mockito.mock(Config4JMapper.class);
        Mockito.when(mapper.convertForward(Mockito.any()))
                .thenCallRealMethod();
        Mockito.when(mapper.toConfiguration(Mockito.any()))
                .thenReturn(conf);
        Configuration mapped = mapper.convertForward(val);
        Assert.assertEquals(conf, mapped);
        Mockito.verify(mapper, Mockito.times(1))
                .toConfiguration(Mockito.any());
    }

    @Test
    public void testConvertBackward() {
        Configuration conf = new SimpleConfiguration();
        Object val = new Object();
        Config4JMapper mapper = Mockito.mock(Config4JMapper.class);
        Mockito.when(mapper.convertBackward(Mockito.any()))
                .thenCallRealMethod();
        Mockito.when(mapper.fromConfiguration(Mockito.any()))
                .thenReturn(val);
        Object mapped = mapper.convertBackward(conf);
        Assert.assertEquals(val, mapped);
        Mockito.verify(mapper, Mockito.times(1))
                .fromConfiguration(Mockito.any());
    }
}
