package edu.whu.homework8.security;

import edu.whu.homework8.entity.Role;
import edu.whu.homework8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        edu.whu.homework8.entity.User user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            for (String auth : role.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }
        return User.builder()
                .username(username)
                //注：正式项目最好在数据库用密文存储密码
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(authorities.toArray(new GrantedAuthority[authorities.size()]))
                .build();
    }


}
