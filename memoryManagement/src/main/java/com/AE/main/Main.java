package com.AE.main;

import com.AE.pojo.Page;
import com.AE.service.FIFOService;
import com.AE.service.LRUService;
import com.AE.utils.PageUtil;

import java.util.List;

public class Main {
    private static final FIFOService fifoService = new FIFOService();
    private static final LRUService lruService = new LRUService();

    public static void main(String[] args) {
        //初始化
        List<Page> pageList = PageUtil.createPageList(30, 4);
        PageUtil.printList(PageUtil.pageListCopy(pageList));
        int referenceSize = pageList.size();
        int memorySize = pageList.get(0).getMemory().size();
        System.out.printf("页面号引用串大小: %d 内存大小: %d\n", referenceSize, memorySize);

        //FIFO
        int fifoMiss = fifoService.calculateForFIFO(pageList);
        PageUtil.printList(PageUtil.pageListCopy(pageList));
        System.out.printf("缺页率: %f%%\n", (double) fifoMiss / referenceSize * 100);

        //LRU
        int lruMiss = lruService.calculateForLRU(pageList);
        PageUtil.printList(PageUtil.pageListCopy(pageList));
        System.out.printf("缺页率: %f%%\n", (double) lruMiss / referenceSize * 100);
    }
}
