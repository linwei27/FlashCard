package com.chenke.flashcards.util;

import java.util.Random;


public class RandomSort {
    private static Random random = new Random();

    public static String[] changePosition(String a[]) {
        String [] b = a;
        for (int i = a.length-1; i >= 0 ; i--) {
            exchange(b,random.nextInt(i+1),i);
        }
        return b;
    }

    public static void exchange(String a[], int p1, int p2) {
        String temp = a[p1];
        a[p1] = a[p2];
        a[p2] = temp;
    }
}
