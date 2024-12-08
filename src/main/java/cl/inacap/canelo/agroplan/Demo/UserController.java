package cl.inacap.canelo.agroplan.Demo;

import cl.inacap.canelo.agroplan.User.User;
import cl.inacap.canelo.agroplan.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección de PasswordEncoder

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value="/demo")
    public String welcome(){
        return "Bienvenido al endpoint de usuario";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/perfil")
    public User getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/perfil")
    public String updateUserProfile(@RequestBody User updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Encriptar contraseña
        userRepository.save(existingUser);
        return "Perfil actualizado exitosamente!";
    }
}
