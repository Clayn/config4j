package de.clayntech.config4j.io;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class SourceTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testSourceExists() throws IOException {
        String intern = "/de/clayntech/config4j/util/test2.c4j";
        File extern = folder.newFile();
        Assert.assertTrue(extern.exists());
        URL u = new URL("http://www.clayncraft.de/");
        Source internSource = Source.newResourceSource(intern);
        Source fileSource = Source.newFileSource(extern);
        Source urlSource = Source.newURLSource(u);
        for (Source src : Arrays.asList(internSource, fileSource, urlSource)) {
            System.out.println("Checking: " + src.getType() + " ; " + src.getSource());
            Assert.assertTrue(src.exists());
        }
    }

    @Test
    public void testSourceDoesntExists() throws IOException {
        String intern = "/notexisting";
        File extern = folder.newFile();
        extern.delete();
        Assert.assertFalse(extern.exists());
        URL u = new URL("http://nourl.no");
        Source internSource = Source.newResourceSource(intern);
        Source fileSource = Source.newFileSource(extern);
        Source urlSource = Source.newURLSource(u);
        Source urlSource2 = Source.newURLSource("url");
        for (Source src : Arrays.asList(internSource, fileSource, urlSource, urlSource2)) {

            Assert.assertFalse(src.exists());
        }
    }
}
