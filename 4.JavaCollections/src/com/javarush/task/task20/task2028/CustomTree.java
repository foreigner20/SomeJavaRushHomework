package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    private Queue<Entry> forAdding = new LinkedTransferQueue<>();
    private ArrayList<Entry> forSizeAndParents = new ArrayList<>();
    int countOfChildren = 0;
    public CustomTree() {
        this.root = new Entry<>("0");
    }

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) {
            throw new UnsupportedOperationException();
        } else {
            Entry forRemove = null;
            for (int i = 0; i < forSizeAndParents.size(); i++) {
                if (forSizeAndParents.get(i).elementName.equals(o)) {
                    forRemove = forSizeAndParents.get(i);
                }
            }
            Entry whatWeNeedToDelete = forRemove;
            removeHelper(forRemove);
            while (!forRemove.elementName.equals(whatWeNeedToDelete.parent.elementName)) {
                removeHelper(forRemove);
                if (forRemove.leftChild == null && forRemove.rightChild == null) {
                    break;
                }
            }
            removeHelper(forRemove);
            if (!forAdding.contains(forRemove.parent))
                forAdding.add(forRemove.parent);
        }
        return true;
    }

    public void removeHelper(Entry s) {
        Entry forRemove = s;
        if (forRemove.leftChild == null && forRemove.rightChild == null) {
            if (forRemove == forRemove.parent.leftChild) {
                forRemove.parent.leftChild = null;
                forRemove.parent.availableToAddLeftChildren = true;
                forSizeAndParents.remove(forRemove);
                if (forAdding.contains(forRemove)) {
                    forAdding.remove(forRemove);
                    forSizeAndParents.remove(forRemove);
                }
            } else {
                forRemove.parent.rightChild = null;
                forRemove.parent.availableToAddRightChildren = true;
                forSizeAndParents.remove(forRemove);
                if (forAdding.contains(forRemove)) {
                    forAdding.remove(forRemove);
                }
            }
        } else if (forRemove.leftChild != null) {
            removeHelper(forRemove.leftChild);
        } else {
            removeHelper(forRemove.rightChild);
        }
    }

    @Override
    public boolean add(String s) {
        add(root, s);

        return true;
    }

    public void add(Entry<String> e, String s) {
        Entry<String> newEntry = new Entry<>(s);
        if (e.isAvailableToAddChildren()) {
            if (e.availableToAddLeftChildren) {
                e.leftChild = newEntry;
                forAdding.add(e.leftChild);
                forSizeAndParents.add(e.leftChild);
                if (e.elementName.equals("0")) {
                    e.leftChild.parent = new Entry<>(e.elementName);
                } else {
                    e.leftChild.parent = e;
                }
                e.availableToAddLeftChildren = false;
            } else {
                e.rightChild = newEntry;
                forAdding.add(e.rightChild);
                forSizeAndParents.add(e.rightChild);
                if (e.elementName.equals("0")) {
                    e.rightChild.parent = new Entry<>(e.elementName);
                } else {
                    e.rightChild.parent = e;
                }
                e.availableToAddRightChildren = false;
            }
        } else {
            add(forAdding.peek(), s);
            if (!forAdding.peek().isAvailableToAddChildren()) {
                forAdding.remove();
            }
        }
    }

    public String getParent(String s) {
        String result = "";
        for (int i = 0; i < forSizeAndParents.size(); i++) {
            if (s.equals(forSizeAndParents.get(i).elementName)) {
                result = forSizeAndParents.get(i).parent.elementName;
                return result;
            }
        }
        return null;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return forSizeAndParents.size();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}
