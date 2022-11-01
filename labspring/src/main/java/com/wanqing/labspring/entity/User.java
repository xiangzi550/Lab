package com.wanqing.labspring.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 颜朋祥
 * @since 2022-11-01
 */
@Getter
@Setter
  public class User implements Serializable {

    private static final long serialVersionUID = 1L;
@TableId
      private Integer username;

    private String name;

    private String gender;



    private String tel;

    private String position;

    private Integer age;

    private String idcard;

    private String level;

    private LocalDateTime createTime;


}
