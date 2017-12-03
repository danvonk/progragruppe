import java.util.Random;
import java.util.Arrays;

public class InsertionSort {
    private static int[] recursiveInsertionSort(int[] arr) {
        sort(arr, arr.length - 1);
        return arr;
    }

    private static void swapRecursively(int[] arr, int x, int current) {
        if (current < 0 || (arr[current] <= x)) {
            arr[current + 1] = x;
            return;
        }
        arr[current + 1] = arr[current];
        swapRecursively(arr, x, current - 1);
    }

    private static void sort(int[] array, int n) {
        if (n > 0) {
            sort(array, n - 1);
            int x = array[n];
            swapRecursively(array, array[n], n - 1);
        }
    }

    private static int[] sortI(int[] arr) {
        for (int upto = 1; upto < arr.length; ++upto) {
            for (int current = upto; current > 0; --current) {
                if (arr[current] >= arr[current - 1]) {
                    break;
                } else {
                    //aktuelles Element gehoert weiter vorne einsortiert
                    int tmp = arr[current];
                    arr[current] = arr[current - 1];
                    arr[current - 1] = tmp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Random rng = new Random();
        boolean passed = true;

        passed = test(new int[0]);
        {
            int[] arr = new int[1];
            arr[0] = rng.nextInt(20) - 7;
            passed &= test(arr);
        }
        for (int i = 0; i < 10; ++i) {
            int[] arr = new int[rng.nextInt(20)];
            for (int j = 0; j < arr.length; ++j) {
                arr[j] = rng.nextInt(20) - 7;
            }
            passed &= test(arr);
        }

        if (passed) {
            System.out.println("All tests passed.");
        } else {
            System.out.println("Some tests failed!");
        }
    }

    private static boolean test(int[] arr) {
        int[] resA = sortI(Arrays.copyOf(arr, arr.length));
        int[] resB = recursiveInsertionSort(Arrays.copyOf(arr, arr.length));

        System.out.println(Arrays.toString(resA));
        System.out.println(Arrays.toString(resB));

        return Arrays.equals(resA, resB);
    }
}
