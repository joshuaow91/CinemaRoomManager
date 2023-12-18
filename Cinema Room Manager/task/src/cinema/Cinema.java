package cinema;

import java.util.Scanner;

public class Cinema {

    private static int currentIncome = 0;
    private static int totalTicketsSold = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] cinema = initializeCinema(scanner);
        runCinemaMenu(cinema, scanner);
    }

    private static char[][] initializeCinema(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        char[][] cinema = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinema[i][j] = 'S';
            }
        }
        return cinema;
    }

    private static void runCinemaMenu(char[][] cinema, Scanner scanner) {
        int totalIncome = calculateTotalIncome(cinema.length, cinema[0].length);

        while (true) {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printCinema(cinema);
                    break;
                case 2:
                    processTicketPurchase(cinema, scanner);
                    break;
                case 3:
                    showStatistics(cinema, totalIncome);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Unknown option. Please try again.");
            }
        }
    }

    private static void printCinema(char[][] cinema) {
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 1; i <= cinema[0].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static void processTicketPurchase(char[][] cinema, Scanner scanner) {
        boolean successfulPurchase = false;
        while (!successfulPurchase) {
            int[] seat = chooseSeat(cinema.length, cinema[0].length, scanner);
            if (isSeatAvailable(cinema, seat[0], seat[1])) {
                int ticketPrice = calculateTicketPrice(cinema.length, cinema[0].length, seat[0]);
                System.out.println("\nTicket price: $" + ticketPrice);
                updateCinemaSeating(cinema, seat[0], seat[1]);
                currentIncome += ticketPrice;
                totalTicketsSold++;
                successfulPurchase = true;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        }
    }

    private static int[] chooseSeat(int rows, int seatsPerRow, Scanner scanner) {
        while (true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();

            if (row < 1 || row > rows || seat < 1 || seat > seatsPerRow) {
                System.out.println("Wrong input!");
            } else {
                return new int[]{row, seat};
            }
        }
    }

    private static boolean isSeatAvailable(char[][] cinema, int row, int seat) {
        return cinema[row - 1][seat - 1] == 'S';
    }

    private static void updateCinemaSeating(char[][] cinema, int row, int seat) {
        cinema[row - 1][seat - 1] = 'B';
    }

    private static int calculateTicketPrice(int rows, int seatsPerRow, int chosenRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return 10;
        } else {
            int frontHalfRows = rows / 2;
            return chosenRow <= frontHalfRows ? 10 : 8;
        }
    }

    private static void showStatistics(char[][] cinema, int totalIncome) {
        System.out.println("Number of purchased tickets: " + totalTicketsSold);
        double percentage = (totalTicketsSold * 100.0) / (cinema.length * cinema[0].length);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    private static int calculateTotalIncome(int rows, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        } else {
            int frontHalfRows = rows / 2;
            int backHalfRows = rows - frontHalfRows;
            return frontHalfRows * seatsPerRow * 10 + backHalfRows * seatsPerRow * 8;
        }
    }
}
