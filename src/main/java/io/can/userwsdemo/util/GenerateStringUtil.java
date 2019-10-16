package io.can.userwsdemo.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class GenerateStringUtil {

    private final Random random;
    private final String alphabet;

    public GenerateStringUtil() {
        random = new SecureRandom();
        alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    }

    /**
     * This method generates random user id for user entity
     * */
    public String generateUserId() {
        return generateRandomString(Constants.LENGTH_FOR_USER_ID);
    }

    private String generateRandomString(int length) {
        StringBuilder returnedValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            returnedValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return returnedValue.toString();
    }


}
