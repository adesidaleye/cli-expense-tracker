import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Expense> expenses = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        String filePath = "C:\\Users\\HomePC\\Desktop\\expenses.txt";
        readExpense(filePath, expenses);

        while (true) {
            System.out.println("""
                       1. Add expense
                       2. View all expenses
                       3. View total spent
                       4. View by category
                       5. Exit
                    """);

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();

                    addExpense(filePath, description, amount, category);

                    expenses.add(new Expense(description, amount, category));
                    break;
                case 2:
                    for (Expense expense : expenses) {
                        System.out.println(expense.toString());
                    }
                    break;
                case 3:
                    Expense.getTotalSpent();
                    break;
                case 4:
                    System.out.print("Enter category to filter by: ");
                    List<String> list = getCategory(expenses, scanner.nextLine());

                    for (String s : list) {
                        System.out.println(s);
                    }

                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, choose between 1 to 3");
                    break;
            }
        }
    }

    public static void addExpense(String filePath, String description, double amount, String category) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.format("%s,%.2f,%s%n", description, amount, category));
        } catch (FileNotFoundException e) {
            System.out.println("File location was not found");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }

    public static void readExpense(String filePath, ArrayList<Expense> expenses) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] content = line.split(",");

                expenses.add(new Expense(content[0], Double.parseDouble(content[1]), content[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File location was not found");
        } catch (IOException e) {
            System.out.println("Could not read from file");
        }
    }

    public static List<String> getCategory(ArrayList<Expense> expenses, String category) {
        ArrayList<String> categoryList = new ArrayList<>();

        for (Expense expense : expenses) {
            if (Objects.equals((expense.getCategory()).toLowerCase(), category.toLowerCase())) {
                categoryList.add(expense.toString());
            }
        }

        return  categoryList;
    }

}