package com.AE.ps.schedule;

import com.AE.ps.pojo.Process;

import java.util.List;

/**
 * 进程调度器抽象模板
 */
public abstract class Schedule {
    /**
     * 开始调度
     */
    public List<Process> start() {
        return counter();
    }
    /**
     * 时间计数器
     */
    protected List<Process>  counter() {
        return null;
    }
    /**
     * 处理进程
     */
    protected void handle() {}
}
