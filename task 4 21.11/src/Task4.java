import java.io.*;
import java.util.*;

public class Task4 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("<имя файла>");
            return;
        }

        String filename = args[0];
        List<Integer> nums = new ArrayList<>();

        // Чтение файла
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    nums.add(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Кривой файл " + e.getMessage());
            return;
        }

        if (nums.isEmpty()) {
            System.out.println("Массив пустой.");
            return;
        }

        int minVal = Collections.min(nums);
        int maxVal = Collections.max(nums);

        int minMoves = Integer.MAX_VALUE;

        // Перебор всех возможных целей приведения
        for (int target = minVal; target <= maxVal; target++) {
            int totalMoves = 0;
            for (int num : nums) {
                totalMoves += Math.abs(num - target);
                if (totalMoves > 20) { // можно досрочно прервать, если уже больше 20
                    break;
                }
            }
            if (totalMoves < minMoves) {
                minMoves = totalMoves;
            }
        }

        if (minMoves > 20) {
            System.out.println("20 ходов недостаточно для приведения всех элементов массива к одному числу");
        } else {
            System.out.println(minMoves);
        }
    }
}
