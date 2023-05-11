package com.AE.banker.utils;

import com.AE.banker.pojo.Process;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.*;

public class ProcessUtil {
    private ProcessUtil() {
    }

    private static final Random random = new Random();

    /**
     * 生成随机数量，随机维度
     * 进程列表
     */
    public static List<Process> createProcessList() {
        return createProcessList(random.nextInt(3, 10), random.nextInt(2, 7));
    }

    /**
     * 生成固定数量，固定维度
     * 进程列表
     *
     * @param n 进程数
     * @param d 资源向量维度
     */
    public static List<Process> createProcessList(int n, int d) {
        List<Process> processList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Process process = createProcess(d);
            process.setPid("P" + i);
            processList.add(process);
        }
        return processList;
    }

    /**
     * 生成随机进程
     *
     * @param d 资源向量维度
     */
    private static Process createProcess(int d) {
        Process process = new Process();
        //分配资源
        List<Integer> allocation = new ArrayList<>();
        List<Integer> need = new ArrayList<>();
        List<Integer> max = new ArrayList<>();
        List<Integer> work = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            int a = random.nextInt(20);
            int n = random.nextInt(20);
            allocation.add(a);
            need.add(n);
            max.add(a + n);
            work.add(0);
        }
        process.setAllocation(allocation);
        process.setNeed(need);
        process.setMax(max);
        process.setWork(work);
        process.setFinish(true);
        return process;
    }

    /**
     * 计算安全序列
     */
    public static void computeSafety(List<Process> processList, List<Integer> available) {
        processList.forEach(process -> {
            List<Integer> work = process.getWork();
            List<Integer> need = process.getNeed();
            List<Integer> allocation = process.getAllocation();
            int d = work.size();
            //赋值工作资源
            for (int i = 0; i < d; i++) {
                work.set(i, available.get(i));
            }
            //比较工作资源是否满足要求
            for (int i = 0; i < d; i++) {
                if (need.get(i) > work.get(i)) {
                    process.setFinish(false);
                    break;
                }
            }
            //回收资源
            if (process.getFinish()) {
                for (int i = 0; i < d; i++) {
                    available.set(i, allocation.get(i) + work.get(i));
                }
            }
        });
    }

    /**
     * 判断是否为安全序列
     */
    private static boolean isSave(List<Process> processList) {
        for (Process process : processList) {
            if (!process.getFinish()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印列表
     */
    public static void printProcessList(List<Process> processList) {
        AsciiTable at = new AsciiTable();
        at.addRule();
        if (!isSave(processList)) {
            List<String> rowList = new ArrayList<>();
            processList.forEach(process -> rowList.add(process.getPid()));
            rowList.add("不能满足请求");
            at.addRow(rowList);
            at.addRule();
        } else {
            //表头
            addHeaderForPrint(at, processList);
            at.addRule();

            processList.forEach(process -> {
                List<Object> rowList = new ArrayList<>();
                rowList.add(process.getPid());
                rowList.addAll(process.getMax());
                rowList.addAll(process.getAllocation());
                rowList.addAll(process.getNeed());
                rowList.addAll(process.getWork());
                List<Integer> work = process.getWork();
                List<Integer> allocation = process.getAllocation();
                for (int i = 0; i < work.size(); i++) {
                    rowList.add(work.get(i) + allocation.get(i));
                }
                rowList.add(process.getFinish());
                at.addRow(rowList);
                at.addRule();
            });
        }

        at.setTextAlignment(TextAlignment.CENTER);
        System.out.println(at.render(170));
    }

    /**
     * 打印表头
     */
    private static void addHeaderForPrint(AsciiTable at, List<Process> processList) {
        //维度
        int d = processList.get(0).getAllocation().size();
        List<String> headList = new ArrayList<>(Arrays.asList("Pid", "Max", "Allocation", "Need", "Work", "Work + Allocation", "finish"));

        List<String> placeList = new ArrayList<>(d);
        for (int i = 0; i < d - 1; i++) {
            placeList.add(null);
        }
        for (int i = 0; i < 5; i++) {
            headList.addAll(i + 1 + i * (d - 1), placeList);
        }
        at.addRow(headList);
        at.addRule();

        List<String> dimensionList = new ArrayList<>(Collections.singletonList(""));
        List<String> dNameList = new ArrayList<>(d);
        for (int i = 0; i < d; i++) {
            dNameList.add(String.valueOf((char) ('A' + i)));
        }
        for (int i = 0; i < 5; i++) {
            dimensionList.addAll(dNameList);
        }
        dimensionList.add("");
        at.addRow(dimensionList);
    }

}
