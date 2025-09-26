package mb.testframeworks.java.utils;

import java.text.Normalizer;

public class StringUtils {
    public static String stripAccents(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
