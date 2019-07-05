package com.itsilesia.auth.service;


import com.itsilesia.auth.dao.RoleDao;
import com.itsilesia.auth.dao.UserDao;
import com.itsilesia.auth.dto.UserDto;
import com.itsilesia.auth.model.Role;
import com.itsilesia.auth.model.RoleType;
import com.itsilesia.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userService")
public class UserService implements UserDetailsService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userDao.findByUsername(userId);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<Role> roleByUserId = user.getRoles();
        final Set<GrantedAuthority> authorities = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
        return authorities;
    }

    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(user -> users.add(user.toUserDto()));
        return users;
    }

    public User findOne(long id) {
        return userDao.findById(id).get();
    }

    public void delete(long id) {
        userDao.deleteById(id);
    }

    public User save(UserDto userDto) {
//        User userWithDuplicateUsername = userDao.findByUsername(userDto.getUsername());
//        if(userWithDuplicateUsername != null && userDto.getId() != userWithDuplicateUsername.getId()) {
//            throw new RuntimeException("Duplicate username.");
//        }
//        User userWithDuplicateEmail = userDao.findByEmail(userDto.getEmail());
//        if(userWithDuplicateEmail != null && userDto.getId() != userWithDuplicateEmail.getId()) {
//            throw new RuntimeException("Duplicate email.");
//        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        List<RoleType> roleTypes = new ArrayList<>();
        userDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));

        user.setRoles(roleDao.find(userDto.getRole()));
        userDao.save(user);
        return user;
    }
}
