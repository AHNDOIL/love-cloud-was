package com.lovecloud.usermanagement.domain;

import com.lovecloud.auth.domain.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//TODO : ADMIN 삭제 예정
//@Getter
//@Entity
//@Table(name = "admin")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Admin extends User {
//
//    @Column(name = "password", nullable = false, length = 100)
//    private String password;
//
//    @Builder
//    public Admin(String email, String name, UserRole userRole, String password) {
//        super(email, name, userRole);
//        this.password = password;
//    }
//
//
//}
