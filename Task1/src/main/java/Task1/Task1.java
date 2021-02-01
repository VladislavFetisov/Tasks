package Task1;

public class Task1 {
    static String itoBase(int nb, String base) {
        int length = base.length();
        StringBuilder result = new StringBuilder();
        while (nb / length != 0) {
            result.append(base.charAt(nb % length));
            nb /= length;
        }
        result.append(base.charAt(nb));
        return result.reverse().toString();
    }

    static String itoBase(String nb, String baseSrc, String baseDst) {
        int decimal = 0;
        for (int i = nb.length() - 1; i >= 0; i--)
            decimal += baseSrc.indexOf(nb.charAt(i)) * (Math.pow(baseSrc.length(), nb.length() - 1 - i));
        return itoBase(decimal, baseDst);
    }

    public static void main(String[] args) {
        switch (args.length) {
            case 2:
                System.out.println(itoBase(Integer.parseInt(args[0]), args[1]));
                break;
            case 3:
                System.out.println(itoBase(args[0], args[1], args[2]));
                break;
            default:
                System.err.println("DecimalNum|Num [baseSrc] baseDst");
        }
        //Тут конечно можно было подключить Args4j
    }
}
