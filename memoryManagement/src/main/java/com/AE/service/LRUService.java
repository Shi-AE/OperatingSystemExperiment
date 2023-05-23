package com.AE.service;

import com.AE.pojo.Page;
import com.alibaba.fastjson2.JSON;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class LRUService {

    /**
     * 计算最近最久未使用LRU算法
     */
    public int calculateForLRU(List<Page> pageList) {
        int memorySize = pageList.get(0).getMemory().size();
        //当前内存
        Queue<Integer> memory = new LinkedList<>(Collections.nCopies(memorySize, 0));
        AtomicInteger miss = new AtomicInteger();

        pageList.forEach(page -> {
            Integer pageNumber = page.getPageNumber();
            //从列表中移除pn
            boolean remove = memory.remove(pageNumber);
            //如果不存在移除失败
            if (!remove) {
                //发生缺页中断
                miss.getAndIncrement();
                //移除顶部
                memory.poll();
                page.setMiss(true);
            }
            //pn插入尾部
            memory.add(pageNumber);
            page.setMemory(JSON.parseArray(JSON.toJSONString(memory), Integer.class));
        });

        return miss.get();
    }
}
