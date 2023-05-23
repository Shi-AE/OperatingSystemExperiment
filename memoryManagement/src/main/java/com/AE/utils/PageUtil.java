package com.AE.utils;

import com.AE.pojo.Page;
import com.alibaba.fastjson2.JSON;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class PageUtil {
    private final Random random = new Random();

    /**
     * 创建页面号引用串及对应内存状态
     */
    public List<Page> createPageList() {
        int referenceSize = random.nextInt(15, 30);
        int memorySize = random.nextInt(2, 6);
        return createPageList(referenceSize, memorySize);
    }


    /**
     * 根据页面号引用串大小 和 内存大小
     * 创建页面号引用串及对应内存状态
     */
    public List<Page> createPageList(int referenceSize, int memorySize) {
        List<Page> pageList = new ArrayList<>();
        for (int i = 0; i < referenceSize; i++) {
            List<Integer> memory = new ArrayList<>(Collections.nCopies(memorySize, 0));
            Page page = Page.builder()
                    .pageNumber(random.nextInt(1, memorySize * 2))
                    .memory(memory)
                    .isMiss(false)
                    .build();
            pageList.add(page);
        }
        return pageList;
    }

    /**
     * 打印序列
     */
    public void printList(List<Page> pageList) {
        int memorySize = pageList.get(0).getMemory().size();
        List<List<Object>> outList = new ArrayList<>();
        for (int i = 0; i < memorySize + 2; i++) {
            outList.add(new ArrayList<>());
        }
        //将纵列反转成行
        pageList.forEach(page -> {
            outList.get(0).add(page.getPageNumber());
            List<Integer> memory = page.getMemory();
            for (int i = 0; i < memorySize; i++) {
                outList.get(i + 1).add(memory.get(i) == 0 ? "" : memory.get(i));
            }
            outList.get(memorySize + 1).add(page.isMiss());
        });

        AsciiTable at = new AsciiTable();

        at.addRule();
        outList.forEach(out -> {
            at.addRow(out);
            at.addRule();
        });

        at.setTextAlignment(TextAlignment.CENTER);
        System.out.println(at.render(180));
    }

    public List<Page> pageListCopy(List<Page> pageList) {
        String jsonString = JSON.toJSONString(pageList);
        return JSON.parseArray(jsonString, Page.class);
    }
}
