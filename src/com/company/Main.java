package com.company;

import java.util.Random;
import java.util.Scanner;


public class Main {
    private static char[][] field;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static int fieldSize;
    private static int winQuantity = 4;
    private static int lastX, lastY;
    private static int chekWinForAI = 3;
    private static int a = 0;


    public static void main(String[] args) {
        while (true) {
            initField();
            printField();

            while (true) {

                humanTurn();
                printField();
                if (checkGame(DOT_HUMAN, "Human wins!!!")) break;
                aiTurn();
                printField();
                if (checkGame(DOT_AI, "AI win!!!")) break;
            }
            System.out.println("Wanna play again?");
            if (!SCANNER.next().equals("y")) {
                SCANNER.close();
                break;
            }
        }

    }

    private static void humanTurn() {
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода Х и У от 1 до 3 через пробел ->");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
        lastX = x; // записываем последний ход в переменную
        lastY = y;


    }


//    private static void aiBot(){
//        int x = 0;
//        int y = 0;
//        if(checkWinH(DOT_HUMAN)){
//            lastX = x;
//            lastY = y +1;
//            if (!isCellEmpty(x,y)){
//                aiTurn();
//            }
//            field[y][x] = DOT_AI;
//
//        }
//    }

    private static void aiTurn() {//перекрытие фишек противника
        int x;
        int y ;
        if (a >= 2) {
            if (checkWinHV(DOT_HUMAN) && checkWinHD(DOT_HUMAN)) {
                x = lastX;
                y = lastY + 1;
                field[y][x] = DOT_AI;

            }

            if (checkWinHH(DOT_HUMAN)&& checkWinHD(DOT_HUMAN)){
                    x = lastX + 1;
                    y = lastY;
                    field[y][x] = DOT_AI;
                }

            if (checkWinHD(DOT_HUMAN)){
                x = lastX + 1;
                y = lastY + 1;
                field[y][x] = DOT_AI;

            }
            x = 0;
            y = 0;
            if (!isCellEmpty(x, y)) {
                x = RANDOM.nextInt(fieldSize);
                y = RANDOM.nextInt(fieldSize);

            }


        }


        if (a < 2) {
            do {
                x = RANDOM.nextInt(fieldSize);
                y = RANDOM.nextInt(fieldSize);
            } while (!isCellEmpty(x, y));

            field[y][x] = DOT_AI;


        }
        a++;
    }



    private static boolean checkGame(char dot, String s) {

        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Draw!!!");
            return true;
        }
        return false;
    }

    private static boolean checkWin(char c) {
        boolean fA1 = true;
        boolean fA2 = true;
        boolean fA3 = true;
        boolean fA4 = true;
        boolean fA5 = true;
        boolean fA6 = true;
        boolean rA1 = true;
        boolean rA2 = true;


        for (int x = 0; x < field.length; x++) {// проверка вертикали
            int a = 0;
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == c) a++;
                if (y + 1 < field[x].length && field[x][y + 1] != c && a != winQuantity)
                    a = 0;
            }
            if (a >= winQuantity) return true;
            break;

        }

        for (int x = 0; x < field.length; x++) {// проверка горизонтали
            int b = 0;
            for (int y = 0; y < field[x].length; y++) {
                if (field[y][x] == c) b++;
                if (y + 1 < field[x].length && field[y + 1][x] != c && b != winQuantity)
                    b = 0;
            }
            if (b >= winQuantity) return true;
            break;

        }
        for (int x = 0; x < winQuantity; x++) {
            //хотела сделать как проверку горизонтали и вертикали, но запуталась
            //с маленькими диагоналями (где 4 клетки в ряд), поэтому осталвяю так

            if (field[x][x] != c) fA1 = false;//проверка оычной диагонали
            if (field[x][field.length - x - 1] != c) fA2 = false;//проверка оычной диагонали
            if (field[field.length - x - 1][x] != c) rA1 = false;//проверка оычной диагонали в другую сорону
            if (field[field.length - x - 1][field.length - x - 1] != c)
                rA2 = false;//проверка оычной диагонали в другую сторону
            if (field[x + 1][x] != c) fA3 = false;//проверка диагоналей и 4-х клеток
            if (field[x][x + 1] != c) fA4 = false;
            if (field[x][field.length - x - 2] != c) fA5 = false;
            if (field[x + 1][field.length - x - 1] != c) fA6 = false;
        }
        if (fA1 || fA2 || fA3 || fA4 || rA1 || rA2 || fA5 || fA6) return true;
        return false;
    }


    //
    private static boolean checkWinHD(char c) {
        boolean fA1 = true;
        boolean fA2 = true;
        boolean fA3 = true;
        boolean fA4 = true;
        boolean fA5 = true;
        boolean fA6 = true;
        boolean rA1 = true;
        boolean rA2 = true;

        for (int x = 0; x < chekWinForAI; x++) {

            if (field[x][x] != c) fA1 = false;//проверка оычной диагонали
            if (field[x][field.length - x - 1] != c) fA2 = false;//проверка оычной диагонали
            if (field[field.length - x - 1][x] != c) rA1 = false;//проверка оычной диагонали в другую сорону
            if (field[field.length - x - 1][field.length - x - 1] != c)
                rA2 = false;//проверка оычной диагонали в другую сторону
            if (field[x + 1][x] != c) fA3 = false;//проверка диагоналей и 4-х клеток
            if (field[x][x + 1] != c) fA4 = false;
            if (field[x][field.length - x - 2] != c) fA5 = false;
            if (field[x + 1][field.length - x - 1] != c) fA6 = false;
        }
        if (fA1 || fA2 || fA3 || fA4 || rA1 || rA2 || fA5 || fA6) return true;
        return false;
    }

    private static boolean checkWinHV(char c) {
        for (int x = 0; x < field.length; x++) {
            int a = 0;
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == c) a++;
                if (y + 1 < field[x].length && field[x][y + 1] != c && a != chekWinForAI)
                    a = 0;
            }
            if (a >= chekWinForAI) return true;
            break;

        }
        return false;
    }




    private static boolean checkWinHH(char c) {
        for (int x = 0; x < field.length; x++) {
            int b = 0;
            for (int y = 0; y < field[x].length; y++) {
                if (field[y][x] == c) b++;
                if (y + 1 < field[x].length && field[y + 1][x] != c && b != chekWinForAI)
                    b = 0;
            }
            if (b >= chekWinForAI) return true;
            break;

        }
        return false;
    }


//

        private static boolean checkDraw() {
            for (int y = 0; y < fieldSize; y++) {
                for (int x = 0; x < fieldSize; x++) {
                    if (isCellEmpty(x, y)) return false;
                }
            }
            return true;
        }

        private static void initField() {
            fieldSize = 5;
            field = new char[fieldSize][fieldSize];
            for (int y = 0; y < fieldSize; y++) {
                for (int x = 0; x < fieldSize; x++) {
                    field[y][x] = DOT_EMPTY;
                }
            }
        }

        private static void printField() {
            System.out.print("+");
            for (int i = 0; i < fieldSize * 2 + 1; i++)
                System.out.print((i % 2 == 0) ? "-" : i / 2 + 1);
            System.out.println();

            for (int i = 0; i < fieldSize; i++) {
                System.out.print(i + 1 + "|");
                for (int j = 0; j < fieldSize; j++)
                    System.out.print(field[i][j] + "|");
                System.out.println();
            }

            for (int i = 0; i <= fieldSize * 2 + 1; i++)
                System.out.print("-");
            System.out.println();
        }

        private static boolean isCellEmpty(int x, int y) {
            return field[y][x] == DOT_EMPTY;
        }

        private static boolean isCellValid(int x, int y) {
            return x >= 0 && x < fieldSize && y >= 0 && y < fieldSize;
        }



        // write your code here

}
