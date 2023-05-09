package com.AE.ps.schedule.impl;

import com.AE.ps.pojo.Process;
import com.AE.ps.schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class FCFSSchedule extends Schedule {
    private List<Process> processList;
    private List<Process> taskList;
    private List<Process> accomplishList;
    private int taskSize;
    private int time = 0;

    public FCFSSchedule(List<Process> processList) {
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
            }
            handle();
            time++;
        }

        return accomplishList;
    }

    @Override
    protected void handle() {
        if (taskList.size() > 0) {
            Process process = taskList.get(0);
            if (process.getStartTime() == null) {
                process.setStartTime((double) time);
            }
            //保证服务时间大于0
            process.setRunningTime(process.getRunningTime() + 1);
            if (process.getServiceTime().equals(process.getRunningTime())) {
                process.setEndTime((double) (time + 1));
                accomplishList.add(process);
                taskList.remove(0);
            }
        }
    }
}
