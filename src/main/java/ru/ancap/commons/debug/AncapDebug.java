package ru.ancap.commons.debug;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

@ToString @EqualsAndHashCode
public class AncapDebug {
    
    public static Consumer<String> OUTPUT_CONSUMER = string -> System.out.println("DEBUG "+string);

    // Класс для того, чтобы оставлять дебаг мессаджи через метод, входящий в него и потом через средства Intellij IDEA
    // удалять все использования этого класса после завершения дебага
    
    public static void debug(@Nullable Object... objects) {
        StringBuilder debug = new StringBuilder();
        for (Object object : objects) {
            debug.append(" \"").append(stringValueOf(object)).append("\"");
        }
        AncapDebug.soloDebug(new String(debug));
    }
    
    public static void debugArray(@Nullable Object[] array) {
        AncapDebug.debug(new Object[]{array});
    }

    private static String stringValueOf(@Nullable Object object) {
        if (object == null)                    return "null";
        else if (!object.getClass().isArray()) return object.toString();
        else                                   return arrayObjectToString(object);
    }

    private static String arrayObjectToString(@NotNull Object object) {
        if      (object instanceof Object  []) return Arrays.deepToString( (Object  []) object);
        else if (object instanceof boolean []) return Arrays.toString(     (boolean []) object);
        else if (object instanceof byte    []) return Arrays.toString(     (byte    []) object);
        else if (object instanceof short   []) return Arrays.toString(     (short   []) object);
        else if (object instanceof char    []) return Arrays.toString(     (char    []) object);
        else if (object instanceof int     []) return Arrays.toString(     (int     []) object);
        else if (object instanceof long    []) return Arrays.toString(     (long    []) object);
        else if (object instanceof float   []) return Arrays.toString(     (float   []) object);
        else if (object instanceof double  []) return Arrays.toString(     (double  []) object);
        else throw new IllegalStateException();
    }

    private static void soloDebug(String string) {
        AncapDebug.OUTPUT_CONSUMER.accept(string);
    }

}
