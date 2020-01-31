package io.can.userwsdemo.util;

import io.can.userwsdemo.ProjectConstants;
import io.can.userwsdemo.enumeration.ErrorMessages;
import io.can.userwsdemo.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import java.security.SecureRandom;
import java.util.Random;

import static io.can.userwsdemo.enumeration.ErrorMessages.*;

@Component
public class ServiceUtil {

    private final Random random;
    private final String alphabet;

    public ServiceUtil() {
        random = new SecureRandom();
        alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    }

    /**
     * This method generates random user id for user entity
     * */
    public String generateUserId() {
        return generateRandomString(ProjectConstants.LENGTH_FOR_USER_ID);
    }

    private String generateRandomString(int length) {
        StringBuilder returnedValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            returnedValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return returnedValue.toString();
    }

    /**
     * This method casts String id to given number type id
     * */
    public <T extends Number> T getValidNumberId(String id, Class<T> type0fNumber) {
        String trimmedId = id.trim();
        if (!StringUtils.isNumeric(trimmedId)) {
            throw new ApplicationException(INVALID_ID.withGiven(trimmedId), HttpStatus.BAD_REQUEST);
        }
        return NumberUtils.parseNumber(trimmedId, type0fNumber);
    }


}
