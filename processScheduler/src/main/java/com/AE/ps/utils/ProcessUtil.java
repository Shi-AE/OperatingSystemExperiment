package com.AE.ps.utils;

import com.AE.ps.pojo.Process;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.*;

/**
 * 进程、进程列表工具类
 */
public class ProcessUtil {
    private ProcessUtil() {
    }

    static private final int processNumber = 5;
    static private final Random random = new Random();

    static public int getProcessNumber() {
        return processNumber;
    }

    /**
     * 生成随机进程列表
     */
    public static List<Process> createProcessList() {
        return createProcessList(processNumber);
    }

    /**
     * 生成固定进程列表
     */
    public static List<Process> createFixProcessList() {
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(1, 0, 3, 1));
        processList.add(new Process(2, 6, 4, 2));
        processList.add(new Process(3, 3, 16, 3));
        processList.add(new Process(4, 6, 2, 3));
        processList.add(new Process(5, 5, 3, 3));
        return processList;
    }

    /**
     * 根据设置个数生成随机进程列表
     */
    public static List<Process> createProcessList(int processNumber) {

        List<Integer> randomList = new ArrayList<>();
        for (int i = 0; i < processNumber; i++) {
            randomList.add(i + 1);
        }
        Collections.shuffle(randomList);

        List<Process> processList = new ArrayList<>();
        for (int i = 0; i < processNumber; i++) {
            Process process = new Process();
            process.setPid(i);
            process.setArrivalTime(random.nextInt(0, 2 * processNumber));
            process.setServiceTime(random.nextInt(1, 20));
            process.setStaticPriority(randomList.get(i));
            processList.add(process);
        }

        sortProcessListByArrival(processList);

        return processList;
    }

    /**
     * 进程列表按照到达时间排序
     */
    private static void sortProcessListByArrival(List<Process> processList) {
        processList.sort(Comparator.comparingInt(Process::getArrivalTime));
    }

    /**
     * 计算周转时间和带权周转时间
     * 周转时间：完成时间 - 到达时间
     * 带权周转时间：周转时间 / 服务时间
     */
    public static void calculateTurnaroundTime(List<Process> processList) {
        processList.forEach(process -> {
            Double endTime = process.getEndTime();
            Integer arrivalTime = process.getArrivalTime();
            Integer serviceTime = process.getServiceTime();
            double turnaroundTime = endTime - arrivalTime;
            double weightedTurnaroundTime = turnaroundTime / serviceTime;
            process.setTurnaroundTime(turnaroundTime);
            process.setWeightedTurnaroundTime(weightedTurnaroundTime);
        });
    }

    /**
     * 格式化打印进程列表
     */
    public static void printProcessList(List<Process> processList) {
        sortProcessListByArrival(processList);

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("pid", "arrivalTime", "serviceTime", "staticPriority", "startTime", "endTime", "turnaroundTime", "weightedTurnaroundTime");
        at.addRule();

        processList.forEach(process -> {
            at.addRow(process.getPid() != null ? process.getPid() : "",
                    process.getArrivalTime() != null ? process.getArrivalTime() : "",
                    process.getServiceTime() != null ? process.getServiceTime() : "",
                    process.getStaticPriority() != null ? process.getStaticPriority() : "",
                    process.getStartTime() != null ? process.getStartTime() : "",
                    process.getEndTime() != null ? process.getEndTime() : "",
                    process.getTurnaroundTime() != null ? process.getTurnaroundTime() : "",
                    process.getWeightedTurnaroundTime() != null ? String.format("%.4f", process.getWeightedTurnaroundTime()) : ""
            );
            at.addRule();
        });

        at.setTextAlignment(TextAlignment.CENTER);
        System.out.println(at.render(120));
    }
}
