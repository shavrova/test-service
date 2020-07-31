package com.tms.api.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserResponseModel {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date updatedAt;
}
