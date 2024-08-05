package ru.ancap.commons.debug;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Marker class to use it for leaving debug messages and easily find them with "find usages" in any IDE.
 */
@ToString @EqualsAndHashCode
public class AncapDebug {
    
    public static Consumer<String> OUTPUT_CONSUMER = string -> System.out.println("DEBUG "+string);
    
    public static <T> T debugThrough(@Nullable T main, Object... additional) {
        var objects = new ArrayList<>();
        objects.add("main");
        objects.add(main);
        objects.addAll(List.of(additional));
        debugArray(objects.toArray());
        return main;
    }
    
    public static void debug(@Nullable Object... objects) {
        StringBuilder debug = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if (i != 0) debug.append(" ");
            debug.append("\"");
            debug.append(stringValueOf(object));
            debug.append("\"");
        }
        AncapDebug.soloDebug(new String(debug));
    }
    
    public static void debugArray(@Nullable Object[] array) {
        AncapDebug.debug(new Object[]{array});
    }

    @NotNull
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