package com.duck.common.server.core.enumerations;

import lombok.Getter;

/**
 * 角色权限
 */
@Getter
public enum UserLevel {
    LOGIN("0"),
    ;
    private String level;

    UserLevel(String level) {
        this.level = level;
    }
}
