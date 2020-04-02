import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static chart.Chart.show;
import static java.lang.Math.*;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String otdelitel = ANSI_RED + "===================================================================" + ANSI_RESET;

    public static void main(String[] args) throws IOException {
        show(); // вывод графика
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_CYAN + "Program has started" + ANSI_RESET);
        System.out.println(otdelitel);
        System.out.println(ANSI_CYAN + "Выберите режим:");
        System.out.println("1 - клавиатура; 2 - файл" + ANSI_RESET);
        int v = scanner.nextInt();
        double a, b, e;
        if (v == 1){
            System.out.println(ANSI_CYAN + "Введите последовательно с каждой новой строки a, b, e" + ANSI_RESET);
            a = scanner.nextDouble();
            b = scanner.nextDouble();
            e = scanner.nextDouble();
            System.out.println(otdelitel);
            HalfDivision(a,b,e,v);
            Newton(a,b,e,v);
            SimpleIteration(a,b,e,v);
        } else if (v == 2){
            BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/iter.txt"));
            String s = reader.readLine();
            double [] arr = Arrays.stream(s.split(" ")).mapToDouble(Double::parseDouble).toArray();
            a = arr[0];
            b = arr[1];
            e = arr[2];
            HalfDivision(a,b,e,v);
            Newton(a,b,e,v);
            SimpleIteration(a,b,e,v);
        } else System.out.println(ANSI_RED + "Ошибка выбора варианта использования" + ANSI_RESET);

    }

    public static void HalfDivision(double a, double b, double e, int output){

        if (f(a) * f(b) < 0){
        int n = 0;
        double x = 0;
        while (abs(a - b) > e) {
            x = (a + b) / 2;
            if (f(a) * f(x) > 0) {
                a = x;
            } else {
                b = x;
            }
            n += 1;
        }
        x = (a + b)/2;
        if (output == 1){
            System.out.println("Метод половинного деления");
            System.out.println("Кол-во итераций = " + n);
            System.out.println("Найденный корень уравнения x = " + x);
            System.out.println("Значение функции в корне f(x) = " + f(x));
            System.out.println(otdelitel);
            } else if (output == 2){
                try(FileWriter writer = new FileWriter("./src/main/resources/result.txt", true)){
                    writer.write("Метод половинного деления");
                    writer.append('\n');
                    writer.write("Кол-во итераций = " + n);
                    writer.append('\n');
                    writer.write("Найденный корень уравнения x = " + x);
                    writer.append('\n');
                    writer.write("Значение функции в корне f(x) = " + f(x));
                    writer.append('\n');
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else
            System.out.println("На данном пром-ке нет корней");
    }

    public static void Newton(double a, double b, double e, int output){
        double x0,xn;
        int i = 0;

        if (f(a) * f(b) < 0){ // если знаки функции на краях отрезка одинаковые, то здесь нет корня
            if (f(a) * df2(a) > 0){ // для выбора начальной точки проверяем f(x0)*d2f(x0)>0?
                x0 = a;
            } else x0 = b;
            xn = x0 - f(x0) / df1(x0); //считаем первое приблежение
            while (abs(x0-xn) > e) {//пока не достигнем необходимой
            x0 = xn;
            xn = x0 - f(x0)/ df1(x0);
            i++;
            }
            if (output == 1){
                System.out.println("Метод Ньютона");
                System.out.println("Кол-во итераций = " + i);
                System.out.println("Найденный корень уравнения x = " + xn);
                System.out.println("Значение функции в корне f(x) = " + f(x0));
                System.out.println(otdelitel);
            } else if (output == 2){
                try(FileWriter writer = new FileWriter("./src/main/resources/result.txt", true)){
                    writer.write("=============================================================================");
                    writer.append('\n');
                    writer.write("Метод Ньютона");
                    writer.append('\n');
                    writer.write("Кол-во итераций = " + i);
                    writer.append('\n');
                    writer.write("Найденный корень уравнения x = " + xn);
                    writer.append('\n');
                    writer.write("Значение функции в корне f(x) = " + f(xn));
                    writer.append('\n');
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else
        System.out.println("На данном пром-ке нет корней");
    }

    public static void SimpleIteration(double a, double b, double e, int output){
        if (f(a) * f(b) < 0) {
            double[] arr;
            List<Double> list = new ArrayList<>();
            list.add((a + b) / 2);
            int i = 0;
            if (abs(dfif1(a)) < 1 && abs(dfif1(b)) < 1) {
                while (true) {
                    list.add(fif1(list.get(i)));
                    i++;
                    if (abs(list.get(i) - list.get(i - 1)) <= e) {
                        break;
                    }
                }
            } else if (abs(dfif2(a)) < 1 && abs(dfif2(b)) < 1) {
                while (true) {
                    list.add(fif2(list.get(i)));
                    i++;
                    if (abs(list.get(i) - list.get(i - 1)) <= e) {
                        break;
                    }
                }
            } else if (abs(dfif3(a)) < 1 && abs(dfif3(b)) < 1) {
                while (true) {
                    list.add(fif3(list.get(i)));
                    i++;
                    if (abs(list.get(i) - list.get(i - 1)) <= e) {
                        break;
                    }
                }
            } else {
                while (true) {
                    list.add(fifa(list.get(i), a, b));
                    i++;
                    if (abs(list.get(i) - list.get(i - 1)) <= e) {
                        break;
                    }
                }
            }
            if (output == 1) {
                System.out.println("Метод простой итерации");
                System.out.println("Кол-во итераций = " + i);
                System.out.println("Найденный корень уравнения x = " + list.get(i));
                System.out.println("Значение функции в корне f(x) = " + f(list.get(i)));
                System.out.println(otdelitel);
            } else if (output == 2){
                try(FileWriter writer = new FileWriter("./src/main/resources/result.txt", true)){
                    writer.write("=============================================================================");
                    writer.append('\n');
                    writer.write("Метод Простой итерации");
                    writer.append('\n');
                    writer.write("Кол-во итераций = " + i);
                    writer.append('\n');
                    writer.write("Найденный корень уравнения x = " + list.get(i));
                    writer.append('\n');
                    writer.write("Значение функции в корне f(x) = " + f(list.get(i)));
                    writer.append('\n');
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else System.out.println("На данном пром-ке нет корней");
    }

    //функции
    public static double f(double x){
        return pow(x, 3) + 4.81 * pow(x, 2) - 17.37 * x + 5.38;
    }

    public static double df1(double x){
        return 3 * pow(x, 2) + 9.62 * x - 17.37;
    }

    public static double df2(double x){
        return 6 * x + 9.62;
    }

    //функции x = fi(x)
    public static double fif1(double x){
        return (pow(x,3) + 4.81 * pow(x, 2) + 5.38)/17.37;
    }

    public static double fif2(double x){
        return sqrt((pow(x,3) - 17.37 * x + 5.38)/(-4.81));
    }

    public static double fif3(double x){
        return pow(-4.81 * pow(x,2) + 17.37 * x - 5.38, 1/3);
    }

    //x + l * f(x)
    public static double fifa(double x, double a, double b){
        double l = -1 / max(df1(a), df1(b));
        return x + l * f(x);
    }

    public static double dfif1(double x) { return 0.1727 * pow(x,2) + 0.5538 * x; }

    public static double dfif2(double x) { return (-0.3119 * pow(x,2) + 1.8056)/sqrt((pow(x,3) - 17.37 * x + 5.38)/(-4.81)); }

    public static double dfif3(double x) { return (-3.2067 * x + 5.79)/pow((-4.81 * pow(x,2) + 17.37 * x - 5.38),2/3); }
}
