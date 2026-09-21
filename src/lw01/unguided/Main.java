import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Rental[] rentals;
        int[] unitsArr;

        try (Scanner scanner = new Scanner(new File("rentals.txt"))) {
            int total = scanner.nextInt(); // skip baris pertama (jumlah record)
            rentals = new Rental[total];
            unitsArr = new int[total];

            for (int i = 0; i < total; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                int units = scanner.nextInt();

                if (type.equalsIgnoreCase("LAPTOP")) {
                    rentals[i] = new LaptopRental(id, days);
                } else if (type.equalsIgnoreCase("PROJECTOR")) {
                    rentals[i] = new ProjectorRental(id, days);
                }
                unitsArr[i] = units;
            }
        } catch (FileNotFoundException e) {
            System.out.println("rentals.txt tidak ditemukan.");
            return;
        }

        for (int i = 0; i < rentals.length; i++) {
            Rental rental = rentals[i];
            int units = unitsArr[i];
            System.out.println(rental.getId() + " | " + rental.label() + " | " + rental.calculateCharge(units));
        }
    }
}