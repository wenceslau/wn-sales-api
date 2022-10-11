package com.wnsales.model;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wnsales.model.dto.AccountDTO;
import com.wnsales.model.dto.UserDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Account> accounts = Collections.EMPTY_SET;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((User) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private User(UserDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public static User of(UserDTO source){
        if (source == null)
            return null;
        return new User(source);
    }

    public static Set<User> of(Set<UserDTO> source){
        return source.stream().map(User::new).collect(Collectors.toSet());
    }

}
