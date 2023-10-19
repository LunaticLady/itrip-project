package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实时库存表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_hotel_temp_store")
public class ItripHotelTempStore implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "hotel_id")
    private Integer hotelId;

    /**
     * 商品id
     */
    @TableField(value = "room_id")
    private Long roomId;

    /**
     * 记录时间
     */
    @TableField(value = "record_date")
    private Date recordDate;

    /**
     * 库存
     */
    @TableField(value = "store")
    private Integer store;

    @TableField(value = "creation_date")
    private Date creationDate;

    @TableField(value = "created_by")
    private Long createdBy;

    @TableField(value = "modify_date")
    private Date modifyDate;

    @TableField(value = "modified_by")
    private Long modifiedBy;

    /**
     * 逻辑删除（0:未删除；1：删除）
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer deleted;


}