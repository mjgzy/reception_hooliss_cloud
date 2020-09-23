package com.xfkj.pojo.vo.commodity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.brand.WatchBrand;
import com.xfkj.entity.commodity.WatchType;
import com.xfkj.pojo.vo.brand.WatchBrandVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 手表档次
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WatchTypeVo extends WatchType {

    private List<WatchBrand> watchBrandVo;
}