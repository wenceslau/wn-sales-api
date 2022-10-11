package com.wnsales.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wnsales.model.User;
import com.wnsales.model.dto.UserDTO;
import com.wnsales.service.UserService;
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
public class UserController  {

    private final UserService userService;

    @GetMapping("/template")
    public UserDTO template(){
        return new UserDTO();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return UserDTO.of(userService.findById(id).get());
    }

    @GetMapping()
    public Page<UserDTO> list(@PageableDefault Pageable pageable) {
        return UserDTO.of(userService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Validated UserDTO dto) {
        User user = userService.save(User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> edit(@PathVariable Long id, @RequestBody @Validated UserDTO dto) {
        User user = userService.edit(id, User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialEdit(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = userService.partialEdit(id, User.of(dto));

        return new ResponseEntity<>(UserDTO.of(user), HttpStatus.OK);
    }
}
