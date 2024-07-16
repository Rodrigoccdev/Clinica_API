package med.voll.Api.controller;

import jakarta.validation.Valid;
import med.voll.Api.domain.usuarios.DatosAutenticacion;
import med.voll.Api.domain.usuarios.Usuario;
import med.voll.Api.infra.security.DatosJwtToken;
import med.voll.Api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionLogin {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacion datosAutenticacion){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacion.login(),
                datosAutenticacion.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwtToken(jwtToken));
    }
}
