package practice;

public class june30_assessment1 {

    public static void main(String[] args) {
        int[] intArray = {2,4,13,15,7,8,9};
        int k = 17;
        String pairs = findPairsThatSumK(intArray, k);
        System.out.println("These are the found pairs:");
        System.out.println(pairs);

    }

    // Task: find pairs on integer array whose sum is equal to k
    // Brute force: O(n^2)
    public static String findPairsThatSumK(int[] arr, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] + arr[j] == k) {
                    sb.append(String.format("(%d, %d)\n", arr[i], arr[j]));
                }
            }
        }
        return sb.toString();
    }
    
}
