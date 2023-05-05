package com.AE.ps.main;

import com.AE.ps.pojo.Process;
import com.AE.ps.schedule.Schedule;
import com.AE.ps.schedule.factory.ScheduleFactory;
import com.AE.ps.utils.ProcessUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> processList = ProcessUtil.createProcessList();
        ProcessUtil.printProcessList(processList);

        //FCFS
        System.out.println("FCFS（先来先服务）");
        Schedule fcfsSchedule = ScheduleFactory.createFCFSSchedule(processList);
        List<Process> fcfsList = fcfsSchedule.start();
        ProcessUtil.calculateTurnaroundTime(fcfsList);
        ProcessUtil.printProcessList(fcfsList);

        //SJF
        System.out.println("SJF（短服务）");
        Schedule sjfSchedule = ScheduleFactory.createSJFSchedule(processList);
        List<Process> sjfList = sjfSchedule.start();
        ProcessUtil.calculateTurnaroundTime(sjfList);
        ProcessUtil.printProcessList(sjfList);

        //非抢占
        System.out.println("优先级非抢占");
        Schedule nonPreemptiveSchedule = ScheduleFactory.createNonPreemptiveSchedule(processList);
        List<Process> nonPreemptiveList = nonPreemptiveSchedule.start();
        ProcessUtil.calculateTurnaroundTime(nonPreemptiveList);
        ProcessUtil.printProcessList(nonPreemptiveList);

        //抢占式
        System.out.println("优先级抢占");
        Schedule preemptiveSchedule = ScheduleFactory.createPreemptiveSchedule(processList);
        List<Process> preemptiveList = preemptiveSchedule.start();
        ProcessUtil.calculateTurnaroundTime(preemptiveList);
        ProcessUtil.printProcessList(preemptiveList);

        //轮转
        System.out.println("轮转");
        Schedule timeAroundSchedule = ScheduleFactory.createTimeAroundSchedule(processList);
        List<Process> timeAroundList = timeAroundSchedule.start();
        ProcessUtil.calculateTurnaroundTime(timeAroundList);
        ProcessUtil.printProcessList(timeAroundList);
    }
}
