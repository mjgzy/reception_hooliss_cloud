package com.xfkj.feign.order;

import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.order.ShoppingCart;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.service.order.ShoppingCartService;
import com.xfkj.tools.ResultBody;
import com.xfkj.utils.DateFormatUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 购物车控制层,包含用户地址管理,加载购物车信息
 * @author Administrator
 *
 */
@FeignClient("hooliss-order-provider")
@RequestMapping("shopcart-provider")
public interface FeignCartController {


	/**
	 * 购物车跳转到提交页面
	 */
	@RequestMapping("/cartTijiao.xf")
	public ResultBody cartTijiao(@RequestParam("user_id")Integer user_id);

	/**
	 *
	 * @param u_id:用户id
	 * @param watch_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping(value = "/addCart.xf/{u_id}/{watch_id}/{add_count}",method = RequestMethod.POST)
	public ResultBody addCart(
            @PathVariable("u_id") Integer u_id,
            @PathVariable("watch_id") Integer watch_id,
            @PathVariable("add_count") Integer add_count
            ) ;

	@RequestMapping("/doCart.xf")
	public ResultBody doCart(@RequestParam("user_id")Integer user_id);
	/**
	 * 合并未登录状态的购物车信息到用户
	 */
	@RequestMapping("/cartMerge.xf")
	public ResultBody cartMerge(@RequestParam("user_id") Integer user_id,@RequestParam("cartInCookies") String cartInCookies);

	/**
	 * 添加商品数量方法
	 * @param user_id:用户id
	 * @param w_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping("/addCount.xf")
	public ResultBody addCount(
            @RequestParam("user_id") Integer user_id,
            @RequestParam("w_id")Integer w_id,
            @RequestParam("addCount")Integer add_count
            );
	/**
	 * 删除购物车
	 * @param u_id:商品id
	 */
	@RequestMapping("/delShop.xf")
	public ResultBody delWatch(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id);

	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	@RequestMapping("existenceCart")
	Boolean existenceCart(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id);

	@RequestMapping(value = "/addOrder")
	public ResultBody add(@RequestParam("shoppingCart") ShoppingCart shoppingCart);
}
