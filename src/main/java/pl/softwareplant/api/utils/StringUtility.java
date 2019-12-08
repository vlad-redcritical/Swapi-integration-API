package pl.softwareplant.api.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@UtilityClass
public class StringUtility {
    public static Long parseIdFromUrl(final String url) {
        if (Objects.isNull(url))
            return null;

        String[] splitArr = url.split("/");

        int maxLength = splitArr.length - 1;

        try {
            return Long.valueOf(splitArr[maxLength]);
        } catch (NumberFormatException ex) {
            log.error("NumberFormatException - details: {}, message {}", ex, ex.getMessage());
            return null;
        }
    }
}
