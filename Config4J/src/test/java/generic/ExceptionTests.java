package generic;

import de.clayntech.config4j.ValueParsingException;
import de.clayntech.config4j.io.NotStorableException;
import de.clayntech.config4j.io.SourceMissingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ExceptionTests {
    @Parameterized.Parameter
    public Class<? extends Exception> configurationClass;


    @Parameterized.Parameters
    public static Collection getConfigurations() {
        return Arrays.asList(new Object[][]{
                {NotStorableException.class},
                {SourceMissingException.class},
                {ValueParsingException.class}
        });
    }

    @Test
    public void testCreation() throws Exception {
        Constructor<? extends Exception> construct;
        String message = "message";
        Throwable cause = new Exception();
        try {
            construct = configurationClass.getDeclaredConstructor();
            construct.newInstance();
        } catch (NoSuchMethodException e) {

        }
        try {
            construct = configurationClass.getDeclaredConstructor(String.class);
            construct.newInstance(message);
        } catch (NoSuchMethodException e) {

        }
        try {
            construct = configurationClass.getDeclaredConstructor(Throwable.class);
            construct.newInstance(cause);
        } catch (NoSuchMethodException e) {

        }
        try {
            construct = configurationClass.getDeclaredConstructor(Throwable.class, String.class);
            construct.newInstance(cause, message);
        } catch (NoSuchMethodException e) {

        }
        try {
            construct = configurationClass.getDeclaredConstructor(String.class, Throwable.class);
            construct.newInstance(message, cause);
        } catch (NoSuchMethodException e) {

        }
    }
}
