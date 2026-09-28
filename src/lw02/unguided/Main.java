import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static final int MAX_BORROW = 2;
    public static void main(String[] args) {
        LinkedList<String[]> requestList = new LinkedList<>();
        LinkedList<String[]> bookList = new LinkedList<>();
        LinkedList<String[]> memberList = new LinkedList<>();
        LinkedList<String[]> successList = new LinkedList<>();
        Queue<String[]> requestQueue = new LinkedList<>();
        Stack<String[]> failedRequestsStack = new Stack<>();
        bookList.add(new String[]{"Kalkulus", "2"});
        bookList.add(new String[]{"Fisika", "1"});
        bookList.add(new String[]{"Statistika", "2"});
        File file = new File("borrowing.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    requestList.add(parts);
                    String memberName = parts[0];
                    boolean exists = false;
                    for (String[] member : memberList) {
                        if (member[0].equals(memberName)) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        memberList.add(new String[]{memberName, "0"});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File borrowing.txt tidak ditemukan!");
            return;
        }
        while (!requestList.isEmpty()) {
            requestQueue.add(requestList.poll());
        }
        while (!requestQueue.isEmpty()) {
            String[] currentReq = requestQueue.poll();
            String name = currentReq[0];
            String title = currentReq[1];
            String[] bookData = null;
            for (String[] book : bookList) {
                if (book[0].equals(title)) {
                    bookData = book;
                    break;
                }
            }

            String[] memberData = null;
            for (String[] member : memberList) {
                if (member[0].equals(name)) {
                    memberData = member;
                    break;
                }
            }

            if (bookData == null || memberData == null) {
                failedRequestsStack.push(currentReq);
                continue;
            }

            int stock = Integer.parseInt(bookData[1]);
            int borrowed = Integer.parseInt(memberData[1]);

            if (stock > 0 && borrowed < MAX_BORROW) {
                bookData[1] = String.valueOf(stock - 1);
                memberData[1] = String.valueOf(borrowed + 1);
                successList.add(currentReq);
            } else {
                failedRequestsStack.push(currentReq);
            }
        }

        System.out.println("=== Successfully Processed Requests ===");
        for (String[] req : successList) {
            System.out.println(req[0] + " " + req[1]);
        }

        System.out.println();
        System.out.println("=== Remaining Book Stock ===");
        for (String[] book : bookList) {
            System.out.println(book[0] + " : " + book[1]);
        }

        System.out.println();
        System.out.println("=== Failed Requests ===");
        while (!failedRequestsStack.isEmpty()) {
            String[] failedReq = failedRequestsStack.pop();
            System.out.println(failedReq[0] + " " + failedReq[1]);
        }
    }
}