package com.xfkj.feign.order;

import com.xfkj.entity.order.ShoppingCart;
import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车控制层,包含用户地址管理,加载购物车信息
 * @author Administrator
 *
 */
@FeignClient(value = "hooliss-order-provider",contextId = "fcc")
//@RequestMapping("shopcart-provider")
public interface FeignCartController {


	/**
	 * 购物车跳转到提交页面
	 */
	@RequestMapping("shopcart-provider/cartTijiao.xf")
	public ResultBody cartTijiao(@RequestParam("user_id")Integer user_id);

	/**
	 *
	 * @param u_id:用户id
	 * @param watch_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping(value = "shopcart-provider/addCart.xf/{u_id}/{watch_id}/{add_count}",method = RequestMethod.POST)
	public ResultBody addCart(
            @PathVariable("u_id") Integer u_id,
            @PathVariable("watch_id") Integer watch_id,
            @PathVariable("add_count") Integer add_count
            ) ;

	@RequestMapping("shopcart-provider/doCart.xf")
	public ResultBody doCart(@RequestParam("user_id")Integer user_id);
	/**
	 * 合并未登录状态的购物车信息到用户
	 */
	@RequestMapping("shopcart-provider/cartMerge.xf")
	public ResultBody cartMerge(@RequestParam("user_id") Integer user_id,@RequestParam("cartInCookies") String cartInCookies);

	/**
	 * 添加商品数量方法
	 * @param user_id:用户id
	 * @param w_id:手表id
	 * @param add_count:添加数量
	 */
	@RequestMapping("shopcart-provider/addCount.xf")
	public ResultBody addCount(
            @RequestParam("user_id") Integer user_id,
            @RequestParam("w_id")Integer w_id,
            @RequestParam("addCount")Integer add_count
            );
	/**
	 * 删除购物车
	 * @param u_id:商品id
	 */
	@RequestMapping("shopcart-provider/delShop.xf")
	public ResultBody delWatch(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id);

	/**
	 * 验证购物车是否有此条记录
	 * @param u_id:用户id
	 * @param w_id:手表id
	 */
	@RequestMapping("shopcart-provider/existenceCart")
	Boolean existenceCart(@RequestParam("u_id")Integer u_id, @RequestParam("w_id")Integer w_id);

	@RequestMapping(value = "shopcart-provider/addOrder")
	public ResultBody add(@RequestParam("shoppingCart") ShoppingCart shoppingCart);
}
