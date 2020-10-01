package com.xfkj.entity;

import com.xfkj.entity.user.Wuser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Wuser implements UserDetails {

    private List<GrantedAuthority> auths;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auths;
    }

    @Override
    public String getPassword() {
        return this.getUPwd();
    }

    @Override
    public String getUsername() {
        return this.getUName();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.getStatus()==-1){
            return false;
        }else{
            return true;
        }
    }

    //帐户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        if (this.getStatus()==1){
            return true;
        }
        return false;
    }

    //凭据是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
