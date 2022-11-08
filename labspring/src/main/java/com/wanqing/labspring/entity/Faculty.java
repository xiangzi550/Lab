package com.wanqing.labspring.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-08
 */
@Getter
@Setter
@ToString
  public class Faculty implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 学院编号
     */
        private String id;

      /**
     * 学院名
     */
      private String name;

      /**
     * 楼号
     */
      private Integer building;

      /**
     * 负责人
     */
      private String responsibler;

      /**
     * 电话
     */
      private String tel;

      /**
     * 实验室数量
     */
      private Integer num;

      /**
     * 添加时间
     */
      private LocalDateTime createTime;


}
