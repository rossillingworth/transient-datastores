package com.techmale.persistance;

import java.util.HashMap;
import java.util.Map;

/**
 * Application Lifetime persistence DataStore
 * ie:globally available configuration store
 *
 * this is only live for the duration of the application
 * ie: when server is stopped, data is gone
 *
 * also, it should be write once, read lots
 * ie: only initialise it at start time
 *
 */
public class ConfigDataStore{

    private static HashMap<String,Object> data = new HashMap<String,Object>();
    private static boolean immutable = false;

    /**
     * Assign key/value pair to runtime application storage
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value){
        if(has(key) && immutable){
            throw new RuntimeException("Attempting to alter immutable configuration.");
        }
        data.put(key,value);
    }

    /**
     * Retrieve a value from config storage
     *
     * NB:
     * if key is missing, it will through an exception
     * As all config should have a default value
     * (opinionated I know, but probably a good idea)
     *
     * @param key
     * @return
     */
    public static Object get(String key){
        if(!has(key)){throw new NullPointerException(String.format("KEY[%s] missing from ConfigDataStore.",key));}
        return data.get(key);
    }

    public static <T> T get(String key, Class<T> c) throws NullPointerException{
        Object val = get(key);
        if(!c.isInstance(val)){
            throw new RuntimeException(String.format("KEY[%s] resolves to Class type [%s], not [%s] as expected",key,val.getClass(),c));
        }
        return (T)val;
    }

    /**
     * Check if store contains a key
     *
     * @param key
     * @return
     */
    public static boolean has(String key){
        return data.containsKey(key);
    }

    public static boolean isImmutable() {
        return immutable;
    }

    public static void setImmutable(boolean immutable) {
        ConfigDataStore.immutable = immutable;
    }
}
