package com.duck.common.server.model;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.duck.common.server.core.utils.GenericAndJson;
import com.duck.common.server.core.utils.JsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

/**
 * @author generator@Lsz
 * @since 2021-02-22
 */
@Data
@TableName(value = "json",autoResultMap = true)
public class Json implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JsonBody json;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<JsonBody> list;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Date deleteTime;

}
