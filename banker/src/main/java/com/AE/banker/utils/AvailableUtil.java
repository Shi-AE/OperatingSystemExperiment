package com.AE.banker.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AvailableUtil {
    private AvailableUtil() {
    }

    /**
     * 生成随机资源
     */
    public static List<Integer> createAvailableByRandom(int d) {
        Random random = new Random();
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            available.add(random.nextInt(5, 20));
        }
        return available;
    }

    /**
     * 手动输入资源
     */
    public static List<Integer> createAvailableByScanner(int d) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            available.add(scanner.nextInt());
        }
        return available;
    }
}
