package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.EventQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery {

    static ArrayList<Log> logs = new ArrayList<>();

    static Pattern pattern = Pattern.compile("(?<ip>[\\d]+.[\\d]+.[\\d]+.[\\d]+)\\s(?<name>[a-zA-Z ]+)\\s(?<date>[\\d]+.[\\d]+.[\\d]+ [\\d]+:[\\d]+:[\\d]+)\\s(?<event>[\\w]+)\\s?((?<taskNumber>[\\d]+)|)\\s(?<status>[\\w]+)");

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public LogParser(Path logDir) {

        String[] strings = Arrays.stream(logDir.toFile().list())
                .filter(s -> s.endsWith(".log"))
                .toArray(String[]::new);

        for (int i = 0; i < strings.length; i++) {
            try {
                Path path1 = Paths.get(logDir.toString(), strings[i]);
                Stream<String> stream = Files.lines(path1);
                stream.map(LogParser::stringToLog)
                        .forEach(log -> logs.add(log));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static private Log stringToLog(String string) {
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        String ip = matcher.group("ip");
        String name = matcher.group("name");
        Date date = null;
        try {
            date = simpleDateFormat.parse(matcher.group("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Event event = Event.valueOf(matcher.group("event"));
        Integer task = matcher.group("taskNumber") == null ? null : Integer.parseInt(matcher.group("taskNumber"));
        Status status = Status.valueOf(matcher.group("status"));

        return new Log(ip, name, date, event, task, status);
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return (int) logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .map(log -> log.ip)
                .distinct()
                .count();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .map(log -> log.ip)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .filter(log -> log.user.equals(user))
                .map(log -> log.ip)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .filter(log -> log.event.equals(event))
                .map(log -> log.ip)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .filter(log -> log.status.equals(status))
                .map(log -> log.ip)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> allUsers = new HashSet<>();
        for (Log l : logs) {
            allUsers.add(l.user);
        }
        return allUsers;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return (int) logs.stream()
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .map(log -> log.user)
                .distinct()
                .count();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) logs.stream()
                .filter(log -> log.user.equals(user))
                .filter(log -> (after == null || (log.date.getTime() >= after.getTime())) && (before == null || log.date.getTime() <= before.getTime()))
                .map(log -> log.event)
                .distinct()
                .count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> allUsers = new HashSet<>();
        for (Log l : logs) {
            if (l.ip.equals(ip)) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    allUsers.add(l.user);
                }
            }
        }
        return allUsers;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.LOGIN) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.DOWNLOAD_PLUGIN) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.WRITE_MESSAGE) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.SOLVE_TASK) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.SOLVE_TASK && l.task == task) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.DONE_TASK) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> users = new HashSet<>();
        for (Log l : logs) {
            if (l.event == Event.DONE_TASK && l.task == task) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    users.add(l.user);
                }
            }
        }
        return users;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> allDates = new HashSet<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if (l.event == event) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allDates.add(l.date);
                    }
                }
            }
        }
        return allDates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> allDates = new HashSet<>();
        for (Log l : logs) {
            if (l.status == Status.FAILED) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    allDates.add(l.date);
                }
            }
        }
        return allDates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> allDates = new HashSet<>();
        for (Log l : logs) {
            if (l.status == Status.ERROR) {
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    allDates.add(l.date);
                }
            }
        }
        return allDates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        ArrayList<Date> allDates = new ArrayList<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if (l.event == Event.LOGIN) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allDates.add(l.date);
                    }
                }
            }
        }
        if (allDates.size() == 0) {
            return null;
        }
        Date firstDate = allDates.get(0);
        for (int i = 1; i < allDates.size(); i++) {
            if (firstDate.getTime() > allDates.get(i).getTime()) {
                firstDate = allDates.get(i);
            }
        }
        return firstDate;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        ArrayList<Date> allDates = new ArrayList<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if (l.event == Event.SOLVE_TASK) {
                    if (l.task == task) {
                        if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                            allDates.add(l.date);
                        }
                    }
                }
            }
        }
        if (allDates.size() == 0) {
            return null;
        }
        Date firstDate = allDates.get(0);
        for (int i = 1; i < allDates.size(); i++) {
            if (firstDate.getTime() > allDates.get(i).getTime()) {
                firstDate = allDates.get(i);
            }
        }
        return firstDate;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        ArrayList<Date> allDates = new ArrayList<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if (l.event == Event.DONE_TASK) {
                    if (l.task == task) {
                        if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                            allDates.add(l.date);
                        }
                    }
                }
            }
        }
        if (allDates.size() == 0) {
            return null;
        }
        Date firstDate = allDates.get(0);
        for (int i = 1; i < allDates.size(); i++) {
            if (firstDate.getTime() > allDates.get(i).getTime()) {
                firstDate = allDates.get(i);
            }
        }
        return firstDate;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> allDates = new HashSet<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if(l.event == Event.WRITE_MESSAGE) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allDates.add(l.date);
                    }
                }
            }
        }

        return allDates;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set<Date> allDates = new HashSet<>();
        for (Log l : logs) {
            if (l.user.equals(user)) {
                if(l.event == Event.DOWNLOAD_PLUGIN) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allDates.add(l.date);
                    }
                }
            }
        }
        return allDates;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    set.add(l.event);
                }
            }
        }
        return set.size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> allEvents = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                    allEvents.add(l.event);
                }
            }
        }
        return allEvents;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> allEvents = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if(l.ip.equals(ip)) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allEvents.add(l.event);
                    }
                }
            }
        }
        return allEvents;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> allEvents = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if(l.user.equals(user)) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allEvents.add(l.event);
                    }
                }
            }
        }
        return allEvents;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> allEvents = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if(l.status == Status.FAILED) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allEvents.add(l.event);
                    }
                }
            }
        }
        return allEvents;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> allEvents = new HashSet<>();
        for(Log l : logs){
            if(l.event != null){
                if(l.status == Status.ERROR) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        allEvents.add(l.event);
                    }
                }
            }
        }
        return allEvents;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        List<Integer> list = new ArrayList<>();
        for(Log l : logs){
            if(l.event == Event.SOLVE_TASK){
                if(l.task == task) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || l.date.getTime() <= before.getTime())) {
                        list.add(l.task);
                    }
                }
            }
        }
        return list.size();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        List<Integer> list = new ArrayList<>();
        for(Log l : logs){
            if(l.event == Event.DONE_TASK){
                if(l.task == task) {
                    if ((after == null || (l.date.getTime() >= after.getTime())) && (before == null || (l.date.getTime() <= before.getTime()))) {
                        list.add(l.task);
                    }
                }
            }
        }
        return list.size();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for(Log l : logs){
            if(l.task != null){
                if(!map.containsKey(l.task)) {
                    map.put(l.task, getNumberOfAttemptToSolveTask(l.task, after, before));
                }
            }
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        for(Log l : logs){
            if(l.task != null){
                if(!map.containsKey(l.task)) {
                    map.put(l.task, getNumberOfSuccessfulAttemptToSolveTask(l.task, after, before));
                }
            }
        }
        return map;
    }
}