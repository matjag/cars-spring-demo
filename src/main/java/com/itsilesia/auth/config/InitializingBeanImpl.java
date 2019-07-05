package com.itsilesia.auth.config;

import com.itsilesia.auth.dao.RoleDao;
import com.itsilesia.auth.model.Role;
import com.itsilesia.auth.model.RoleType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanImpl implements InitializingBean {


    @Autowired
    private RoleDao roleDao;

    @Override
    public void afterPropertiesSet() throws Exception{

        for(RoleType roleType : RoleType.values() ){
            Role role = new Role(roleType);
            roleDao.save(role);
        }
    }
}
