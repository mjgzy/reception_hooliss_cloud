package com.xfkj.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.xfkj.enums.CommonEnum;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.entity.user.WatchReceinfo;
import com.xfkj.service.WReceinfoService;
import com.xfkj.tools.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.RequestUtil;
import org.bouncycastle.pqc.math.linearalgebra.IntUtils;
import org.jasypt.commons.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址相关接口
 */
@Slf4j
@RestController
@RequestMapping("userAddress-provider")
public class UserReceInfoController {

    @Autowired
    private WReceinfoService wReceinfoService;

    @RequestMapping("/initAddress")
    public ResultBody<?> initAddress(@RequestParam("address_id")Integer address_id){
        WatchReceinfo watchReceinfo = null;
        try {
            watchReceinfo = wReceinfoService.getById(address_id);
        } catch (XFException e) {
            e.printStackTrace();
            return new ResultBody<>(e.getErrorCode(),e.getMessage() );
        }
        return new ResultBody<>(CommonEnum.SUCCESS,JSON.toJSONString(watchReceinfo));
    }
    /**
     * 删除地址信息
     * @param info_id:地址主键id
     */
    @RequestMapping("/delAddress")
    public ResultBody<?> delAddress(@RequestParam("info_id")Integer info_id){
        Boolean aBoolean = wReceinfoService.removeById(info_id);
        return new ResultBody<>(CommonEnum.SUCCESS,aBoolean);
    }
    /**
     * 添加地址信息
     * @param str_watchReceinfo：地址对象
     */
    @RequestMapping(value="/addReceInfo")
    @ResponseBody
    public ResultBody<?> addReceInfo(
            @RequestParam("watchReceinfo") String str_watchReceinfo){
        WatchReceinfo watchReceinfo=JSON.parseObject(str_watchReceinfo,WatchReceinfo.class);
        boolean b = wReceinfoService.saveOrUpdate(watchReceinfo);
        return new ResultBody<>(CommonEnum.SUCCESS,b);
    }
    /**
     * 查找地址
     */
    @RequestMapping("/orderFindAddr")
    public ResultBody<?> findAddr(@RequestParam("user_id")Integer user_id){
        List<WatchReceinfo> info = null;
        if(user_id==null||user_id==0){
            return new ResultBody<>(CommonEnum.ERROR_EMPTY);
        }
        try {
            LambdaQueryWrapper<WatchReceinfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WatchReceinfo::getUserId,user_id);
            info = wReceinfoService.list(wrapper);
        } catch (XFException e) {
            e.printStackTrace();
            return new ResultBody<>(e.getErrorCode(),e.getMessage() );
        }
        return new ResultBody<>(CommonEnum.SUCCESS,info);
    }

    @RequestMapping("findAddressById")
    public ResultBody<?> findReceInfoById(@RequestParam("rece_id")Integer rece_id){
       return new ResultBody<>(CommonEnum.SUCCESS,wReceinfoService.getById(rece_id));
    };
}
