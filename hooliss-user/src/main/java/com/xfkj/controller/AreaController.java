package com.xfkj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xfkj.entity.user.Area;
import com.xfkj.enums.CommonEnum;
import com.xfkj.pojo.vo.user.AreaVo;
import com.xfkj.service.AreaService;
import com.xfkj.tools.ResultBody;
import org.jasypt.commons.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("hoolissArea")
public class AreaController {

    @Autowired
    private AreaService areaService;
    @GetMapping("getAreasByParenntId")
    public ResultBody<List<AreaVo>> getAreasByParenntId(Long parentId){
        try {
            LambdaQueryWrapper<Area> wrapper = new LambdaQueryWrapper<>();
            List<AreaVo> vos = new ArrayList<>();
            if(parentId!=null&&parentId!=0){
                wrapper.eq(Area::getParentId,parentId);
            }else{
                wrapper.isNull(Area::getParentId).or().eq(Area::getParentId,0);
            }
            List<Area> list = areaService.list(wrapper);
            list.forEach(item->{
                vos.add(BeanUtil.toBean(item,AreaVo.class));
            });
            return new ResultBody<>(CommonEnum.SUCCESS,vos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody<>(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
