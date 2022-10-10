package com.wnsales.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wnsales.model.Account;
import com.wnsales.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String name;

    private String email;

    private Set<AccountDTO> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((UserDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private UserDTO(User model) {
        BeanUtils.copyProperties(model, this);
        this.accounts = AccountDTO.of(model.getAccounts());
    }

    public static UserDTO of(User source){
        if (source == null)
            return null;
        return new UserDTO(source);
    }

    public static Set<UserDTO> of(Set<User> source){
        return source.stream().map(UserDTO::new).collect(Collectors.toSet());
    }

}
