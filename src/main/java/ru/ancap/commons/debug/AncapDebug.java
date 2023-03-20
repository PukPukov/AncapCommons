package ru.ancap.commons.debug;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;

@ToString @EqualsAndHashCode
public class AncapDebug {

    // Класс для того, чтобы оставлять дебаг мессаджи через метод, входящий в него и потом через средства Intellij IDEA
    // удалять все использования этого класса после завершения дебага
    
    public static void debug(Object... objects) {
        StringBuilder debug = new StringBuilder();
        for (Object object : objects) {
            debug.append(" \"").append(stringValueOf(object)).append("\"");
        }
        AncapDebug.soloDebug(new String(debug));
    }

    private static String stringValueOf(Object object) {
        if (object == null)                    return "null";
        else if (!object.getClass().isArray()) return object.toString();
        else                                   return arrayObjectToString(object);
    }

    private static String arrayObjectToString(Object object) {
        if      (object instanceof Object  []) return Arrays.deepToString( (Object[])  object);
        else if (object instanceof boolean []) return Arrays.toString(     (boolean[]) object);
        else if (object instanceof byte    []) return Arrays.toString(     (byte[])    object);
        else if (object instanceof short   []) return Arrays.toString(     (short[])   object);
        else if (object instanceof char    []) return Arrays.toString(     (char[])    object);
        else if (object instanceof int     []) return Arrays.toString(     (int[])     object);
        else if (object instanceof long    []) return Arrays.toString(     (long[])    object);
        else if (object instanceof float   []) return Arrays.toString(     (float[])   object);
        else if (object instanceof double  []) return Arrays.toString(     (double[])  object);
        else throw new IllegalStateException();
    }

    private static void soloDebug(String string) {
        System.out.println("DEBUG "+string);
    }

    public static void permanentDebug(Object object) {
        System.out.println(object);
    }

}
