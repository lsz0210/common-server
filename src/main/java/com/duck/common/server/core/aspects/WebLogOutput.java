package com.duck.common.server.core.aspects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 5duck
 * @date 2021-02-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebLogOutput {
    private Long endTime;
    private Long useTime;
    private String responseParam;
}
