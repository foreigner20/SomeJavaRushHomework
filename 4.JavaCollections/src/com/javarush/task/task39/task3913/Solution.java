package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("logs"));
        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(48, null, null));
    }
}