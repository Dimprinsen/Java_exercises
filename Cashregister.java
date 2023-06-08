/**
 * This program is a cash register system that allows users to perform various operations such as adding articles, 
 * removing articles, displaying the list of articles, registering sales, displaying the order history, 
 * sorting and displaying the order history table, and quitting the program.
 */
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] articles = new int[10][3]; // Array to store articles [item number, quantity, price]
        int[][] sales = new int[1000][3]; // Array to store sales [item number, number, sum]
        Date[] salesDate = new Date[1000]; // Array to store sales dates

        boolean quit = false;
        while (!quit) {
            int choice = menu();
            switch (choice) {
                case 1:
                    System.out.print("Enter the number of articles to add: ");
                    int numberOfArticles = input();
                    articles = insertArticles(articles, articles[articles.length - 1][0] + 1, numberOfArticles);
                    break;
                case 2:
                    articles = removeArticle(articles);
                    break;
                case 3:
                    printArticles(articles);
                    break;
                case 4:
                    sellArticle(sales, salesDate, articles);
                    break;
                case 5:
                    printSales(sales, salesDate);
                    break;
                case 6:
                    sortedTable(sales, salesDate);
                    break;
                case 7:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    // Displays the main menu and returns the user's choice
    public static int menu() {
        System.out.println("\n--- Cash Register System Menu ---");
        System.out.println("1. Insert articles");
        System.out.println("2. Remove an article");
        System.out.println("3. Display a list of articles");
        System.out.println("4. Register a sale");
        System.out.println("5. Display order history");
        System.out.println("6. Sort and display order history table");
        System.out.println("7. Quit");
        System.out.print("Your choice: ");
        return input();
    }

    // Reads and returns an integer input from the user
    public static int input() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    // Checks if the articles array is full and resizes it if necessary
    public static int[][] checkFull(int[][] articles, int noOfArticles) {
        int currentSize = articles.length;
        int requiredSize = currentSize + noOfArticles;
        if (requiredSize > currentSize) {
            int[][] newArticles = new int[requiredSize][3];
            System.arraycopy(articles, 0, newArticles, 0, currentSize);
            return newArticles;
        }
        return articles;
    }

    // Inserts randomly generated articles into the articles array
    public static int[][] insertArticles(int[][] articles, int articleNumber, int noOfArticles) {
        if (noOfArticles > 0) {
            int currentSize = articles.length;
            int[][] updatedArticles = new int[currentSize + noOfArticles][3];
            System.arraycopy(articles, 0, updatedArticles, 0, currentSize);

            for (int i = currentSize; i < currentSize + noOfArticles; i++) {
                updatedArticles[i][0] = articleNumber + i - currentSize;
                updatedArticles[i][1] = (int) (Math.random() * 10) + 1; // Random quantity (1-10 pcs)
                updatedArticles[i][2] = (int) (Math.random() * 901) + 100; // Random price (SEK 100-1000)
            }
            return updatedArticles;
        } else {
            System.out.println("Invalid number of articles. Please enter a value greater than 0.");
            return articles; // Return the original array if the number of articles is invalid
        }
    }

    // Removes an article from the articles array based on the given article number
    public static int[][] removeArticle(int[][] articles) {
        System.out.print("Enter the article number to remove: ");
        int articleNumber = input();
        if (articleNumber > 0) {
            int articleIndex = -1;
            for (int i = 0; i < articles.length; i++) {
                if (articles[i][0] == articleNumber) {
                    articleIndex = i;
                    break;
                }
            }
            if (articleIndex != -1) {
                int[][] updatedArticles = new int[articles.length - 1][3];
                int counter = 0;
                for (int i = 0; i < articles.length; i++) {
                    if (i != articleIndex) {
                        updatedArticles[counter++] = articles[i];
                    }
                }
                System.out.println("Article removed successfully.");
                return updatedArticles;
            } else {
                System.out.println("Article not found.");
                return articles;
            }
        } else {
            System.out.println("Invalid article number. Please enter a value greater than 0.");
            return articles;
        }
    }

    // Prints the list of articles stored in the articles array
    public static void printArticles(int[][] articles) {
        System.out.println("\n--- List of Articles ---");
        System.out.println("Item No.\tQuantity\tPrice");
        for (int i = 0; i < articles.length; i++) {
            System.out.printf("%-10d\t%-10d\t%d SEK%n", articles[i][0], articles[i][1], articles[i][2]);
        }
    }

    // Registers a sale by updating the sales and articles arrays with the sold article and quantity
public static void sellArticle(int[][] sales, Date[] salesDate, int[][] articles) {
    System.out.print("Enter the article number to sell: ");
    int articleNumber = input();

    int articleIndex = -1;
    for (int i = 0; i < articles.length; i++) {
        if (articles[i][0] == articleNumber) {
            articleIndex = i;
            break;
        }
    }
    if (articleIndex != -1) {
        System.out.print("Enter the quantity to sell: ");
        int quantity = input();
        if (quantity > 0) {
            int availableQuantity = articles[articleIndex][1];
            if (quantity <= availableQuantity) {
                int totalPrice = quantity * articles[articleIndex][2];

                int salesIndex = -1;
                for (int i = 0; i < sales.length; i++) {
                    if (sales[i][0] == articleNumber) {
                        salesIndex = i;
                        break;
                    }
                }
                if (salesIndex != -1) {
                    sales[salesIndex][1] += quantity;
                    sales[salesIndex][2] += totalPrice;
                } else {
                    int emptyIndex = -1;
                    for (int i = 0; i < sales.length; i++) {
                        if (sales[i][0] == 0) {
                            emptyIndex = i;
                            break;
                        }
                    }
                    if (emptyIndex != -1) {
                        sales[emptyIndex][0] = articleNumber;
                        sales[emptyIndex][1] = quantity;
                        sales[emptyIndex][2] = totalPrice;
                        salesDate[emptyIndex] = new Date();
                    } else {
                        System.out.println("Sales table is full. Cannot register the sale.");
                    }
                }

                // Update article quantity
                articles[articleIndex][1] -= quantity;
                if (articles[articleIndex][1] < 0) {
                    articles[articleIndex][1] = 0;
                }

                System.out.println("Sale registered successfully.");
            } else {
                System.out.println("Insufficient quantity available for sale.");
            }
        } else {
            System.out.println("Invalid quantity. Please enter a value greater than 0.");
        }
    } else {
        System.out.println("Article not found.");
    }
}

    // Prints the sales history stored in the sales and salesDate arrays
    public static void printSales(int[][] sales, Date[] salesDate) {
        System.out.println("\n--- Sales History ---");
        System.out.println("Article No.\tQuantity\tTotal\t\tDate");
        for (int i = 0; i < sales.length; i++) {
            if (sales[i][0] != 0) {
                System.out.printf("%-12d\t%-10d\t%d SEK\t%s%n", sales[i][0], sales[i][1], sales[i][2], salesDate[i]);
            }
        }
    }

    // Sorts the sales and salesDate arrays based on the sales dates and then prints the sorted sales table
    public static void sortedTable(int[][] sales, Date[] salesDate) {
        // Sort sales by date
        for (int i = 0; i < sales.length; i++) {
            for (int j = i + 1; j < sales.length; j++) {
                if (salesDate[j] != null && (salesDate[i] == null || salesDate[i].compareTo(salesDate[j]) > 0)) {
                    int[] tempSales = sales[i];
                    sales[i] = sales[j];
                    sales[j] = tempSales;

                    Date tempDate = salesDate[i];
                    salesDate[i] = salesDate[j];
                    salesDate[j] = tempDate;
                }
            }
        }

        printSales(sales, salesDate);
    }
}
