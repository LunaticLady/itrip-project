package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 酒店房间表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_hotel_room")
public class ItripHotelRoom implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店ID
     */
    @TableField(value = "hotel_id")
    private Long hotelId;

    /**
     * 房间名称
     */
    @TableField(value = "room_title")
    private String roomTitle;

    /**
     * 房间价格
     */
    @TableField(value = "room_price")
    private BigDecimal roomPrice;

    /**
     * 酒店床型
     */
    @TableField(value = "room_bed_type_id")
    private Long roomBedTypeId;

    /**
     * 是否包含早餐
     */
    @TableField(value = "is_having_breakfast")
    private Integer isHavingBreakfast;

    /**
     * 1:在线付 2:到店付 3:不限
     */
    @TableField(value = "pay_type")
    private Integer payType;

    /**
     * 满意度（冗余字段，在用户评论后更新）
     */
    @TableField(value = "satisfaction")
    private Long satisfaction;

    /**
     * 是否可预订(0:不可以 1:可以)
     */
    @TableField(value = "is_book")
    private Integer isBook;

    /**
     * 是否可取消(0:不可 1:可以)
     */
    @TableField(value = "is_cancel")
    private Integer isCancel;

    /**
     * 是否及时响应(0:不及时 1:及时)
     */
    @TableField(value = "is_timely_response")
    private Integer isTimelyResponse;

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