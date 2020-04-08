package com.xfkj.feign.user;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xfkj.exceptionHandling.XFException;
import com.xfkj.pojo.user.WatchReceinfo;
import com.xfkj.service.user.WReceinfoService;
import com.xfkj.tools.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@FeignClient("hooliss-user-provider")
@RequestMapping("userAddress-provider")
public interface FeignUserReceInfoController {

    @RequestMapping("findAddressById")
    ResultBody findReceInfoById(@RequestParam("rece_id")Integer rece_id);
    /**
     * 初始化地址信息
     * @param address_id:地址主键id
     */
    @RequestMapping("initaddress.xf")
    public ResultBody initAddress(@RequestParam("address_id")Integer address_id);

    /**
     * 添加地址信息
     * @param watchReceinfo:地址对象
     * @param saveType:保存类型
     */
    @RequestMapping(value="addReceInfo.xf")
    public ResultBody addReceInfo(
            @RequestParam("watchReceinfo") String watchReceinfo,
            @RequestParam("saveType")String saveType);
    @RequestMapping("/delAddress.xf")
    public ResultBody delAddress(@RequestParam("info_id")Integer info_id);

    /**
     * 获取所有地址对象
     * @param user_id:用户id
     */
    @RequestMapping(value = "getAllAddress.xf")
    public ResultBody getAddressAllByUId(@RequestParam("user_id") Integer user_id);
    /**
     * 查找地址
     */
    @RequestMapping("orderFindAddr.xf")
    public ResultBody findAddr(@RequestParam("user_id")Integer user_id);


    @RequestMapping("orderaddrde.xf/{i_id}")
    public ResultBody orderaddrde(@PathVariable("i_id")Integer i_id);
}
