package com.xfkj.pojo.vo.stock;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfkj.entity.stock.StockPile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 库存表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StockPileVo extends StockPile {
}
