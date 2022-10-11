package com.wnsales.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wnsales.model.dto.AccountDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "IBAN")
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Account) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Builder
    private Account(AccountDTO dto) {
        BeanUtils.copyProperties(dto, this);
        if (dto.getUserId() != null){
            this.user = new User();
            this.user.setId(dto.getUserId());
        }
    }

    public static Account of(AccountDTO source){
        if (source == null)
            return null;
        return new Account(source);
    }

    public static Set<Account> of(Set<AccountDTO> source){
        return source.stream().map(Account::new).collect(Collectors.toSet());
    }
}
