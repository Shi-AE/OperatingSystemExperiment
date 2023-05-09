package com.AE.ps.schedule.factory;

import com.AE.ps.pojo.Process;
import com.AE.ps.schedule.Schedule;
import com.AE.ps.schedule.impl.*;
import com.alibaba.fastjson2.JSON;

import java.util.List;

/**
 * 进程调度器简单工厂
 */
public class ScheduleFactory {
    private ScheduleFactory() {}

    private static List<Process> copyProcessList(List<Process> processList) {
        String jsonString = JSON.toJSONString(processList);
        return JSON.parseArray(jsonString, Process.class);
    }

    public static Schedule createFCFSSchedule(List<Process> processList) {
        return new FCFSSchedule(copyProcessList(processList));
    }

    public static Schedule createNonPreemptiveSchedule(List<Process> processList) {
        return new NonPreemptiveSchedule(copyProcessList(processList));
    }

    public static Schedule createPreemptiveSchedule(List<Process> processList) {
        return new PreemptiveSchedule(copyProcessList(processList));
    }

    public static Schedule createSJFSchedule(List<Process> processList) {
        return new SJFSchedule(copyProcessList(processList));
    }

    public static Schedule createTimeAroundSchedule(List<Process> processList) {
        return new TimeAroundSchedule(copyProcessList(processList));
    }

}
