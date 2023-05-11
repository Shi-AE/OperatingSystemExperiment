package com.AE.banker.main;

import com.AE.banker.pojo.Process;
import com.AE.banker.utils.AvailableUtil;
import com.AE.banker.utils.ProcessUtil;
import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //初始化进程资源
        List<Process> processList = ProcessUtil.createProcessList(5, 4);
        ProcessUtil.printProcessList(processList);
        //初始化可用资源
        List<Integer> available = AvailableUtil.createAvailableByRandom(processList.get(0).getNeed().size());
        //计算安全序列
        //生成排列
        int size = processList.size();
        int[] list = new int[size];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        perm(list, 0, size, processList, available);
    }

    /**
     * 计算全排列
     */
    private static void perm(int[] list, int k, int m, List<Process> processList, List<Integer> available) {
        if (k == m) {
            System.out.println("available: " + available);
            //按照顺序复制序列
            List<Process> computeList = new ArrayList<>();
            for (int j : list) {
                Process process = JSON.parseObject(JSON.toJSONString(processList.get(j)), Process.class);
                computeList.add(process);
            }
            List<Integer> computeResource = JSON.parseArray(JSON.toJSONString(available), Integer.class);
            solve(computeList, computeResource);
            return;
        }
        for (int i = k; i < m; i++) {
            swap(list, i, k);
            perm(list, k + 1, m, processList, available);
            swap(list, i, k);
        }
    }

    private static void swap(int[] list, int i, int j) {
        int t = list[i];
        list[i] = list[j];
        list[j] = t;
    }

    /**
     * 按照排列复制序列
     * 并计算书否安全
     * 最后输出序列
     * 如果安全给出pid顺序
     * 不安全则给出提示不能满足请求
     */
    private static void solve(List<Process> processList, List<Integer> available) {
        ProcessUtil.computeSafety(processList, available);
        ProcessUtil.printProcessList(processList);
    }
}
