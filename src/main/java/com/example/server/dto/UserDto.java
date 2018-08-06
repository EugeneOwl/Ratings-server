package com.example.server.dto;

import com.example.server.model.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Dto {
    private int id;
    private String username;
    private String password;
    private String mobileNumber;
    private Set<Role> roles = new HashSet<>();
}
