package com.AE.ps.pojo;

import lombok.Data;

@Data
public class Process {
    /**
     *  服务名
     */
    private Integer pid;
    /**
     * 到达时间
     */
    private Integer arrivalTime;
    /**
     * 服务时间
     */
    private Integer serviceTime;
    /**
     * 静态优先权
     */
    private Integer staticPriority;
    /**
     * 开始时间
     */
    private Double startTime;
    /**
     * 完成时间
     */
    private Double endTime;
    /**
     * 周转时间
     */
    private Double turnaroundTime;
    /**
     * 带权周转时间
     */
    private Double weightedTurnaroundTime;
    /**
     * 运行时长
     */
    private Integer runningTime = 0;

    public Process() {
    }

    public Process(Integer pid, Integer arrivalTime, Integer serviceTime, Integer staticPriority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.staticPriority = staticPriority;
    }
}
