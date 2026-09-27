import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        LinkedList<String[]> transactionList = new LinkedList<>();
        LinkedList<String[]> customerList = new LinkedList<>();
        Queue<String[]> transactionQueue = new LinkedList<>();
        Stack<String[]> failedTransactionsStack = new Stack<>();
        File file = new File("transactions.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length == 3) {
                    transactionList.add(parts);
                    String customerName = parts[0];
                    boolean exists = false;
                    for (String[] cust : customerList) {
                        if (cust[0].equals(customerName)) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        customerList.add(new String[]{customerName, "0"});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File transactions.txt tidak ditemukan!");
            return;
        }
        while (!transactionList.isEmpty()) {
            transactionQueue.add(transactionList.poll());
        }
        while (!transactionQueue.isEmpty()) {
            String[] currentTx = transactionQueue.poll();
            String name = currentTx[0];
            String type = currentTx[1];
            int amount = Integer.parseInt(currentTx[2]);
            for (String[] cust : customerList) {
                if (cust[0].equals(name)) {
                    int currentBalance = Integer.parseInt(cust[1]);

                    if (type.equalsIgnoreCase("DEPOSIT")) {
                        currentBalance += amount;
                        cust[1] = String.valueOf(currentBalance);
                    } else if (type.equalsIgnoreCase("WITHDRAW")) {
                        if (amount > currentBalance) {
                            failedTransactionsStack.push(currentTx);
                        } else {
                            currentBalance -= amount;
                            cust[1] = String.valueOf(currentBalance);
                        }
                    }
                    break;
                }
            }
        }

        System.out.println("=== Final Balances ===");
        for (String[] cust : customerList) {
            System.out.println(cust[0] + ": " + cust[1]);
        }

        System.out.println("=== Failed Transactions ===");
        while (!failedTransactionsStack.isEmpty()) {
            String[] failedTx = failedTransactionsStack.pop();
            System.out.println(failedTx[0] + " " + failedTx[1] + " " + failedTx[2]);
        }
    }
}