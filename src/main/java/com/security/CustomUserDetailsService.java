package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String userQuery = "SELECT email, password, enabled FROM users WHERE email = ?";
        List<Map<String, Object>> userRows = jdbcTemplate.queryForList(userQuery, email);

        if (userRows.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Map<String, Object> userMap = userRows.get(0);
        String password = (String) userMap.get("password");
        Boolean enabled = (Boolean) userMap.get("enabled");

        if (enabled == null || !enabled) {
            throw new UsernameNotFoundException("User is not enabled");
        }

        List<GrantedAuthority> authorities = getAuthorities(email);

        return new User(email, password, enabled, true, true, true, authorities);
    }

    private List<GrantedAuthority> getAuthorities(String email) {
        String roleQuery = "SELECT role FROM users WHERE email = ?";
        List<String> roles = jdbcTemplate.queryForList(roleQuery, String.class, email);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
