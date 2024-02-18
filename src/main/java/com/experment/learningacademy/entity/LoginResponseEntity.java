package com.experment.learningacademy.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginResponseEntity {
    private String jwtToken;
    private String username;

}
