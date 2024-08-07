package ru.ancap.commons.debug;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.ancap.commons.DynData;
import ru.ancap.commons.iterable.StreamIterator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Marker class to use it for leaving debug messages and easily find them with "find usages" in any IDE.
 */
@ThreadSafe
@ToString @EqualsAndHashCode
public class AncapDebug {
    
    public static volatile Consumer<String> OUTPUT_CONSUMER = System.out::println;
    
    public static void debug(@Nullable Object @Nullable... objects) {
        debug0(caller(), objects);
    }
    
    public static void debugArray(@Nullable Object @Nullable[] array) {
        debugArray0(caller(), array);
    }
    
    public static <T> T debugThrough(@Nullable T main, Object... additional) {
        return debugThrough0(caller(), main, additional);
    }
    
    /**
     * Magic method to name line.
     * <p>
     * Example:
     * <pre>
     * debug(named("foo", foo), bar)
     * </pre>
     * will output<br>
     * <pre>
     * === DEBUG in %CALLER% ===
     * <0> foo "%FOO DATA%"
     * <1> "%BAR_DATA%"
     * </pre>
     */
    public static Object named(String name, Object object) { return new Named(name, object); }
    
    /**
     * Magic method to inline objects.
     * <p>
     * Example:
     * <pre>
     * debug(inline(foo, bar))
     * </pre>
     * will output<br>
     * <pre>
     * === DEBUG in %CALLER% ===
     * <0> (2 entries) "%FOO DATA%" "%BAR_DATA%"
     * </pre>
     */
    public static Object inline(Object... objects)         { return new Inline(objects);     }
    
    /* --- PRIVATE ENTRIES --- */
    
    private static void debug0(Optional<StackTraceElement> caller, @Nullable Object @Nullable... objects) {
        String callerInformation = caller
            .map(StackTraceElement::toString)
            .orElse("null");
        StringBuilder debug = new StringBuilder();
        debug.append("=== DEBUG in ").append(callerInformation).append(" ===");
        debug.append("\n");
        if (objects == null) debug.append("null");
        else if (objects.length == 0) debug.append("no objects to inspect provided");
        else for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            debug.append("<").append(i).append("> ");
            debug.append(stringValueOf(object));
            if (i < objects.length - 1) debug.append("\n");
        }
        AncapDebug.soloDebug(new String(debug));
    }
    
    public static void debugArray0(Optional<StackTraceElement> caller, @Nullable Object @Nullable[] array) {
        debug0(caller, new Object[]{array});
    }
    
    public static <T> T debugThrough0(Optional<StackTraceElement> caller, @Nullable T main, Object... additional) {
        var objects = new ArrayList<>();
        objects.add("main");
        objects.add(main);
        objects.addAll(List.of(additional));
        debugArray0(caller, objects.toArray());
        return main;
    }
    
    private record Named(String name, Object object) { }
    private record Inline(Object[] objects) {
        
        @Override
        public String toString() {
            return "("+this.objects.length+" entries) " + Arrays.stream(this.objects())
                .map(AncapDebug::stringValueOf)
                .collect(Collectors.joining(" "));
        }
        
    }
    
    @NotNull
    private static String stringValueOf(@Nullable Object object) {
        if (object == null)                                  return "\"null\"";
        else if (object instanceof AncapDebug.Inline inline) return inline.toString();
        else if (object instanceof AncapDebug.Named named)   return named.name()+" "+stringValueOf(named.object());
        else if (object instanceof Iterable<?> iterable)     return
            debugName(iterable.getClass())+
            "{   "+streamElementsString(StreamIterator.wrap(iterable.iterator())
                .map(Object.class::cast))+"   }";
        else if (object.getClass().isArray())                return "\""+arrayObjectToString(object)+"\"";
        else                                                 return "\""+debugName(object.getClass())+"{  "+object.toString()+"  }"+"\"";
    }
    
    /**
     * Should be used wisely. If you will add packages that have class name intersections with existing packages,
     * it can confuse you.
     */
    public static final List<String> commonPrefixes = new CopyOnWriteArrayList<>(List.of(
        "java.lang",
        "java.util",
        "java.io",
        "java.math",
        "java.net",
        "java.nio",
        "java.security",
        "java.text",
        "java.time"
    ));
    
    private static String debugName(Class<?> class_) {
        String fullName;
        if (class_.getPackageName().isEmpty()) fullName = "<root-package>."+class_.getName();
        else fullName = simplifiedName(class_);
        return fullName;
    }
    
    private static String simplifyName(String fullName) {
        String commonPrefix = commonPrefix(fullName);
        if (commonPrefix != null) return removeDot(fullName.substring(commonPrefix.length()));
        else return fullName;
    }
    
    private static String removeDot(String noPackageName) {
        if (noPackageName.startsWith(".")) return noPackageName.substring(1);
        else return noPackageName;
    }
    
    private static String simplifiedName(Class<?> class_) {
        String fullName = class_.getName();
        if (commonPrefix(fullName) != null) return noPackageName(class_);
        else return fullName;
    }
    
    private static String noPackageName(Class<?> class_) {
        return removeDot(class_.getName().substring(class_.getPackageName().length()));
    }
    
    private static @Nullable String commonPrefix(String name) {
        for (String commonPrefix : commonPrefixes) if (name.startsWith(commonPrefix)) return commonPrefix;
        return null;
    }
    
    private static String arrayObjectToString(@NotNull Object object) {
        if (object instanceof Object[]) return
            debugName(object.getClass().getComponentType()) +
            "[]{   "+streamElementsString(Arrays.stream(((Object[]) object)))+"   }";
        else if (object instanceof boolean[]) return "boolean[]{   "+pwacs(ArrayUtils.toObject( (boolean[]) object))+"   }";
        else if (object instanceof   short[]) return   "short[]{   "+pwacs(ArrayUtils.toObject(   (short[]) object))+"   }";
        else if (object instanceof    byte[]) return    "byte[]{   "+pwacs(ArrayUtils.toObject(    (byte[]) object))+"   }";
        else if (object instanceof    char[]) return    "char[]{   "+pwacs(ArrayUtils.toObject(    (char[]) object))+"   }";
        else if (object instanceof     int[]) return     "int[]{   "+pwacs(ArrayUtils.toObject(     (int[]) object))+"   }";
        else if (object instanceof    long[]) return    "long[]{   "+pwacs(ArrayUtils.toObject(    (long[]) object))+"   }";
        else if (object instanceof   float[]) return   "float[]{   "+pwacs(ArrayUtils.toObject(   (float[]) object))+"   }";
        else if (object instanceof  double[]) return  "double[]{   "+pwacs(ArrayUtils.toObject(  (double[]) object))+"   }";
        else throw new IllegalStateException();
    }
    
    private static String streamElementsString(Stream<Object> stream) {
        return stream
            .map(AncapDebug::stringValueOf)
            .collect(Collectors.joining(", "));
    }
    
    /**
     * pwacs — primitiveWrapperArrayContentsString
     */
    @SafeVarargs
    private static <T> String pwacs(T... array) {
        return Arrays.stream(array)
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
    }
    
    private static void soloDebug(String string) {
        AncapDebug.OUTPUT_CONSUMER.accept(string);
    }
    
    private static Optional<StackTraceElement> caller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        
        if (stackTrace.length > 3) return Optional.of(stackTrace[3]);
        else return Optional.empty();
    }
    
}