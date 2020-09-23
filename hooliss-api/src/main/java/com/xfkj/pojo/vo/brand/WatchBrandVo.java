package com.xfkj.pojo.vo.brand;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.brand.WatchBrand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class WatchBrandVo extends WatchBrand {

    private CountryVo country;	//国家对象
}