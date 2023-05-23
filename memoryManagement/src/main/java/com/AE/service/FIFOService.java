package com.AE.service;

import com.AE.pojo.Page;
import com.alibaba.fastjson2.JSON;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FIFOService {

    /**
     * 计算先进先出FIFO算法
     * 并统计缺页次数
     */
    public int calculateForFIFO(List<Page> pageList) {
        int memorySize = pageList.get(0).getMemory().size();
        //内存位置先进先出队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < memorySize; i++) {
            queue.add(i);
        }
        //当前内存列表
        List<Integer> memory = new ArrayList<>(Collections.nCopies(memorySize, 0));
        AtomicInteger miss = new AtomicInteger();

        pageList.forEach(page -> {
            Integer pageNumber = page.getPageNumber();
            if (!memory.contains(pageNumber)) {
                //发生缺页
                //获取需要置换的索引
                Integer index = queue.remove();
                //置换页面
                memory.set(index, pageNumber);
                queue.add(index);
                miss.getAndIncrement();
                page.setMiss(true);
            }
            page.setMemory(JSON.parseArray(JSON.toJSONString(memory), Integer.class));
        });

        return miss.get();
    }
}
