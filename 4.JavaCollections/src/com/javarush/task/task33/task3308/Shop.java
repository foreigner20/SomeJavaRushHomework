package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class Shop {
    public Goods goods;
    public int count;
    public double profit;

    public String[] secretData;

    @XmlRootElement
    static class Goods{
        @XmlElementWrapper(name = "goods", nillable = true)
        public List<String> names;
    }
}
