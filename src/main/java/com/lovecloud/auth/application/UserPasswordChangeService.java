package com.lovecloud.auth.application;

import com.lovecloud.auth.domain.Password;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.usermanagement.domain.User;
import com.lovecloud.usermanagement.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPasswordChangeService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public void changePassword(final User user, final String newPassword){
        Password encryptedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encryptedPassword);
        userRepository.save(user);
    }
}
