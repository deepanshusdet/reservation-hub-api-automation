package utils;

public class ApiLogStore {

    private static ThreadLocal<StringBuilder> logs =
            ThreadLocal.withInitial(StringBuilder::new);

    public static void clear() {
        logs.get().setLength(0);
    }

    public static void add(String text) {
        logs.get().append(text).append("\n\n");
    }

    public static String get() {
        return logs.get().toString();
    }
}