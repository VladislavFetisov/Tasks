package Task3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static Task3.Triangle.findAngle;
import static Task3.Triangle.signOfSimilar;
import static java.nio.charset.StandardCharsets.UTF_8;

class Triangle {
    public final int[] A;//[x,y,z]
    public final int[] B;
    public final int[] C;

    public Triangle(int[] a, int[] b, int[] c) {
        A = a;
        B = b;
        C = c;
    }

    static double findAngle(double x1, double x2, double y1, double y2, double z1, double z2) {
        return Math.acos(Math.abs(x1 * x2 + y1 * y2 + z1 * z2) /
                (Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1) * Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2)));
    }

    static boolean thirdSignOfSimilar(double ab1, double bc1, double ca1,
                                      double ab2, double bc2, double ca2) {
        return ((ab2 / ab1 == bc2 / bc1) || (bc2 / bc1 == ca2 / ca1) || (ab2 / ab1 == ca2 / ca1));
    }

    static boolean signOfSimilar(double ab1, double bc1, double ca1,
                                 double ab2, double bc2, double ca2,
                                 double angleB1, double angleC1, double angleA1,
                                 double angleB2, double angleC2, double angleA2) {
        return ((ab1 / ab2 == bc1 / bc2 && angleB1 == angleB2) ||
                (bc1 / bc2 == ca1 / ca2 && angleC1 == angleC2) ||
                (ab1 / ab2 == ca1 / ca2 && angleA1 == angleA2))
                || thirdSignOfSimilar(ab1, bc1, ca1, ab2, bc2, ca2)
                || firstSignOfSimilar(angleB1, angleC1, angleA1, angleB2, angleC2, angleA2);
    }

    static boolean firstSignOfSimilar(double angleB1, double angleC1, double angleA1,
                                      double angleB2, double angleC2, double angleA2) {
        return (angleB1 == angleB2 && angleC1 == angleC2) ||
                (angleC1 == angleC2 && angleA1 == angleA2) ||
                (angleA1 == angleA2 && angleB1 == angleB2);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "A=" + Arrays.toString(A) +
                ", B=" + Arrays.toString(B) +
                ", C=" + Arrays.toString(C) +
                '}';
    }
}

public class Task3 {

    static boolean isTriangleSimilar(Triangle first, Triangle second) {

        double x1 = first.B[0] - first.A[0];
        double x2 = first.C[0] - first.B[0];
        double x3 = first.A[0] - first.C[0];
        double y1 = first.B[1] - first.A[1];
        double y2 = first.C[1] - first.B[1];
        double y3 = first.A[1] - first.C[1];
        double z1 = first.B[2] - first.A[2];
        double z2 = first.C[2] - first.B[2];
        double z3 = first.A[2] - first.C[2];

        double angleB1 = findAngle(x1, x2, y1, y2, z1, z2);
        double angleC1 = findAngle(x2, x3, y2, y3, z2, z3);
        double angleA1 = findAngle(x1, x3, y1, y3, z1, z3);
        double ab1 = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
        double bc1 = Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);
        double ca1 = Math.sqrt(x3 * x3 + y3 * y3 + z3 * z3);

        //Для 2 треугольника
        x1 = second.B[0] - second.A[0];
        x2 = second.C[0] - second.B[0];
        x3 = second.A[0] - second.C[0];
        y1 = second.B[1] - second.A[1];
        y2 = second.C[1] - second.B[1];
        y3 = second.A[1] - second.C[1];
        z1 = second.B[2] - second.A[2];
        z2 = second.C[2] - second.B[2];
        z3 = second.A[2] - second.C[2];

        double angleB2 = findAngle(x1, x2, y1, y2, z1, z2);
        double angleC2 = findAngle(x2, x3, y2, y3, z2, z3);
        double angleA2 = findAngle(x1, x3, y1, y3, z1, z3);
        double ab2 = Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
        double bc2 = Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);
        double ca2 = Math.sqrt(x3 * x3 + y3 * y3 + z3 * z3);

        return signOfSimilar(ab1, bc1, ca1, ab2, bc2, ca2, angleB1, angleC1, angleA1, angleB2, angleC2, angleA2);

    }

    public static void main(String[] args) {
        String line = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(args[0]), UTF_8))) {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("С файлом что-то не так!");
        }
        assert line != null;
        jsonParser(line);
    }

    private static void jsonParser(String str) {
        try {
            Triangle triangle1 = new Triangle(new int[3], new int[3], new int[3]);
            Triangle triangle2 = new Triangle(new int[3], new int[3], new int[3]);

            JSONObject json = (JSONObject) JSONValue.parseWithException(str);

            fillTriangle(triangle1,(JSONObject) json.get("triangle1"));
            fillTriangle(triangle2,(JSONObject) json.get("triangle2"));

            System.out.println(isTriangleSimilar(triangle1,triangle2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static void fillTriangle(Triangle triangle,JSONObject jsonObject){
        JSONArray jsonArray = (JSONArray) jsonObject.get("A");
        for (int i = 0; i < jsonArray.size(); i++) {
            triangle.A[i]= Integer.parseInt(String.valueOf(jsonArray.get(i)));
        }
        jsonArray = (JSONArray) jsonObject.get("B");
        for (int i = 0; i < jsonArray.size(); i++) {
            triangle.B[i]= Integer.parseInt(String.valueOf(jsonArray.get(i)));
        }
        jsonArray = (JSONArray) jsonObject.get("C");
        for (int i = 0; i < jsonArray.size(); i++) {
            triangle.C[i]= Integer.parseInt(String.valueOf(jsonArray.get(i)));
        }
    }
}