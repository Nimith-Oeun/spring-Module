import java.util.*;

public class ArrayApplication {

    private static final List<Integer> nums = List.of(1, 2, 3, 4, 2, 5, 1, 6);

    public void FindDuplicates () {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();

        for (int n : nums) {
            if (!seen.add(n)) {
                duplicates.add(n);
            }
        }

        System.out.println("Duplicates: " + duplicates);
    }

    public void isPrime(List<Integer> nums) {
        List<Integer> sortedNums = new ArrayList<>();
        for (int n : nums){
            if (n <=1) {
                continue;
            }
            boolean isPrime = true;
            for (int i = 2; i <= Math.sqrt(n); i++){
                if (n % i == 0){
                    isPrime = false;
                    break;
                }
            }
            if (isPrime){
                sortedNums.add(n);
            }
        }
        System.out.println("Prime numbers: " + sortedNums);
    }



    public static void main(String[] args) {
        ArrayApplication app = new ArrayApplication();
        app.FindDuplicates();
        app.isPrime(nums);
    }
}

