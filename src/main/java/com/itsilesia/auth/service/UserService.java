package com.itsilesia.auth.service;

import com.itsilesia.auth.dao.RoleDao;
import com.itsilesia.auth.dao.UserDao;
import com.itsilesia.auth.dto.UserDto;
import com.itsilesia.auth.dto.UserSaveDto;
import com.itsilesia.auth.dto.UserUpdateDto;
import com.itsilesia.auth.model.Role;
import com.itsilesia.auth.model.RoleType;
import com.itsilesia.auth.model.User;
import com.itsilesia.exceptions.NotFoundException;
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

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(user -> users.add(user));
        return users;
    }

    public User findOne(long id) {
        return userDao.findById(id).orElseThrow(() -> new NotFoundException(id, User.class));
    }

    public void delete(long id) {
        userDao.delete(userDao.findById(id)
                .orElseThrow(() -> new NotFoundException(id, User.class))
        );
    }

    public User save(UserSaveDto userSaveDto) {
        User user = new User();
        user.setEmail(userSaveDto.getEmail());
        user.setUsername(userSaveDto.getUsername());
        user.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
        mapRoles(user, userSaveDto);
        userDao.save(user);

        return user;
    }

    public User update(UserUpdateDto userUpdateDto, Long id) {
        return userDao.findById(id)
                .map(user -> {
                            user.setEmail(userUpdateDto.getEmail() != null ? userUpdateDto.getEmail() : user.getEmail());
                            user.setUsername(userUpdateDto.getUsername() != null ? userUpdateDto.getUsername() : user.getUsername());
                            user.setPassword(userUpdateDto.getPassword() != null ? userUpdateDto.getPassword() : user.getPassword());

                            if (userUpdateDto.getRole() != null) {
                                mapRoles(user, userUpdateDto);
                            }
                            return userDao.save(user);
                        }
                )
                .orElseThrow(() -> new NotFoundException(id, User.class));
    }

    private List<RoleType> mapRoles(User user, UserDto userDto) {
        List<RoleType> roleTypes = new ArrayList<>();
        userDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));
        user.setRoles(roleDao.find(userDto.getRole()));

        return roleTypes;
    }
}


