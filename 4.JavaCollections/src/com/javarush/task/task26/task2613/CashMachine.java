package com.javarush.task.task26.task2613;

import javax.swing.plaf.ColorUIResource;
import java.io.IOException;
import java.util.Locale;

public class CashMachine {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        String code = null;// спрашиваем код
        try {
            code = ConsoleHelper.askCurrencyCode();
            String[] digits = ConsoleHelper.getValidTwoDigits(code);// получаем номинал и количество купюр
            CurrencyManipulator cm = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);// выбираем манипулятор
            cm.addAmount(Integer.parseInt(digits[0]), Integer.parseInt(digits[1]));// добавляем введенный номинал и количество купюр
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
