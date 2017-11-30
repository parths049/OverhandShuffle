package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OverhandShuffler implements Overhand {
    
    /**
     * stores the current state of the deck
     */
    private int[] deck;
    
    @Override
    public void makeNew(int size) {
        deck = new int[size];
        for (int i = 0; i < size; i++) {
            deck[i] = i;
        }
    }
    
    /**
     * Returns the current state of the deck.
     */
    @Override
    public int[] getCurrent() {
        return Arrays.copyOf(deck, deck.length);
    }
    
    /**
     * Shuffles the current state of the deck according to the array of block sizes given.
     * Should throw BlockSizeException if blocks contains any negative integers or the sum of its entries are not equal to the total size of the deck.
     */
    @Override
    public void shuffle(int[] blocks) throws BlockSizeException {
        handleBlockException(blocks);
        int[] result = new int[deck.length];
        int i = 0;
        for (int block : blocks) {
            for (int j = block - 1 + i, k = deck.length - 1 - i; j >= i; --j, --k) {
                result[k] = deck[j];
            }
            i += block;
        }
        deck = result;
        System.out.println("Suffle :: " + Arrays.toString(deck));
    }

   /**
    * Returns the minimum number of times that the deck could be shuffled (from its initial state) using the same set of block sizes each time (as given by the argument) in order to return to return the deck to the initial state.
    * Should throw a BlockSizeException if blocks contain any negative integers, or the sum of its entries are not equal to the total size of the deck.
    */
    @Override
    public int order(int[] blocks) throws BlockSizeException {
        handleBlockException(blocks);
        OverhandShuffler original = new OverhandShuffler(this.deck.length);
        original.load(this.deck);
        int i = 1;
        boolean isMatch = true;
        while (isMatch) {
            shuffle(blocks);
            if (this.toString().equals(original.toString())) {
                isMatch = false;
                return i;
            }
            i++;
        }
        return i;
    }

    @Override
    public int unbrokenParis() {
        int unbrokenPairs = 0;
        for (int i = 1; i < deck.length; i++) {
            if (deck[i - 1] + 1 == deck[i]) {
                ++unbrokenPairs;
            }
        }
        return unbrokenPairs;
    }

    public void load(int[] cards) {
        deck = Arrays.copyOf(cards, cards.length);
        System.out.println("Load :: " + Arrays.toString(deck));
    }
    
    public OverhandShuffler(int length) {
        makeNew(length);
    }

    /**
     * Shuffles the deck by splitting it into randomly-sized blocks.
     * For a 52-card deck, it splits the deck between any two adjacent cards (any of the 51 adjacent cards) with a probability of 10%.4
     * It then calls the shuffle() method with the randomly chosen blocks array.
     */
     public void randomShuffle() {
         final double breakProbability = 0.1;
         List<Integer> blocksList = new ArrayList<Integer>();
         Random random = new Random();
         int blockSize = 0;
         for (int i = 0; i < deck.length; ++i) {
             ++blockSize;
             if (random.nextDouble() < breakProbability) {
                 blocksList.add(blockSize);
                 blockSize = 0;
             }
         }
         if (blockSize != 0) {
             blocksList.add(blockSize);
             blockSize = 0;
         }

         int[] blocks = new int[blocksList.size()];
         for (int i = 0; i < blocks.length; ++i) {
             blocks[i] = blocksList.get(i);
         }
         try {
             shuffle(blocks);
         } catch (BlockSizeException blockException) {
             blockException.printStackTrace();
             return;
         }
     }
     
     /**
      * Count shuffles
      * @param unbrokenPairs
      * @return
      */
     public int countShuffles(int unbrokenPairs) {
         if (unbrokenPairs <= 0) {
             throw new IllegalArgumentException("It is logically impossible to reduce unbrokenPairs to or below 0");
         }
         int shuffles = 0;
         while (this.unbrokenPairs() >= unbrokenPairs) {
             randomShuffle();
             shuffles++;
         }
         System.out.println("Count-shuffles :: " + shuffles);
         return shuffles;
     }
     
     /**
      * Returns the number of pairs of cards which were consecutive in the original deck.
      */
      public int unbrokenPairs() {
          int unbrokenPairs = 0;
          for (int i = 1; i < deck.length; i++) {
              if (deck[i - 1] + 1 == deck[i]) {
                  ++unbrokenPairs;
              }
          }
          System.out.println("Unbroken-pairs :: " + unbrokenPairs);
          return unbrokenPairs;
      }
      
      /**
       * Handle Block Exception
       * @param blocks
       * @throws BlockSizeException
       */
      private void handleBlockException(int[] blocks) throws BlockSizeException {
          int sum = 0;
          for (int block : blocks) {
              sum += block;
              if (block < 0) {
                  throw new BlockSizeException("A block value is negative");
              }
          }
          if (sum != deck.length) {
              throw new BlockSizeException("The sum of the blocks is not equal to the deck size. Sum: " + sum + ", deck size: " + deck.length);
          }
      }
      
    @Override
    public String toString() {
        String s = "";
        for (int card : deck) {
            s += card + " ";
        }
        return s;
    }
    
}
