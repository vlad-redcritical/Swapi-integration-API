package pl.softwareplant.api.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PageCounterUtils {
    public static Integer count(Integer count, Integer perPage, Integer actualPage) {
        /*
         * Null Validation
         * */
        if (Objects.isNull(count) || Objects.isNull(perPage) || Objects.isNull(actualPage))
            return 0;

        /*
         * Return natural -> if count of the elements < perPage value
         * */
        int actualCount = perPage * actualPage;

        if (count <= actualCount)
            return 0;


        double additionalPage = Math.ceil((double) count / actualCount);


        return (int) additionalPage;
    }
}
