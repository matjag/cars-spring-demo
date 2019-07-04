package com.itsilesia.auth.model;


import com.itsilesia.auth.dto.UserDto;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    public UserDto toUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setEmail(this.email);
        userDto.setUsername(this.username);
        userDto.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
        return userDto;
    }
}
