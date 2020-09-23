package com.xfkj.pojo.vo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.order.Express;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 快递信息类
 * @author Administrator
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExpressVo extends Express {

	private Integer id;

	private Integer orderId;	//订单id
	
	private String company;		//快递公司
	
	private String oddNumber;		//快递单号
	
	private String createDate;		//快递创建时间
}
