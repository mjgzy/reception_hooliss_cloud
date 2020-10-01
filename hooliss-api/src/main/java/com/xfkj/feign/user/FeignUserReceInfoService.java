package com.xfkj.feign.user;

import com.xfkj.tools.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hooliss-user-provider",contextId = "userReceInfo")
//@RequestMapping("userAddress-provider")
public interface FeignUserReceInfoService {

    @RequestMapping("userAddress-provider/findAddressById")
    ResultBody findReceInfoById(@RequestParam("rece_id")Integer rece_id);
    /**
     * 初始化地址信息
     * @param address_id:地址主键id
     */
    @RequestMapping("userAddress-provider/initaddress.xf")
    public ResultBody initAddress(@RequestParam("address_id")Integer address_id);

    /**
     * 添加地址信息
     * @param watchReceinfo:地址对象
     * @param saveType:保存类型
     */
    @RequestMapping(value="userAddress-provider/addReceInfo.xf")
    public ResultBody addReceInfo(
            @RequestParam("watchReceinfo") String watchReceinfo,
            @RequestParam("saveType")String saveType);
    @RequestMapping("/delAddress.xf")
    public ResultBody delAddress(@RequestParam("info_id")Integer info_id);

    /**
     * 获取所有地址对象
     * @param user_id:用户id
     */
    @RequestMapping(value = "userAddress-provider/getAllAddress.xf")
    public ResultBody getAddressAllByUId(@RequestParam("user_id") Integer user_id);
    /**
     * 查找地址
     */
    @RequestMapping("userAddress-provider/orderFindAddr.xf")
    public ResultBody findAddr(@RequestParam("user_id")Integer user_id);


    @RequestMapping("userAddress-provider/orderaddrde.xf/{i_id}")
    public ResultBody orderaddrde(@PathVariable("i_id")Integer i_id);
}
