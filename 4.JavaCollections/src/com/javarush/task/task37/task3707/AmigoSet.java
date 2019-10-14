package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet <E> extends AbstractSet implements Serializable, Cloneable, Set {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = Math.max(16, (int)(collection.size()/.75f)+1);
        map = new HashMap<>(capacity);
        for(E e : collection){
            this.add(e);
        }
    }

    @Override
    public boolean add(Object e) {
        return null == map.put((E) e, PRESENT);
    }



    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return null == map.remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() throws InternalError {
        AmigoSet<E> object = null;
        try{
            object = new AmigoSet<>();
            object.map = (HashMap<E, Object>) map.clone();
        }catch (Exception e){
            throw new InternalError();
        }
        return object;
    }

    private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();

        out.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        out.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        out.writeInt(map.keySet().size());
        for(E e: map.keySet()){
            out.writeObject(e);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        int capacity = in.readInt();
        float loadFactor = in.readFloat();
        int size = in.readInt();
        map = new HashMap<>(capacity, loadFactor);
        for(int i = 0; i < size; i++){
            E e = (E) in.readObject();
            map.put(e, PRESENT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AmigoSet<?> amigoSet = (AmigoSet<?>) o;
        return Objects.equals(map, amigoSet.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), map);
    }
}
