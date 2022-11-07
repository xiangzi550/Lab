package com.wanqing.labspring.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
  public class User implements Serializable {

    private static final long serialVersionUID = 1L;
@TableId
      private String username;

    private String name;

    private String gender;

    private String tel;

    private String position;

    private Integer age;

    private String idcard;

    private String level;

    private Timestamp createTime;
}
