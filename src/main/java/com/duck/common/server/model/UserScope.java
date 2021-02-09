package com.duck.common.server.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;

/**
 * @author generator@Lsz
 * @since 2021-02-09
 */
@Data
@TableName("user_scope")
public class UserScope implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    private Integer uid;

    /**
     * 角色权限
     */
    private String scope;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
