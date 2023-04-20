package org.isoContest;

import java.util.*;


class Mdf3 {

    private static Long COUNT = 0L;
    private static int NB_START_UPS = 0;


    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Scanner sc = new Scanner(System.in);
        NB_START_UPS = Integer.parseInt(sc.nextLine());

        int[][] stThemes = new int[NB_START_UPS][2];

        int startUpIdx = 0;
        while (sc.hasNextLine()) {

            String[] themes = sc.nextLine().split(" ");
            String firstTheme = themes[0];
            String secondTheme = themes[1];

            stThemes[startUpIdx][0] = firstTheme.hashCode();
            stThemes[startUpIdx][1] = secondTheme.hashCode();
            startUpIdx++;
        }

        // 1st attempt
        // processList(stThemes, 1, stThemes[0][0], stThemes[0][0]);
        // processList(stThemes, 1, stThemes[0][1], stThemes[0][1]);

        // 2nd attempt
        NewThread firstThread = new NewThread(NB_START_UPS, stThemes, 1, stThemes[0][0], stThemes[0][0]);
        NewThread secondThread = new NewThread(NB_START_UPS, stThemes, 1, stThemes[0][1], stThemes[0][1]);

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        COUNT = firstThread.getCount() + secondThread.getCount();

        System.out.println("Count: " + COUNT);
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (endTime - startTime));
    }


    private static void processList(int[][] stThemes, int startUpIdx, int initialTheme, int previousTheme) {

        if (startUpIdx < NB_START_UPS) {

            int firstTheme = stThemes[startUpIdx][0];
            int secondTheme = stThemes[startUpIdx][1];

            if (firstTheme != previousTheme) {
                processList(stThemes, startUpIdx + 1, initialTheme, firstTheme);
            }

            if (secondTheme != previousTheme) {
                processList(stThemes, startUpIdx + 1, initialTheme, secondTheme);
            }

        } else {
            if (initialTheme != previousTheme) {
                COUNT++;
            }
        }
    }

}

class NewThread extends Thread {
    private int NB_START_UPS = 0;
    private int[][] stThemes;
    private int startUpIdx;
    private int initialTheme;
    private int previousTheme;
    private Long count = 0L;

    public NewThread(int NB_START_UPS, int[][] stThemes, int startUpIdx, int initialTheme, int previousTheme) {
        this.NB_START_UPS = NB_START_UPS;
        this.stThemes = stThemes;
        this.startUpIdx = startUpIdx;
        this.initialTheme = initialTheme;
        this.previousTheme = previousTheme;
    }

    public void run() {
        processList(stThemes, startUpIdx, initialTheme, previousTheme);
        // System.out.println("Initial theme: " + initialTheme + " -> COUNT: " + count);
    }

    public void processList(int[][] stThemes, int startUpIdx, int initialTheme, int previousTheme) {

        if (startUpIdx < NB_START_UPS) {

            int firstTheme = stThemes[startUpIdx][0];
            int secondTheme = stThemes[startUpIdx][1];

            if (firstTheme != previousTheme) {
                processList(stThemes, startUpIdx + 1, initialTheme, firstTheme);
            }

            if (secondTheme != previousTheme) {
                processList(stThemes, startUpIdx + 1, initialTheme, secondTheme);
            }

        } else {
            if (initialTheme != previousTheme) {
                count++;
            }
        }
    }

    public Long getCount() {
        return count;
    }
}