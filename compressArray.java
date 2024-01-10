import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class compressArray {
    public static String serialize(int[] numbers) {
        Map<Integer, Integer> countMap = new LinkedHashMap<>();

        for (int num : numbers) {
            countMap.put(num, countMap.getOrDefault(num, 0)+1);
        }

        StringBuilder compressedData = new StringBuilder();

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                compressedData.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            } else {
                compressedData.append(entry.getKey()).append(",");
            }

        }

        if (!compressedData.isEmpty()) {
            compressedData.setLength((compressedData.length() -1 ));
        }

        return compressedData.toString();
    }

    public static int[] deserialize(String data) {
        String[] pairs = data.split(",");
        List<Integer> numbers = new ArrayList<>();

        for (String pair: pairs) {
            String[] parts = pair.split(":");
            int num = Integer.parseInt(parts[0]);
            int count;
            if (parts.length>1) {
                count = Integer.parseInt(parts[1]);
            } else count = 1;

            for (int i =0; i < count; i++) {
                numbers.add(num);
            }
        }

        int[] result = new int[numbers.size()];
        for (int i=0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        return result;
    }

    public static int[] generateRandomNumbers(int count, int min, int max) {
        int[] randomNumbers = new int[count];
        for (int i = 0; i < count; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            randomNumbers[i] = randomNum;
        }
        return randomNumbers;
    }

    public static float randomProcess(int count, int min, int max) {
        int[] numbers50 = generateRandomNumbers(count, min,max);
        System.out.println("random numbers: "+ Arrays.toString(numbers50));
        String serializeData = serialize(numbers50);
        int[] deserializedNumbers = deserialize(serializeData);
        String originalArrayString = String.join(",", Arrays.stream(numbers50).mapToObj(String::valueOf).toArray(String[]::new));
        float percentage = (float)  serializeData.length() * 100 / originalArrayString.length();

        System.out.println("Original array string length " + originalArrayString.length());
        System.out.println("Serialized array string length " + serializeData.length());
        System.out.println("Compress rate " + (100 - percentage));
        return 100 - percentage;
    }
    public static void main(String[] args) {
        float sum = 0;
        System.out.println("---------- Random 50 number ------------");
        sum += randomProcess(50,1,300);
        System.out.println("---------- Random 100 number ------------");
        sum += randomProcess(100,1,300);
        System.out.println("---------- Random 500 number ------------");
        sum += randomProcess(500,1,300);
        System.out.println("---------- Random 1000 number ------------");
        sum += randomProcess(1000,1,300);

        System.out.println("---------- Random 50 number  1 digit------------");
        sum += randomProcess(50,1,9);
        System.out.println("---------- Random 100 number 1 digit ------------");
        sum += randomProcess(100,1,9);
        System.out.println("---------- Random 500 number 1 digit ------------");
        sum += randomProcess(500,1,9);
        System.out.println("---------- Random 1000 number 1 digit ------------");
        sum += randomProcess(1000,1,9);

        System.out.println("---------- Random 50 number  2 digit------------");
        sum += randomProcess(50,10,99);
        System.out.println("---------- Random 100 number 2 digit ------------");
        sum += randomProcess(100,10,99);
        System.out.println("---------- Random 500 number 2 digit ------------");
        sum += randomProcess(500,10,99);
        System.out.println("---------- Random 1000 number 2 digit ------------");
        sum += randomProcess(1000,10,99);

        System.out.println("---------- Random 50 number  3 digit------------");
        sum += randomProcess(50,100,300);
        System.out.println("---------- Random 100 number 2 digit ------------");
        sum += randomProcess(100,100,300);
        System.out.println("---------- Random 500 number 2 digit ------------");
        sum += randomProcess(500,100,300);
        System.out.println("---------- Random 1000 number 2 digit ------------");
        sum += randomProcess(1000,100,300);

        System.out.println("Average percentage = "+ sum / 16);
    }
}
