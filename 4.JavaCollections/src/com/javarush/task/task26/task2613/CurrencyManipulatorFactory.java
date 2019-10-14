package com.javarush.task.task26.task2613;

import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {

    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        for (Map.Entry<String, CurrencyManipulator> pair : map.entrySet()){
            if(currencyCode.equalsIgnoreCase(pair.getKey())){
                return pair.getValue();
            }
        }
        map.put(currencyCode, new CurrencyManipulator(currencyCode));
        return map.get(currencyCode);
    }
}
