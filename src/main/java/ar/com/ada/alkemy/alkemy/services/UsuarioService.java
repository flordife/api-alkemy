package ar.com.ada.alkemy.alkemy.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import ar.com.ada.alkemy.alkemy.entities.Usuario;
import ar.com.ada.alkemy.alkemy.repos.UsuarioRepository;
import ar.com.ada.alkemy.alkemy.security.Crypto;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario login(String username, String password) {

        /**
         * Metodo IniciarSesion recibe usuario y contraseña validar usuario y contraseña
         */

        Usuario u = buscarPorUsername(username);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

        return u;
    }

    public Usuario crearUsuario(String email, String password, String fullName, Date fechaLogin){

        Usuario usuario = new Usuario();
        usuario.setFechaLogin(new Date());
        usuario.setNombre(fullName);
        usuario.setUsername(email);
        usuario.setEmail(email);
        usuario.setPassword(Crypto.encrypt(password, email.toLowerCase()));
        usuarioRepository.save(usuario);

        return usuario;
    }

    public Usuario buscarPorEmail(String email) {

        return usuarioRepository.findByEmail(email);
    }

    public Usuario buscarPor(Integer id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isPresent()) {
            return usuarioOp.get();
        }

        return null;
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
    
        claims.put("userType", usuario.getUsuarioId()); ///
    
        //if (usuario.getPersonaId() != null)
        // claims.put("userType", usuario.getPersonaId());
       
        return claims;
      }

    public UserDetails getUserAsUserDetail(Usuario usuario) {
        UserDetails uDetails;

        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

        return uDetails;
    }

    // Usamos el tipo de datos SET solo para usar otro diferente a List private
    Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        Integer userType = usuario.getUsuarioId();
    
        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));
    
       
        return authorities;
      }

}
