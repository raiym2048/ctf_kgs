package com.htb_kg.ctf.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.htb_kg.ctf.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users_table")
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Boolean toBusiness;

    private String businessFileSentDate;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData accessToBusiness;

    private Integer checkCode;
    private Integer refreshCode;

    private String creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData logo_image;

    private String email;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Teacher teacher;

    @OneToOne(cascade = CascadeType.ALL)
    private Hacker hacker;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_DEFAULT"));
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
