package com.xfkj.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户地址表
 * @author Administrator
 *
 */
@Data
public class WatchReceinfo implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1791476128276087640L;
    @Id
    @TableId(value = "info_id")
    private Integer infoId;

    private String raceName;

    private Long areaId;

    private String street;

    private String zipCode;		//邮箱

    private Integer userId;

    private String phone;	//电话

    private String defaultaddr; //是否为默认


    public WatchReceinfo(){

    }

}