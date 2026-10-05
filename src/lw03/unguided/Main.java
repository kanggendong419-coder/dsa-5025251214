import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> enrollment = new HashMap<>();
        List<String> courseOrder = new ArrayList<>();
        List<String> checkResults = new ArrayList<>();
        int rejected = 0;

        try (Scanner sc = new Scanner(new File("enrollment.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) continue;

                String[] parts = line.split(" ");
                String type = parts[0];
                String code = parts[1];

                if (type.equals("REGISTER")) {
                    int count = Integer.parseInt(parts[2]);
                    if (count <= 0) {
                        rejected++;
                    } else if (!enrollment.containsKey(code)) {
                        enrollment.put(code, count);
                        courseOrder.add(code);
                    } else {
                        enrollment.put(code, enrollment.get(code) + count);
                    }
                } else if (type.equals("WITHDRAW")) {
                    int count = Integer.parseInt(parts[2]);
                    if (count <= 0) {
                        rejected++;
                    } else if (enrollment.containsKey(code) && enrollment.get(code) >= count) {
                        enrollment.put(code, enrollment.get(code) - count);
                    } else {
                        rejected++;
                    }
                } else if (type.equals("CHECK")) {
                    if (enrollment.containsKey(code)) {
                        checkResults.add(code + ": " + enrollment.get(code) + " students");
                    } else {
                        checkResults.add(code + ": Not found");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File enrollment.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Enrollment Checks =====");
        for (int i = 0; i < checkResults.size(); i++) {
            System.out.println(checkResults.get(i));
        }

        System.out.println("===== Final Enrollment =====");
        for (int i = 0; i < courseOrder.size(); i++) {
            String code = courseOrder.get(i);
            System.out.println(code + ": " + enrollment.get(code) + " students");
        }
        System.out.println("Rejected operations: " + rejected);
    }
}