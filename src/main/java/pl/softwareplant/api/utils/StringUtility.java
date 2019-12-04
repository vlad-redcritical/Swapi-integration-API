package pl.softwareplant.api.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class StringUtility {
    public static Integer parseIdFromUrl(final String url) {
        if (Objects.isNull(url))
            return null;

        String[] splitArr = url.split("/");

        int maxLength = splitArr.length - 1;

        return Integer.valueOf(splitArr[maxLength]);
    }
}
