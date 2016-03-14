package com.techmale.persistance;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for Persistence stores
 * Uses Map to provide key/value storage
 */
public abstract class DataStore<T> {

    private static HashMap<String,Object> data = new HashMap<String,Object>();
    private static boolean immutable = false;

    public static HashMap<String,Object> getData(){
        return data;
    };

    private static void setMap(Map<String,Object> map){
        dataStore.set(map);
    }

    /**
     * Push a key/value pair to DataStore
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value){
        getMap().put(key,value);
    }

    /**
     * Check if DataStore contains a key
     *
     * @param key
     * @return
     */
    public static boolean has(String key){
        return getMap().containsKey(key);
    }


    /**
     * Get a value by key from DataStore
     *
     * NB:
     * a NullPointerException will be thrown if the key is missing
     * So design your code defensively, ie: use if(!ds.has(key)){....}
     * This is opinionated(fail-fast), but avoids propagating a NULL to other code
     *
     * @param key
     * @return
     * @throws NullPointerException If key Not Found
     */
    public static Object get(String key) throws NullPointerException{
        if(!has(key)){
            throw new NullPointerException(String.format("KEY[%s] missing from ThreadLocalDataStore.",key));
        }
        return getMap().get(key);
    }

    /**
     * Get a key by value from DataStore and ensure its Class type
     *
     * NB:
     * a NullPointerException will be thrown if the key is missing
     * So design your code defensively, ie: use if(!ds.has(key)){....}
     * This is opinionated(fail-fast), but avoids propagating a NULL to other code
     *
     * @param key
     * @param c
     * @return
     * @throws NullPointerException
     */
    public static <T> T get(String key, Class<T> c) throws NullPointerException{
        Object val = get(key);
        if(!c.isInstance(val)){
            throw new RuntimeException(String.format("KEY[%s] resolves to Class type [%s], not [%s] as expected",key,val.getClass(),c));
        }
        return (T)val;
    }


    /**
     * Wipe data in DataStore
     */
    public static void clear(){
        dataStore.remove();
    }

}
