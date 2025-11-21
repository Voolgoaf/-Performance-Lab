import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Вставьте Файл эллипса и файл точек");
            return;
        }

        String ellipseFile = args[0];
        String pointsFile = args[1];


        double centerX, centerY, radiusX, radiusY;


        try (BufferedReader br = new BufferedReader(new FileReader(ellipseFile))) {
            String line = br.readLine();
            String[] parts = line.trim().split("\\s+");
            if (parts.length != 4) {
                System.out.println("Неверный формат файла эллипса");
                return;
            }
            centerX = Double.parseDouble(parts[0]);
            centerY = Double.parseDouble(parts[1]);
            radiusX = Double.parseDouble(parts[2]);
            radiusY = Double.parseDouble(parts[3]);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла эллипса: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат чисел в файле эллипса");
            return;
        }

        try (BufferedReader brPoints = new BufferedReader(new FileReader(pointsFile))) {
            String line;
            while ((line = brPoints.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] coords = line.split("\\s+");
                if (coords.length != 2) {
                    System.out.println("0");
                    continue; // пропуск для сломанной строки
                }

                double x, y;
                try {
                    x = Double.parseDouble(coords[0]);
                    y = Double.parseDouble(coords[1]);
                } catch (NumberFormatException e) {
                    System.out.println("0");
                    continue;
                }

                double value = Math.pow((x - centerX) / radiusX, 2) + Math.pow((y - centerY) / radiusY, 2);

                if (Math.abs(value - 1.0) < 1e-12) {
                    // точка на эллипсе
                    System.out.println("0");
                } else if (value < 1.0) {
                    // внутри эллипса
                    System.out.println("1");
                } else {
                    // снаружи
                    System.out.println("2");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла точек: " + e.getMessage());
        }
    }
}