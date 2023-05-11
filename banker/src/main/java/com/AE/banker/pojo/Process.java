package com.AE.banker.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Process {
    /**
     * 进程id
     */
    private String pid;
    /**
     * 最大需求资源
     */
    private List<Integer> max;
    /**
     * 分配资源
     */
    private List<Integer> allocation;
    /**
     * 需要资源
     */
    private List<Integer> need;
    /**
     * 工作资源向量
     */
    private List<Integer> work;
    /**
     * 是否能完成
     */
    private Boolean finish;
}
