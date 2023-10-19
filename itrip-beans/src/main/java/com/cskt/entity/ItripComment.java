package com.cskt.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "itrip_comment")
public class ItripComment implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 如果产品是酒店的话 改字段保存酒店id
     */
    @TableField(value = "hotel_id")
    private Long hotelId;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 商品类型(0:旅游产品 1:酒店产品 2:机票产品)
     */
    @TableField(value = "product_type")
    private Integer productType;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 是否包含图片(当用户上传评论时检测)0:无图片 1:有图片
     */
    @TableField(value = "is_having_img")
    private Integer isHavingImg;

    /**
     * 位置评分
     */
    @TableField(value = "position_score")
    private Integer positionScore;

    /**
     * 设施评分
     */
    @TableField(value = "facilities_score")
    private Integer facilitiesScore;

    /**
     * 服务评分
     */
    @TableField(value = "service_score")
    private Integer serviceScore;

    /**
     * 卫生评分
     */
    @TableField(value = "hygiene_score")
    private Integer hygieneScore;

    /**
     * 综合评分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 出游类型
     */
    @TableField(value = "trip_mode")
    private Long tripMode;

    /**
     * 是否满意（0：有待改善 1：值得推荐）
     */
    @TableField(value = "is_ok")
    private Integer isOk;

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