package com.example.demo.domain.member;

import lombok.Data;

@Data
public class Member {

    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String birth;

    public Member() {
    }

    public Member(String id, String password, String name, String email, String address, String gender, String birth) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
    }
}
