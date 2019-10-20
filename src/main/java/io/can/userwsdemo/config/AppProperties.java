package io.can.userwsdemo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("appProperties")
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Getter
    @Setter
    private Jwt jwt;

    @Getter
    @Setter
    public static class Jwt {
        private String tokenSecretKey = "app";
        private long tokenExpirationTime = 864000000L;
    }

}

