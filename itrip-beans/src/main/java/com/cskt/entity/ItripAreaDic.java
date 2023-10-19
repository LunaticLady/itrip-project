package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域字典表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_area_dic")
public class ItripAreaDic implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 区域名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 区域编号
     */
    @TableField(value = "area_no")
    private String areaNo;

    /**
     * 父级区域
     */
    @TableField(value = "parent")
    private Long parent;

    /**
     * 0:未激活 1:已激活
     */
    @TableField(value = "is_activated")
    private Integer isActivated;

    /**
     * 是否是商圈(0:不是 1:是)
     */
    @TableField(value = "is_trading_area")
    private Integer isTradingArea;

    /**
     * (0:不是 1：是)
     */
    @TableField(value = "is_hot")
    private Integer isHot;

    /**
     * 区域级别(0:国家级 1:省级 2:市级 3:县/区)
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 1:国内 2：国外
     */
    @TableField(value = "is_china")
    private Integer isChina;

    @TableField(value = "pinyin")
    private String pinyin;

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