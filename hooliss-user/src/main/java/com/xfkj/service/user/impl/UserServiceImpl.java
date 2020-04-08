package com.xfkj.service.user.impl;

import com.xfkj.exceptionHandling.XFException;
import com.xfkj.mapper.user.WuserMapper;
import com.xfkj.pojo.user.Wuser;
import com.xfkj.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Resource
    private WuserMapper wuserMapper;
    @Override
    public Wuser validateLogin(String name, String pwd) {


        if(wuserMapper.count(name)>0){
            Wuser wuser=wuserMapper.validateLogin(name,pwd);
            return wuser;
        }else{
            return null;
        }

    }

    @Override
    public boolean register(Wuser register) {
        register.setVip_id(1);      //设置为普通会员
        if(wuserMapper.register(register)>0){
            return  true;
        }else{
            return false;
        }
    }

    @Override
    public Integer findIdByName(String u_name) {
        int id=wuserMapper.findIdByName(u_name);
        return  id;
    }

    @Override
    public boolean contains(String name) {
        int num=wuserMapper.contains(name);
        if(num==1){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Wuser findUserByPhone(String phone) {
        if (phone!=null&&!phone.equals("")){
            return wuserMapper.findUserByPhone(phone);
        }
        return null;
    }

    @Override
    public boolean modify(Wuser wuser) {
        int num=wuserMapper.modify(wuser);
        try {
            if(num==1) {
                return true;
            }
             } catch (Exception e) {
                 throw new RuntimeException();
            }
                return  false;
            }

    @Override
    public boolean count(String u_name) throws XFException{
        if (ObjectUtils.isEmpty(u_name)){
            throw new XFException("400",u_name+" is null!" );
        }
        Integer count = wuserMapper.count(u_name);
        if(wuserMapper.count(u_name)==0){
            return false;
        }else{
            return true;
        }

    }

    //手机号唯一性验证
    @Override
    public Boolean phoneOnlyVa(String phone) throws XFException{
        if (ObjectUtils.isEmpty(phone)){
            throw new XFException("400",phone+" is null!" );
        }
        Integer integer = wuserMapper.phoneOnlyVa(phone);
        return integer==0;
}

    @Override
    public Wuser findById(Integer u_id) {
        Wuser byId = wuserMapper.findById(u_id);
        return byId;
    }

    @Override
    public Integer addUserIntegral(Integer user_id, Integer integral) throws XFException {
        if (user_id==null||user_id==0){
            throw new XFException("500", "参数异常,user_id is null");
        }else if (integral==null||integral==0){
            throw new XFException("500", "参数异常,integral is null");
        }
        Integer integer = wuserMapper.addUserIntegral(user_id, integral);
        return integer;
    }
}
