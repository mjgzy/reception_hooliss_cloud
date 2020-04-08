package com.xfkj.controller.user;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.feign.user.FeignUserReceInfoController;
import com.xfkj.pojo.order.ShoppingCart;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.user.WReceinfoService;
import com.xfkj.tools.Constants;
import com.xfkj.tools.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("userAddress")
public class UserReceInfoController {

    @Resource
    private FeignUserReceInfoController feignUserReceInfoController;

    @RequestMapping("frame/address")
    public String frameAddress(){
        return "frame/address";
    }
    /**
     * 初始化地址信息
     * @param address_id:地址主键
     */
    @RequestMapping("/initaddress.xf")
    @ResponseBody
    public ResultBody initAddress(@RequestParam("address_id")Integer address_id){
        ResultBody resultBody = feignUserReceInfoController.initAddress(address_id);
        if(resultBody.getCode().equals("200")){
            return ResultBody.success(resultBody.getResult());
        }else{
            return ResultBody.error(resultBody.getCode(),resultBody.getResult().toString());
        }
    }

    /**
     * 删除地址信息
     * @param info_id:地址主键id
     */
    @RequestMapping("/delAddress.xf")
    @ResponseBody
    public ResultBody delAddress(@RequestParam("info_id")Integer info_id){
        ResultBody resultBody = feignUserReceInfoController.delAddress(info_id);
        if (resultBody.getCode().equals("200")){
            return ResultBody.success(resultBody.getResult());
        }else{
            return ResultBody.error(resultBody.getCode(),resultBody.getMessage());
        }
    }
    /**
     * 添加地址信息
     * @param watchReceinfo：地址对象
     * @param saveType：保存类型
     */
    @RequestMapping(value="/addReceInfo.xf", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseBody
    public ResultBody addReceInfo(WatchReceinfo watchReceinfo, @RequestParam("saveType")String saveType,HttpSession session){
        log.info(watchReceinfo+"");
        log.info("1");
        Wuser wuser= (Wuser) session.getAttribute(Constants.USER_SESSION);
        watchReceinfo.setUser_id(wuser.getU_id());
        return feignUserReceInfoController.addReceInfo(JSON.toJSONString(watchReceinfo),saveType);
    }

    /**
     * 查找地址
     */
    @RequestMapping("/orderFindAddr.xf")
    @ResponseBody
    public ResultBody findAddr(HttpSession session){
       Wuser wuser= (Wuser) session.getAttribute(Constants.USER_SESSION);
     return wuser!=null?feignUserReceInfoController.findAddr(wuser.getU_id()):ResultBody.error("user is null");
    }
    /**
     * 获取所有地址对象
     */
    @RequestMapping(value = "/getAllAddress.xf")
    @ResponseBody
    public ResultBody getAddressAllByUId(HttpSession session){
        Wuser wuser= (Wuser) session.getAttribute(Constants.USER_SESSION);
        return feignUserReceInfoController.getAddressAllByUId(wuser.getU_id());
    }
}
