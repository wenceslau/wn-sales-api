package com.wnsales.model.dto;

import com.wnsales.model.Account;
import com.wnsales.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private UUID id;

    private String accountName;

    private String iban;

    private UserDTO user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((AccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private AccountDTO(Account model) {
        BeanUtils.copyProperties(model, this);
        this.user = UserDTO.of(model.getUser());
    }

    public static AccountDTO of(Account source){
        if (source == null)
            return null;
        return new AccountDTO(source);
    }

    public static Set<AccountDTO> of(Set<Account> source){
        return source.stream().map(AccountDTO::new).collect(Collectors.toSet());
    }
}
