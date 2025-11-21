// File: Task3.java
import java.io.*;
import java.util.*;

public class Task3 {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Использование: java Task3 <values.json> <tests.json> <report.json>");
            return;
        }

        String valuesPath = args[0];
        String testsPath = args[1];
        String reportPath = args[2];

        String valuesContent = readFile(valuesPath);
        String testsContent = readFile(testsPath);

        // Создаем карту id -> значение из values.json
        Map<Integer, String> idToValue = parseValues(valuesContent);

        // Обновляем структуру tests.json, заменяя "value": "" на соответствующее значение
        String updatedJson = updateTestsJson(testsContent, idToValue);

        // Записываем результат в report.json
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportPath))) {
            writer.write(updatedJson);
        }

        System.out.println("Готово! Результат записан в " + reportPath);
    }

    // Метод для чтения файла в строку
    private static String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    // Метод парсит values.json для построения карты id -> value
    private static Map<Integer, String> parseValues(String json) {
        Map<Integer, String> map = new HashMap<>();
        int index = 0;
        while (true) {
            int idIdx = json.indexOf("\"id\":", index);
            int valIdx = json.indexOf("\"value\":", index);
            if (idIdx == -1 || valIdx == -1) break;

            int idStart = idIdx + 5;
            int idEnd = json.indexOf(",", idStart);
            String idStr = json.substring(idStart, idEnd).trim();
            int id = Integer.parseInt(idStr);

            int valStart = json.indexOf("\"", valIdx + 7) + 1;
            int valEnd = json.indexOf("\"", valStart);
            String valStr = json.substring(valStart, valEnd);

            map.put(id, valStr);
            index = valEnd;
        }
        return map;
    }

    // Метод обновляет структуру tests.json, заполняя "value" по соответствию id
    private static String updateTestsJson(String json, Map<Integer, String> idToValue) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (true) {
            int idIdx = json.indexOf("\"id\":", index);
            if (idIdx == -1) {
                sb.append(json.substring(index));
                break;
            }

            int idStart = idIdx + 5;
            int idEnd = json.indexOf(",", idStart);
            String idStr = json.substring(idStart, idEnd).trim();
            int id = Integer.parseInt(idStr);

            int valueIdx = json.indexOf("\"value\":", idEnd);
            int valueStartIdx = json.indexOf("\"", valueIdx + 7) + 1;
            int valueEndIdx = json.indexOf("\"", valueStartIdx);
            String currentValue = json.substring(valueStartIdx, valueEndIdx);

            // Заменяем только, если поле пустое
            String newValue = currentValue;
            if (currentValue.equals("")) {
                if (idToValue.containsKey(id)) {
                    newValue = idToValue.get(id);
                }
            }

            // Собираем строку
            sb.append(json, index, valueStartIdx);
            sb.append(newValue);
            index = valueEndIdx + 1;
        }

        return sb.toString();
    }
}