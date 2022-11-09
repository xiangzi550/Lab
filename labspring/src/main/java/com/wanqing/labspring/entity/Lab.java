package com.wanqing.labspring.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-09
 */
@Getter
@Setter
  public class Lab implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 实验室编号
     */
        private String id;

      /**
     * 实验室名称
     */
      private String name;

      /**
     * 所属学院
     */
      private String faculty;

      /**
     * 地址
     */
      private String address;

      /**
     * 容纳人数
     */
      private Integer capacity;

      /**
     * 实验室照片
     */
      private String picture;

      /**
     * 实验室类型
     */
      private String type;

      /**
     * 负责人
     */
      private String responsibler;

      /**
     * 联系方式
     */
      private String tel;

      /**
     * 详情
     */
      private String detail;

      /**
     * 添加时间
     */
      private LocalDateTime createTime;


}
