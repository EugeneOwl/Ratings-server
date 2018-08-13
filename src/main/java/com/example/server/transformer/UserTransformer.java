package com.example.server.transformer;

import com.example.server.dto.UserDto;
import com.example.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserTransformer implements Transformer<User, UserDto> {

    @Autowired
    private TaskTransformer taskTransformer;

    @Override
    public User transform(final UserDto userDto) {
        if (Objects.isNull(userDto)) {

            return null;
        }
        final User user = User.builder()
                .username(userDto.getUsername())
                .mobileNumber(cleanUpMobileNumber(userDto.getMobileNumber()))
                .roles(userDto.getRoles())
                .tasks(userDto.getTasks().stream()
                        .map(taskTransformer::transform)
                        .collect(Collectors.toList()))
                .build();
        user.setId(userDto.getId());
        user.setRatingsRecipient(new ArrayList<>());
        user.setRatingsSender(new ArrayList<>());

        return user;
    }

    @Override
    public UserDto transform(final User user) {

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .mobileNumber(user.getMobileNumber())
                .roles(user.getRoles())
                .tasks(user.getTasks().stream()
                        .map(taskTransformer::transform)
                        .collect(Collectors.toList()))
                .build();
    }

    private String cleanUpMobileNumber(final String mobileNumber) {
        return mobileNumber.replaceAll("[^0-9]", "");
    }
}
