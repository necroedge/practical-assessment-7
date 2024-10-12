package test;

import org.aeonbits.owner.Config;

@Config.Sources({ "file:src/main/resources/config.properties" })
public interface ApplicationConfig extends Config {

    @Config.Key("server.host")
    String host();

    @Config.Key("server.base")
    String base();

    @Config.Key("server.baseUser")
    String baseUser();
}
