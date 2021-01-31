package Task2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Task2 {
    private static void merge(int[] elements, int begin, int middle, int end) {
        int[] left = Arrays.copyOfRange(elements, begin, middle);
        int[] right = Arrays.copyOfRange(elements, middle, end);
        int li = 0, ri = 0;
        for (int i = begin; i < end; i++) {
            if (li < left.length && (ri == right.length || left[li] <= right[ri])) {
                elements[i] = left[li++];
            } else {
                elements[i] = right[ri++];
            }
        }
    }

    private static void mergeSort(int[] elements, int begin, int end) {
        if (end - begin <= 1) return;
        int middle = (begin + end) / 2;
        mergeSort(elements, begin, middle);
        mergeSort(elements, middle, end);
        merge(elements, begin, middle, end);
    }

    public static void mergeSort(int[] elements) {
        mergeSort(elements, 0, elements.length);
    }

    static int sumOfNumbers(int[] array, double percentile) {
        mergeSort(array);//O(n*logn)
        int average = Arrays.stream(array).sum() / array.length;//O(n)
        int perc = array[(int) Math.ceil((percentile * array.length)) - 1];
        int result = 0;
        for (Integer num : array) if ((num >= perc && num <= average) || (num <= perc && num >= average)) result += num;
        return result;
    }

    public static void main(String[] args) {
        String line;
        ArrayList<Integer> arrayList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(args[0]), UTF_8))) {
            line = reader.readLine();
            while (line != null) {
                arrayList.add(Integer.parseInt(line));
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("С файлом что-то не так!");
        }

        System.out.println(Task2.sumOfNumbers(arrayList.stream().mapToInt(integer -> integer).toArray(), 0.9));
    }
}
