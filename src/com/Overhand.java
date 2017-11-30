package com;

public interface Overhand {
    
    public void makeNew(int size);
    
    public int[] getCurrent();
    
    public void shuffle(int[] blocks) throws BlockSizeException;
    
    public int order(int[] blocks) throws BlockSizeException;
    
    public int unbrokenParis();
}
