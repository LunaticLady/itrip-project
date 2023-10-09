package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 订单表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_hotel_order")
public class ItripHotelOrder implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 订单类型(0:旅游产品 1:酒店产品 2：机票产品)
     */
    @TableField(value = "order_type")
    private Integer orderType;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 交易编号
     */
    @TableField(value = "trade_no")
    private String tradeNo;

    /**
     * 冗余字段 酒店id
     */
    @TableField(value = "hotel_id")
    private Long hotelId;

    /**
     * 冗余字段 酒店名称
     */
    @TableField(value = "hotel_name")
    private String hotelName;

    /**
     * 房间id
     */
    @TableField(value = "room_id")
    private Long roomId;

    /**
     * 消耗数量
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 预订天数
     */
    @TableField(value = "booking_days")
    private Integer bookingDays;

    /**
     * 入住时间
     */
    @TableField(value = "check_in_date")
    private Date checkInDate;

    /**
     * 退房时间
     */
    @TableField(value = "check_out_date")
    private Date checkOutDate;

    /**
     * 订单状态（0：待支付 1:已取消 2:支付成功 3:已消费 4：已点评）
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 支付金额
     */
    @TableField(value = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 支付方式:1:支付宝 2:微信 3:到店付
     */
    @TableField(value = "pay_type")
    private Integer payType;

    /**
     * 联系手机号
     */
    @TableField(value = "notice_phone")
    private String noticePhone;

    /**
     * 联系邮箱
     */
    @TableField(value = "notice_email")
    private String noticeEmail;

    /**
     * 特殊需求
     */
    @TableField(value = "special_requirement")
    private String specialRequirement;

    /**
     * 是否需要发票（0：不需要 1：需要）
     */
    @TableField(value = "is_need_invoice")
    private Integer isNeedInvoice;

    /**
     * 发票类型（0：个人 1：公司）
     */
    @TableField(value = "invoice_type")
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    @TableField(value = "invoice_head")
    private String invoiceHead;

    /**
     * 入住人名称
     */
    @TableField(value = "link_user_name")
    private String linkUserName;

    /**
     * 0:WEB端 1:手机端 2:其他客户端
     */
    @TableField(value = "book_type")
    private Integer bookType;

    /**
     * 预定时间
     */
    @TableField(value = "creation_date")
    private Date creationDate;

    @TableField(value = "created_by")
    private Long createdBy;

    /**
     * 支付完成时间
     */
    @TableField(value = "modify_date")
    private Date modifyDate;

    @TableField(value = "modified_by")
    private Long modifiedBy;

    /**
     * 逻辑删除（0:未删除；1：删除）
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}