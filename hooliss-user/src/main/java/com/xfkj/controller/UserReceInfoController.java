package com.xfkj.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.user.WReceinfoService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("userAddress-provider")
public class UserReceInfoController {

    @Autowired
    private WReceinfoService wReceinfoService;

    @Autowired
    private ObjectMapper objectMapper;
    @RequestMapping("/initaddress.xf")
    public ResultBody initAddress(@RequestParam("address_id")Integer address_id){
        WatchReceinfo watchReceinfo = null;
        try {
            watchReceinfo = wReceinfoService.queryAddressById(address_id);
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage() );
        }
        return ResultBody.success(JSON.toJSONString(watchReceinfo));
    }
    /**
     * 删除地址信息
     * @param info_id:地址主键id
     */
    @RequestMapping("/delAddress.xf")
    public ResultBody delAddress(@RequestParam("info_id")Integer info_id){
        Boolean aBoolean = wReceinfoService.del(info_id);
        return ResultBody.success(aBoolean);
    }
    /**
     * 添加地址信息
     * @param str_watchReceinfo：地址对象
     * @param saveType：保存类型
     */
    @RequestMapping(value="/addReceInfo.xf")
    @ResponseBody
    public ResultBody addReceInfo(
            @RequestParam("watchReceinfo") String str_watchReceinfo,
            @RequestParam("saveType")String saveType){
        WatchReceinfo watchReceinfo=JSON.parseObject(str_watchReceinfo,WatchReceinfo.class);
        String[] reces = watchReceinfo.getProvince().split("-");
        watchReceinfo.setProvince(reces[0]);
        watchReceinfo.setCity(reces[1]);
        watchReceinfo.setArea(reces[2]);
        Boolean add = null;
        if (saveType.equals("save")){
            add= wReceinfoService.add(watchReceinfo);//添加购物车信息
        }else if (saveType.equals("modify")){
            add = wReceinfoService.modify(watchReceinfo);
        }
        return ResultBody.success(add);
    }
    /**
     * 查找地址
     */
    @RequestMapping("/orderFindAddr.xf")
    public ResultBody findAddr(@RequestParam("user_id")Integer user_id){
        PageInfo<WatchReceinfo> info = null;
        try {
            info = wReceinfoService.queryAddressById(user_id,0,0);
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage() );
        }
        List<WatchReceinfo> list  = info.getList();
        return ResultBody.success(list);
    }


    @RequestMapping("/orderaddrde.xf/{i_id}")
    public ResultBody orderaddrde(@PathVariable("i_id")Integer i_id){
        Boolean bool  = null;
        try {
            bool = wReceinfoService.del(i_id);
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage() );
        }
        return ResultBody.success(bool);
    }
    /**
     * 获取所有地址对象
     * @param user_id:用户id
     */
    @RequestMapping(value = "/getAllAddress.xf")
    public ResultBody getAddressAllByUId(@RequestParam("user_id") Integer user_id){
        PageInfo<WatchReceinfo> watchReceinfoPageInfo = null;
        try {
            watchReceinfoPageInfo = wReceinfoService.queryAddressById(user_id, 0, 0);
        } catch (XFException e) {
            e.printStackTrace();
            return ResultBody.error(e.getErrorCode(),e.getMessage());
        }
        return ResultBody.success(watchReceinfoPageInfo.getList());
    }
    @RequestMapping("findAddressById")
    public ResultBody findReceInfoById(@RequestParam("rece_id")Integer rece_id){
       return ResultBody.success(wReceinfoService.queryAddressById(rece_id));
    };
}
