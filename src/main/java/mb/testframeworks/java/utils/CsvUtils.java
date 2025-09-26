package mb.testframeworks.java.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static <T> List<T> readCsv(String resourcePath, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        try (
                Reader reader = new InputStreamReader(
                        Objects.requireNonNull(CsvUtils.class.getClassLoader().getResourceAsStream(resourcePath))
                );
                CSVParser csvParser = CSVFormat.DEFAULT.builder().setHeader().get().parse(reader);
        ) {
            for (CSVRecord record : csvParser) {
                T obj = clazz.getDeclaredConstructor().newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    String value = record.get(field.getName());

                    if (value != null) {
                        Object castedValue = castValue(field.getType(), value);
                        field.set(obj, castedValue);
                    }
                }
                result.add(obj);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read and map CSV to class " + clazz.getSimpleName(), e);
        }

        return result;
    }

    private static Object castValue(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == long.class || type == Long.class) return Long.parseLong(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == LocalDate.class) return LocalDate.parse(value, DATE_FORMAT);
        return value;
    }
}
