// package com.example.springapp.service;

// import com.example.springapp.model.Role;
// import com.example.springapp.model.User;
// import com.example.springapp.repository.UserRepository;
// import com.example.springapp.security.JwtService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class UserService {

//     private final UserRepository userRepository;
//     private final JwtService jwtService;
//     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     public UserService(UserRepository userRepository, JwtService jwtService) {
//         this.userRepository = userRepository;
//         this.jwtService = jwtService;
//     }

//     public User registerUser(User user, String token) {
//         // Extract role of requester from JWT
//         String requesterRole = jwtService.extractRole(token.substring(7));

//         // PATIENT creation allowed only by DOCTOR or ADMIN
//         if (user.getRole() == Role.PATIENT) {
//             if (!(requesterRole.equals("DOCTOR") || requesterRole.equals("ADMIN"))) {
//                 throw new RuntimeException("Only DOCTOR or ADMIN can create patients");
//             }
//         }

//         // DOCTOR, INSURER, ADMIN creation allowed only by ADMIN or GOVT
//         if (user.getRole() == Role.DOCTOR ||
//             user.getRole() == Role.INSURER ||
//             user.getRole() == Role.ADMIN) {
//             if (!(requesterRole.equals("ADMIN") || requesterRole.equals("GOVT"))) {
//                 throw new RuntimeException("Only ADMIN or GOVT can create " + user.getRole());
//             }
//         }

//         // GOVT creation → Only GOVT itself
//         if (user.getRole() == Role.GOVT && !requesterRole.equals("GOVT")) {
//             throw new RuntimeException("Only GOVT can create GOVT users");
//         }

//         // Encode password and save
//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return userRepository.save(user);
//     }
// }
package com.example.springapp.service;

import com.example.springapp.model.Role;
import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User registerUser(User user, String token) {
        String requesterRole = null;

        // ✅ Extract role only if token is provided
        if (token != null && token.startsWith("Bearer ")) {
            requesterRole = jwtService.extractRole(token.substring(7));
        }

        // GOVT creation → Only GOVT itself (bootstrap exception)
        if (user.getRole() == Role.GOVT) {
            // ✅ Bootstrap case: allow if no GOVT exists yet
            if (userRepository.findByRole(Role.GOVT).isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
            if (!(requesterRole != null && requesterRole.equals("GOVT"))) {
                throw new RuntimeException("Only GOVT can create GOVT users");
            }
        }

        // PATIENT creation allowed only by DOCTOR or ADMIN
        if (user.getRole() == Role.PATIENT) {
            if (!(requesterRole != null &&
                    (requesterRole.equals("DOCTOR") || requesterRole.equals("ADMIN")))) {
                throw new RuntimeException("Only DOCTOR or ADMIN can create patients");
            }
        }

        // DOCTOR, INSURER, ADMIN creation allowed only by ADMIN or GOVT
        if (user.getRole() == Role.DOCTOR ||
            user.getRole() == Role.INSURER ||
            user.getRole() == Role.ADMIN) {
            if (!(requesterRole != null &&
                    (requesterRole.equals("ADMIN") || requesterRole.equals("GOVT")))) {
                throw new RuntimeException("Only ADMIN or GOVT can create " + user.getRole());
            }
        }

        // ✅ Encode password and save
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
