package com.dev.backend.entity;

import com.dev.backend.validator.unique.Unique;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username cannot be empty")
    @Unique(fieldName = "username", message = "username already exists")
    @Size(min = 10, max = 25, message = "username must be between {min} and {max} characters")
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @NotBlank(message = "email cannot be empty")
    @Unique(fieldName = "email", message = "email already exists")
    @Email
    @Size(min = 10, max = 30, message = "email must be between {min} and {max} characters")
    @Column(name = "email", unique = true)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.REMOVE,
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.REMOVE,
                fetch = FetchType.EAGER)
    @JoinTable(name = "user_follow",
               joinColumns = @JoinColumn(name = "following", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "follower", referencedColumnName = "id"))
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
