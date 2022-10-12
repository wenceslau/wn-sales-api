package com.wnsales.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wnsales.model.User;
import com.wnsales.model.dto.UserDTO;
import com.wnsales.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
@Tag(name = "User API - List/Create/Update")
public class UserController  {

    private final UserService userService;

    @GetMapping("/template")
    @Operation(summary = "Template JSON for resource create")
    public UserDTO template(){
        return new UserDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find User resource by ID")
    public UserDTO findById(@PathVariable Long id) {
        return UserDTO.of(userService.findById(id).get());
    }

    @GetMapping()
    @Operation(summary = "User list pageable")
    public Page<UserDTO> list(@PageableDefault Pageable pageable) {
        return UserDTO.of(userService.findAll(pageable));
    }

    @PostMapping
    @Operation(summary = "Save an User resource")
    public ResponseEntity<UserDTO> save(@RequestBody @Validated UserDTO dto) {
        User user = userService.save(User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit an User resource")
    public ResponseEntity<UserDTO> edit(@PathVariable Long id, @RequestBody @Validated UserDTO dto) {
        User user = userService.edit(id, User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Edit partially User resource")
    public ResponseEntity<UserDTO> partialEdit(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = userService.partialEdit(id, User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete User resource")
    public void deleteByCode(@PathVariable Long id) {
        userService.delete(id);
    }
}
