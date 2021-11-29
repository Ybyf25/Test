package com.company;

import java.io.File;
import java.io.*;
import java.util.Scanner;


public class Main {
    static int min = 2;
    static String errorText = "Ошибка ввода. Введите корректное число.";
    static Scanner scan = new Scanner(System.in);
    public static void printAim() {
        System.out.println("Эта программа расставляет элементы каждой строки квадратной матрицы в порядке убывания.");
    }
    public static boolean isInLimit(short num) {
        boolean isWithinLimit;
        if (num < min) {
            System.out.println(errorText);
            isWithinLimit = false;
        }
        else {
            isWithinLimit = true;
        }
        return isWithinLimit;
    }
    public static short inputParameter(String text) {
        boolean isCorrect;
        short number = 2;
        do {
            isCorrect = true;
            System.out.print(text);
            try {
                number = Short.parseShort(scan.nextLine());
            }
            catch (Exception eIn) {
                System.out.println(errorText);
                isCorrect = false;
            }
            if (isCorrect) {
                isCorrect = isInLimit(number);
            }
        }
        while (!isCorrect);
        return number;
    }
    public static String getFileName(String fileText) {
        String path;
        System.out.println(fileText);
        boolean isNotCorrect = true;
        do {
            System.out.print("Путь: ");
            path = scan.nextLine();
            File myFile = new File(path);
            try {
                if (myFile.exists()) {
                    isNotCorrect = false;
                }
                else {
                    System.out.println("Файл не найден. Попробуйте снова.");
                }
            }
            catch(Exception exF) {
                System.out.println("Файл не найден. Попробуйте снова.");
                throw exF;
            }
        }
        while (isNotCorrect);
        return path;
    }
    public static int readElementFromConsole(short i, short j) {
        boolean isNotCorrect;
        int element = 0;
        do {
            System.out.print("Введите " + (j + 1) + "-й элемент " + (i + 1) + "-й строки: ");
            isNotCorrect = false;
            try {
                element = Integer.parseInt(scan.nextLine());
            }
            catch (Exception exIn) {
                System.out.println(errorText);
                isNotCorrect = true;
            }
        }
        while (isNotCorrect);
        return element;
    }
    public static int[][] readMatrixFromConsole(short parameter) {
        int[][] consoleMatrixIn = new int [parameter][parameter];
        for (short i = 0; i < parameter; i++) {
            for (short j = 0; j < parameter; j++) {
                consoleMatrixIn[i][j] = readElementFromConsole(i, j);
            }
        }
        return consoleMatrixIn;
    }
    public static int readElementFromFile(Scanner inF, short i, short j) {
        int element = 0;
        boolean isNotCorrect;
        do {
            isNotCorrect = false;
            try {
                element = inF.nextInt();
            }
            catch (Exception exIn) {
                if (inF.hasNext()) {
                    isNotCorrect = true;
                    inF.next();
                }
                else {
                    System.out.println("В файле недостаточно чисел. Выполните ввод с клавиатуры.");
                    element = readElementFromConsole(i, j);
                }
            }
        }
        while (isNotCorrect);
        return element;
    }
    public static int[][] readMatrixFromFile(short parameter) {
        int[][] fileMatrixIn = new int [parameter][parameter];
        System.out.println("Убедитесь, что в выбранном файле достаточно чисел и они отделены друг от друга пробелом или переходом на новую строку.");
        String inputFileText = "Введите путь и название файла, из которого хотите выполнить ввод.";
        String inputFilePath = getFileName(inputFileText);
        File myFile = new File(inputFilePath);
        try {
            Scanner inFile = new Scanner(myFile);
            for (short i = 0; i < parameter; i++) {
                for (short j = 0; j < parameter; j++) {
                    if (!inFile.hasNext()) {
                        System.out.println("В файле недостаточно чисел. Выполните ввод с клавиатуры.");
                        fileMatrixIn[i][j] = readElementFromConsole(i, j);
                    }
                    else {
                        fileMatrixIn[i][j] = readElementFromFile(inFile, i, j);
                    }
                }
            }
            inFile.close();
        }
        catch (Exception exF) {
            System.out.println("Ошибка данных. Выберите другой файл.");
            fileMatrixIn = readMatrixFromFile(parameter);
        }
        return fileMatrixIn;
    }
    public static int[][] readMatrix(short parameter) throws IOException {
        int[][] matrixIn = new int [parameter][parameter];
        boolean isUndeclared = true;
        while (isUndeclared) {
            isUndeclared = false;
            System.out.print("Если хотите считать элементы из файла, введите 'F'; если хотите набрать вручную, введите 'C': ");
            char fromFile = (char) System.in.read();
            scan.nextLine();
            if ((fromFile == 'F') || (fromFile == 'f')) {
                matrixIn = readMatrixFromFile(parameter);
            }
            else if ((fromFile == 'C') || (fromFile == 'c')) {
                matrixIn = readMatrixFromConsole(parameter);
            }
            else {
                System.out.println("Ошибка! Введите нужную букву.");
                isUndeclared = true;
            }
        }
        return matrixIn;
    }
    public static int[] arrangeRowElementsDecreasing(short parameter, int[] rowToDecrease) {
        boolean isNotDecreasing;
        short maxElementI = 0;
        do {
            short newMaxElementI = maxElementI;
            for (short i = maxElementI; i < parameter; i++) {
                if (rowToDecrease[i] > rowToDecrease[newMaxElementI]) {
                    newMaxElementI = i;
                }
            }
            if (newMaxElementI > maxElementI) {
                rowToDecrease[newMaxElementI] = rowToDecrease[newMaxElementI] + rowToDecrease[maxElementI];
                rowToDecrease[maxElementI] = rowToDecrease[newMaxElementI] - rowToDecrease[maxElementI];
                rowToDecrease[newMaxElementI] = rowToDecrease[newMaxElementI] - rowToDecrease[maxElementI];
            }
            maxElementI++;
            isNotDecreasing = false;
            for (short i = 1; i < parameter; i++) {
                if (rowToDecrease[i] > rowToDecrease[i - 1]) {
                    isNotDecreasing = true;
                }
            }
        }
        while (isNotDecreasing);
        return rowToDecrease;
    }
    public static void printResultsInConsole(int[][] consoleMatrixOut, short parameter) {
        System.out.println("Введенная матрица:");
        for (short i = 0; i < parameter; i++) {
            for (short j = 0; j < parameter; j++) {
                System.out.print(consoleMatrixOut[i][j]);
                if ((j + 1) < parameter) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("Упорядоченная матрица:");
        int[][] consoleArrangedMatrixOut = new int [parameter][parameter];
        for (short i = 0; i < parameter; i++) {
            consoleArrangedMatrixOut[i] = arrangeRowElementsDecreasing(parameter, consoleMatrixOut[i]);
            for (short j = 0; j < parameter; j++) {
                System.out.print(consoleArrangedMatrixOut[i][j]);
                if ((j + 1) < parameter) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    public static void printResultsToFile(int[][] fileMatrixOut, short parameter) {
        String outputFileText = "Введите путь и название файла, в который хотите вывести результат.";
        String outputFilePath = getFileName(outputFileText);
        File myNewFile = new File(outputFilePath);
        try {
            FileWriter outF = new FileWriter(myNewFile);
            outF.write("Введенная матрица:\r\n");
            for (short i = 0; i < parameter; i++) {
                for (short j = 0; j < parameter; j++) {
                    outF.append(String.valueOf(fileMatrixOut[i][j]));
                    if ((j + 1) < parameter) {
                        outF.append(" ");
                    }
                }
                outF.append("\r\n");
            }
            outF.write("Упорядоченная матрица:");
            int[][] fileArrangedMatrixOut = new int [parameter][parameter];
            for (short i = 0; i < parameter; i++) {
                outF.append("\r\n");
                fileArrangedMatrixOut[i] = arrangeRowElementsDecreasing(parameter, fileMatrixOut[i]);
                for (short j = 0; j < parameter; j++) {
                    outF.append(String.valueOf(fileArrangedMatrixOut[i][j]));
                    if ((j + 1) < parameter) {
                        outF.append(" ");
                    }
                }
            }
            outF.close();
            System.out.print("Полученные данные были успешно записаны в файл.");
        }
        catch (IOException e) {
            System.out.println("Что-то пошло не так! Придется вывести в консоль.");
            printResultsInConsole(fileMatrixOut, parameter);
        }
    }
    public static void printResults(int[][] matrixOut, short parameter) throws IOException {
        boolean isUndeclared;
        do {
            isUndeclared = false;
            System.out.print("Если хотите вывести результат в файл, введите 'F'; если на экран консоли, введите 'C': ");
            char  toFile = (char) System.in.read();
            scan.nextLine();
            if ((toFile == 'F') || (toFile == 'f')) {
                printResultsToFile(matrixOut, parameter);
            }
            else if ((toFile == 'C') || (toFile == 'c')) {
                printResultsInConsole(matrixOut, parameter);
            }
            else {
                System.out.println("Ошибка! Введите нужную букву.");
                isUndeclared = true;
            }
        }
        while (isUndeclared);
    }
    public static void doMain() throws IOException {
        printAim();
        String parameterText = "Введите порядок матрицы: ";
        short parameter = inputParameter(parameterText);
        int[][] matrix = readMatrix(parameter);
        printResults(matrix, parameter);
        scan.close();
    }
    public static void main(String[] args) throws IOException {
        doMain();
    }
}