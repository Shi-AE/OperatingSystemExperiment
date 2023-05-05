package com.AE.ps.schedule.impl;

import com.AE.ps.pojo.Process;
import com.AE.ps.schedule.Schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PreemptiveSchedule extends Schedule {
    private List<Process> processList;
    private List<Process> taskList;
    private List<Process> accomplishList;
    private Process activeProcess;
    private int taskSize;
    private int time = 0;

    public PreemptiveSchedule(List<Process> processList) {
        this.processList = processList;
    }

    @Override
    protected List<Process> counter() {
        time = 0;
        taskSize = processList.size();
        taskList = new ArrayList<>();
        accomplishList = new ArrayList<>();

        while (accomplishList.size() < taskSize) {
            while (processList.size() > 0 && processList.get(0).getArrivalTime().equals(time)) {
                taskList.add(processList.get(0));
                processList.remove(0);
                taskList.sort(Comparator.comparingInt(Process::getStaticPriority));
            }
            handle();
            time++;
        }

        return accomplishList;
    }

    @Override
    protected void handle() {
        if (activeProcess == null && taskList.size() > 0) {
            activeProcess = taskList.get(0);
            taskList.remove(0);
            if (activeProcess.getStartTime() == null) {
                activeProcess.setStartTime((double) time);
            }
        } else if (activeProcess != null && taskList.size() > 0 && taskList.get(0).getStaticPriority() < activeProcess.getStaticPriority()) {
            taskList.add(activeProcess);
            activeProcess = taskList.get(0);
            taskList.remove(0);
            if (activeProcess.getStartTime() == null) {
                activeProcess.setStartTime((double) time);
            }
        }
        if (activeProcess != null) {
            activeProcess.setRunningTime(activeProcess.getRunningTime() + 1);
            if (activeProcess.getServiceTime().equals(activeProcess.getRunningTime())) {
                activeProcess.setEndTime((double) time + 1);
                accomplishList.add(activeProcess);
                activeProcess = null;
            }
        }
    }
}
