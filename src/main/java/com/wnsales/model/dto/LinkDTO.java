package com.wnsales.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class LinkDTO {
    private String link;

    private String status;

    public LinkDTO(String link) {
        this.link = link;
    }
}
