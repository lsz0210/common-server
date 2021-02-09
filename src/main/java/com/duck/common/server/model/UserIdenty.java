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
@TableName("user_identy")
public class UserIdenty implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户uid
     */
    private Long uid;

    /**
     * 密码
     */
    private String password;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;


}
