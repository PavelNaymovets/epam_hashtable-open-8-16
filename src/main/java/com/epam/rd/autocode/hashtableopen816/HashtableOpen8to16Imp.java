package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;
import java.util.Hashtable;

public class HashtableOpen8to16Imp implements HashtableOpen8to16 {
    private final int MAX_SIZE = 16;
    private int capacity;
    private int size;
    Integer [] arrKeys;
    Object [] arrValues;

    public HashtableOpen8to16Imp() {
        this.arrKeys = new Integer[8];
        this.arrValues = new Object[8];
        this.capacity = 8;
        this.size = 0;
    }

    private int hashCode(int key, int uniqueCapacity){
        if(key < 0) key = -key;
        return key % uniqueCapacity;
    }

    @Override
    public void insert(int key, Object value) {
        if (search(key) != null) return;
        if (size >= MAX_SIZE) throw new IllegalStateException();

        if (capacity == size) {
            capacity = capacity*2;
            resize(capacity/2, capacity);
        }

        int hashCode = hashCode(key, capacity);
        while (arrKeys[hashCode] != null) {
            hashCode++;
            if(hashCode == capacity) hashCode = 0;
        }

        arrKeys[hashCode] = key;
        arrValues[hashCode] = value;

        size++;
    }

    private void resize(int capacity, int newCapacity) {
        Integer[] newArrKeys = new Integer[newCapacity];
        Object[] newArrValues = new Object[newCapacity];

        Integer[] tempK = arrKeys;
        arrKeys = newArrKeys;
        newArrKeys = tempK;

        Object[] tempV = arrValues;
        arrValues = newArrValues;
        newArrValues = tempV;

        size = 0;

        for (int i = 0; i < capacity; i++) {
            if(newArrKeys[i] != null) insert(newArrKeys[i], newArrValues[i]);
        }
    }


    @Override
    public Object search(int key) {
        if (size == 0) return null;
        int index = hashCode(key, capacity);
        int indexTemp = index;
        while (true) {
            if(arrKeys[indexTemp] != null && arrKeys[indexTemp] == key){
                break;
            }
            indexTemp++;
            if (indexTemp == capacity) indexTemp = 0;
            if (indexTemp == index) return null;

        }
        return arrValues[indexTemp];
    }

    @Override
    public void remove(int key) {
        if (size == 0) return;
        int index = hashCode(key, capacity);
        int indexTemp = index;
        while (true) {
            if(arrKeys[indexTemp] != null && arrKeys[indexTemp] == key){
                break;
            }
            indexTemp++;
            if (indexTemp == capacity) indexTemp = 0;
            if (indexTemp == index) return;

        }
        arrKeys[indexTemp] = null;
        arrValues [indexTemp] = null;

        size--;

        if (size * 4 <= capacity && capacity != 2) {
            capacity = capacity/2;
            resize(capacity * 2, capacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int[] keys() {
        int [] array = new int[capacity];
        //array = Arrays.copyOf(arrKeys,0);

        for (int i = 0; i < capacity; i++) {
            array[i] = arrKeys[i] == null ? 0 : arrKeys[i];
        }

        return array;
    }
}
