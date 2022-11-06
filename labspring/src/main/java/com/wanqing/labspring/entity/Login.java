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
 * @since 2022-11-05
 */
@Getter
@Setter
  public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 登陆用户名
     */
      @TableId
        private String username;
      /**
     * 登陆密码
     */
      private String password;
      /**
     * 创建时间
     */
      private LocalDateTime createTime;

}
