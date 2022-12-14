package org.example;

import java.util.*;

public class Main {
    public static final int THREADS_SIZE = 1000;
    public static final int LENGHT = 100;
    public static final String LETTERS = "RLRFR";
    public static final Map<Integer, Integer> SIZE_TO_FREQ = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREADS_SIZE; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS,LENGHT);
                int countR = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (SIZE_TO_FREQ) {
                    if (SIZE_TO_FREQ.containsKey(countR)){
                        SIZE_TO_FREQ.put(countR,SIZE_TO_FREQ.get(countR) + 1);
                    }else {
                        SIZE_TO_FREQ.put(countR,1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> max = SIZE_TO_FREQ
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey() + "(встретилось " + max.getValue() + " раз.)");
        System.out.println("Другие размеры: ");

        SIZE_TO_FREQ.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println("-" + e.getKey() + "(встретилось " + e.getValue() + " раз.)"));

    }
}