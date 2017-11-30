package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OverhandApp {

    private static OverhandShuffler overhandShuffler;

   /**
    * Main method
    * @param args
    */
    public static void main(String[] args) {
        System.out.println("You have following choices :");
        System.out.println("1) make-new = > m && pass any arguments like from 0 to 17" );
        System.out.println("2) print = > p" );
        System.out.println("3) shuffle = > s && arguments" );
        System.out.println("4) order = > o && arguments" );
        System.out.println("5) un-broken pairs = > u" );
        System.out.println("6) random-shuffle = > r" );
        System.out.println("7) count-shuffle = > c && argument" );
        System.out.println("8) load = > l" );
        overhandShuffler = new OverhandShuffler(10);
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            handleLine(input.nextLine());
        }
    }

    /**
    * This method processes a user-entered command for manipulating an instance of OverhandShuffler (i.e. a deck of cards).
    */
    public static void handleLine(String input) {
        Scanner scan = new Scanner(input);
        String command = scan.next().toLowerCase();

        try {
            switch (command) {
                case "make-new": case "m":
                    int deckSize = scan.nextInt();
                    System.out.println("MakeNew method called..");
                    overhandShuffler = new OverhandShuffler(deckSize);
                    break;
                case "print": case "p":
                    System.out.println("Print method called..");
                    System.out.println(overhandShuffler);
                    break;
                case "shuffle": case "s":
                    try {
                        System.out.println("Suffle method called..");
                        overhandShuffler.shuffle(getNums(scan));
                    } catch (BlockSizeException ex) {
                        System.err.println(ex.getMessage());
                    }
                    break;
                case "order": case "o":
                    try {
                        System.out.println("Order method called..");
                        System.out.println("Order minimum number :: " + overhandShuffler.order(getNums(scan)));
                    } catch (BlockSizeException ex) {
                        System.err.println(ex.getMessage());
                    }
                    break;
                case "unbroken-pairs": case "u":
                    System.out.println("Unbroken-pairs method called..");
                    overhandShuffler.unbrokenPairs();
                    break;
                case "random-shuffle": case "r":
                    System.out.println("Random-shuffle method called..");
                    overhandShuffler.randomShuffle();
                    break;
                case "count-shuffles": case "c":
                    int n = scan.nextInt();
                    System.out.println("Count-shuffles method called..");
                    overhandShuffler.countShuffles(n);
                    break;
                case "load": case "l":
                    System.out.println("Load method called..");
                    overhandShuffler.load(getNums(scan));
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int[] getNums(Scanner input) {
        List<Integer> numlist = new ArrayList<Integer>();
        while (input.hasNextInt()) {
            numlist.add(input.nextInt());
        }
        int[] nums = new int[numlist.size()];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = numlist.get(i);
        }
        return nums;
    }

}
