package Task5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {
    public static boolean stringEquals(String first, String second) {
        Pattern p = Pattern.compile(second.replaceAll("\\*", ".*"));
        return p.matcher(first).matches();
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            if (stringEquals(args[0], args[1])) System.out.println("OK");
            else System.out.println("KO");
        }
        else System.out.println("Неверное кол-во аргументов");
    }
}
