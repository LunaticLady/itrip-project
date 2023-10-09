package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 酒店表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_hotel")
public class ItripHotel implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店名称
     */
    @TableField(value = "hotel_name")
    private String hotelName;

    /**
     * 所在国家id
     */
    @TableField(value = "country_id")
    private Long countryId;

    /**
     * 所在省份id
     */
    @TableField(value = "province_id")
    private Long provinceId;

    /**
     * 所在城市id
     */
    @TableField(value = "city_id")
    private Long cityId;

    /**
     * 酒店所在地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 酒店介绍（保存附文本）
     */
    @TableField(value = "details")
    private String details;

    /**
     * 酒店设施
     */
    @TableField(value = "facilities")
    private String facilities;

    /**
     * 酒店政策
     */
    @TableField(value = "hotel_policy")
    private String hotelPolicy;

    /**
     * 酒店类型(1:国内酒店 2:国际酒店)
     */
    @TableField(value = "hotel_type")
    private Integer hotelType;

    /**
     * (1:经济酒店  2:二星级酒店  3:三星级 4:四星酒店 5星酒店)
     */
    @TableField(value = "hotel_level")
    private Integer hotelLevel;

    /**
     * 是否是团购酒店
     */
    @TableField(value = "is_group_purchase")
    private Integer isGroupPurchase;

    /**
     * 城市名称 冗余字段
     */
    @TableField(value = "redundant_city_name")
    private String redundantCityName;

    /**
     * 省名称 冗余字段
     */
    @TableField(value = "redundant_province_name")
    private String redundantProvinceName;

    /**
     * 国家名称 冗余字段
     */
    @TableField(value = "redundant_country_name")
    private String redundantCountryName;

    /**
     * 酒店库存（冗余，每天开定时任务的时候更新）
     */
    @TableField(value = "redundant_hotel_store")
    private Integer redundantHotelStore;

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
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}