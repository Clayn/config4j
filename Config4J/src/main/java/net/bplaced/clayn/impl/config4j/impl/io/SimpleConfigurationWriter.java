package net.bplaced.clayn.impl.config4j.impl.io;

import net.bplaced.clayn.impl.config4j.Configuration;
import net.bplaced.clayn.impl.config4j.io.ConfigurationWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SimpleConfigurationWriter implements ConfigurationWriter {
    @Override
    public void store(OutputStream out, Configuration config) throws IOException {
        Properties prop=new Properties();
        storeProperties(prop,config,null);
        prop.store(out,"");
    }

    private void storeProperties(Properties prop, Configuration config,String prefix) {
        for(String key:config.getConfigurations()) {
            String finalKey=(prefix==null?"":prefix)+key;
            prop.setProperty(finalKey,config.get(key));
        }
        for(String profile:config.getProfiles()) {
            storeProperties(prop,config.getProfile(profile),prefix+profile);
        }
    }
}
