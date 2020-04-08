package com.xfkj.service.order.impl;

import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.order.ShoppingCartMapper;
import com.xfkj.pojo.order.ShoppingCart;
import com.xfkj.service.order.ShoppingCartService;
import com.xfkj.tools.ResultBody;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart queryShoppingCartByIdAndW_Id(Integer u_id, Integer w_id) {
        if (u_id!=null && u_id!=0){
            return shoppingCartMapper.findShoppingCartByIdAndW_Id(u_id, w_id);
        }
       return null;
    }
    @Cacheable(value = "shoppingCarts",key = "#p0.toString()")
    @Override
    public List<ShoppingCart> queryShoppingCartsByIdAndW_Id(Integer u_id, Integer w_id) {
        if (u_id!=null && u_id!=0){
            return shoppingCartMapper.findShoppingCartsByIdAndW_Id(u_id, w_id);
        }
        return null;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(value = "shoppingCarts",key = "#p0.u_id.toString()")
    @Override
    public Boolean addShopInfo(ShoppingCart sc) throws XFException {
        Integer integer = shoppingCartMapper.queryWatchContains(sc.getU_id(), sc.getW_id());
        boolean result = false;
        if ( integer==0){
            result= shoppingCartMapper.addShopInfo(sc)>0;
        }else if(integer==1){
            result= shoppingCartMapper.addCountBuy(sc.getU_id(),sc.getW_id(),sc.getAdd_count()  )>0;
        }else{
            shoppingCartMapper.deleteShopInfoByWId(sc.getW_id());
            result= shoppingCartMapper.addShopInfo(sc)>0;
        }
        if (!result){
            throw new XFException("500","保存购物车信息失败!" );
        }
        return true;
    }

    @CacheEvict(value = "shoppingCarts",key = "#p0.toString()")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean deleteShopInfoById(Integer u_id, Integer w_id) throws XFException {
        if (ObjectUtils.isEmpty(u_id)){
            throw new XFException("400",u_id+" is null!" );
        }
        return shoppingCartMapper.deleteShopInfoById(u_id,w_id)>0;
    }

    @CacheEvict(value = "shoppingCarts",key = "#p0.toString()")
    @Override
    public Boolean addCount(Integer u_id, Integer w_id,Integer count) throws XFException {

        if (ObjectUtils.isEmpty(u_id)||ObjectUtils.isEmpty(w_id)||ObjectUtils.isEmpty(count)){
            throw new XFException("400",u_id+"or "+w_id+" or "+count+" is null!" );
        }
        return shoppingCartMapper.addCount(u_id,w_id,count  )>0;
    }

    @Override
    public Boolean existenceCart(Integer u_id, Integer w_id) {
        if(u_id!=null&&w_id!=null){
            return shoppingCartMapper.existenceCart(u_id,w_id)==1;
        }
       throw new XFException("参数为空!");
    }
}
