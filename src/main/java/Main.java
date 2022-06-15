import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        new Thread(() -> client(host, port)).start();
        new Thread(() -> server(port)).start();
    }

    public static void client(String host, int port) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Введите число n");
            int n = Integer.parseInt(scanner.nextLine());
            out.println(n);
            String fibonacciNumber = in.readLine();
            System.out.println("Число под номером " + n + " в ряде Фибоначчи: " + fibonacciNumber);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Введите число!");
            numberFormatException.printStackTrace();
        }
    }

    public static void server(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            final int n = Integer.parseInt(in.readLine());
            out.println(findNInFibonacciSeries(n));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int findNInFibonacciSeries(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return findNInFibonacciSeries(n - 1) + findNInFibonacciSeries(n - 2);
        }
    }
}

