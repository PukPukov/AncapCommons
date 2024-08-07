package ru.ancap.commons.debug;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Marker class to use it for leaving debug messages and easily find them with "find usages" in any IDE.
 */
@ThreadSafe
@ToString @EqualsAndHashCode
public class AncapDebug {
    
    public static volatile Consumer<String> OUTPUT_CONSUMER = string -> System.out.println("DEBUG "+string);
    
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
        else if (!object.getClass().isArray()) return object.getClass()+"{"+object+"}";
        else                                   return arrayObjectToString(object);
    }

    private static String arrayObjectToString(@NotNull Object object) {
        if (object instanceof Object []) return object.getClass().getComponentType().getName()+"[]{"+ Arrays.stream(((Object[]) object))
            .map(AncapDebug::stringValueOf)
            .collect(Collectors.joining(", "))+"}";
        else if (object instanceof boolean []) return "boolean[]{" +Arrays.toString((boolean []) object)+"}";
        else if (object instanceof byte    []) return "byte[]{"    +Arrays.toString((byte    []) object)+"}";
        else if (object instanceof short   []) return "short[]{"   +Arrays.toString((short   []) object)+"}";
        else if (object instanceof char    []) return "char[]{"    +Arrays.toString((char    []) object)+"}";
        else if (object instanceof int     []) return "int[]{"     +Arrays.toString((int     []) object)+"}";
        else if (object instanceof long    []) return "long[]{"    +Arrays.toString((long    []) object)+"}";
        else if (object instanceof float   []) return "float[]{"   +Arrays.toString((float   []) object)+"}";
        else if (object instanceof double  []) return "double[]{"  +Arrays.toString((double  []) object)+"}";
        else throw new IllegalStateException();
    }

    private static void soloDebug(String string) {
        AncapDebug.OUTPUT_CONSUMER.accept(string);
    }

}