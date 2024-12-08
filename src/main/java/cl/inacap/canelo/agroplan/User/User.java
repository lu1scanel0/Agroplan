package cl.inacap.canelo.agroplan.User; // Declaración del paquete

import java.util.Collection; // Importando Collection para manejar colecciones de datos
import java.util.List; // Importando List para manejar listas de datos

import org.springframework.security.core.GrantedAuthority; // Importando GrantedAuthority para manejar autoridades de seguridad
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Importando SimpleGrantedAuthority para manejar autoridades simples
import org.springframework.security.core.userdetails.UserDetails; // Importando UserDetails para obtener detalles del usuario

import jakarta.persistence.Basic; // Importando Basic para anotaciones básicas de JPA
import jakarta.persistence.Column; // Importando Column para anotaciones de columnas de JPA
import jakarta.persistence.Entity; // Importando Entity para anotaciones de entidades de JPA
import jakarta.persistence.EnumType; // Importando EnumType para anotaciones de tipos enumerados de JPA
import jakarta.persistence.Enumerated; // Importando Enumerated para anotaciones de enumeraciones de JPA
import jakarta.persistence.GeneratedValue; // Importando GeneratedValue para anotaciones de valores generados de JPA
import jakarta.persistence.Id; // Importando Id para anotaciones de identificadores de JPA
import jakarta.persistence.Table; // Importando Table para anotaciones de tablas de JPA
import jakarta.persistence.UniqueConstraint; // Importando UniqueConstraint para anotaciones de restricciones únicas de JPA

import lombok.AllArgsConstructor; // Importando AllArgsConstructor para generar un constructor con todos los argumentos
import lombok.Builder; // Importando Builder para el patrón de diseño Builder
import lombok.Data; // Importando Data para generar getters, setters y otros métodos útiles
import lombok.NoArgsConstructor; // Importando NoArgsConstructor para generar un constructor sin argumentos

@Data // Anotación para generar getters, setters, toString, equals y hashCode
@Builder // Anotación para implementar el patrón de diseño Builder
@NoArgsConstructor // Anotación para generar un constructor sin argumentos
@AllArgsConstructor // Anotación para generar un constructor con todos los argumentos
@Entity // Anotación para marcar esta clase como una entidad de JPA
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}) // Anotación para definir la tabla y las restricciones únicas
public class User implements UserDetails { // Declaración de la clase User que implementa UserDetails
    @Id // Anotación para marcar este campo como el identificador
    @GeneratedValue // Anotación para generar automáticamente el valor del identificador
    Integer id; // Campo para almacenar el identificador del usuario
    @Basic // Anotación básica de JPA
    @Column(nullable = false) // Anotación para definir la columna y establecer que no puede ser nula
    String username; // Campo para almacenar el nombre de usuario
    @Column(nullable = false) // Anotación para definir la columna y establecer que no puede ser nula
    String lastname; // Campo para almacenar el apellido
    String firstname; // Campo para almacenar el primer nombre
    String country; // Campo para almacenar el país
    String password; // Campo para almacenar la contraseña
    @Enumerated(EnumType.STRING) // Anotación para definir que este campo es una enumeración y se almacenará como una cadena
    Role role; // Campo para almacenar el rol del usuario

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); // Devuelve una lista de autoridades del usuario
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // Indica que la cuenta no ha expirado
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; // Indica que la cuenta no está bloqueada
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indica que las credenciales no han expirado
    }
    @Override
    public boolean isEnabled() {
        return true; // Indica que la cuenta está habilitada
    }
}