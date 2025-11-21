public class Task1 {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Использование: java CircularArrayPath n1 m1 n2 m2");
            return;
        }

        int n1 = Integer.parseInt(args[0]);
        int m1 = Integer.parseInt(args[1]);
        int n2 = Integer.parseInt(args[2]);
        int m2 = Integer.parseInt(args[3]);

        String path1 = getPath(n1, m1);
        String path2 = getPath(n2, m2);

        System.out.println(path1 + path2);
    }

    public static String getPath(int n, int m) {
        StringBuilder path = new StringBuilder();

        int start = 1;
        int currentStart = start;
        boolean firstTime = true;

        do {

            path.append(currentStart);
            currentStart = ((currentStart + m - 1) % n) + 1;
            if (currentStart == start && !firstTime) {
                break;
            }
            firstTime = false;
        } while (true);

        return path.toString();
    }
}
