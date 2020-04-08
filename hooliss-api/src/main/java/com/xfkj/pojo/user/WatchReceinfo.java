package com.xfkj.pojo.user;

import lombok.Data;

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

	private Integer info_id;

    private String race_name;

    private String province;

    private String city;

    private String area;

    private String street;

    private String zipCode;		//邮箱

    private Integer user_id;
    
    private String phone;	//电话

    private String defaultaddr; //是否为默认


    public WatchReceinfo(String province, String city, String area, String street, Integer info_id) {
        super();
        this.province=province;
        this.city=city;
        this.area=area;
        this.street=street;
        this.info_id=info_id;
    }
    public WatchReceinfo(){

    }

}