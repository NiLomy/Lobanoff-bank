package ru.kpfu.itis.lobanov.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.VERIFICATION_CODE_LENGTH;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Passport passport;

    @Email
    @NotNull
    @Column(unique = true)
    @NotNull(message = EMAIL_NOT_NULL)
    @NotBlank(message = EMAIL_NOT_BLANK)
    private String email;

    private String phone;

    @NotNull
    @NotNull(message = PASSWORD_NOT_NULL)
    @NotBlank(message = PASSWORD_NOT_BLANK)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @NotNull(message = DELETED_NOT_NULL)
    @ColumnDefault("false")
    private Boolean deleted;

    @Column(name = "verification_code", length = VERIFICATION_CODE_LENGTH)
    private String verificationCode;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
        return Collections.singleton(authority);
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
        return state.equals(State.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return state.equals(State.ACTIVE);
    }
}
