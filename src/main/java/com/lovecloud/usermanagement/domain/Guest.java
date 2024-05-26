package com.lovecloud.usermanagement.domain;

import com.lovecloud.auth.domain.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest extends User {

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Embedded
    private Password password;

    @Builder
    public Guest(String email, String name, UserRole userRole, String phoneNumber,
            Password password) {
        super(email, name, userRole);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
