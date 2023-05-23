package com.AE.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    private Integer pageNumber;
    private List<Integer> memory;
    private boolean isMiss;
}