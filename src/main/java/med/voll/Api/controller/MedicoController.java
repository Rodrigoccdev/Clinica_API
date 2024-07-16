package med.voll.Api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.Api.domain.direccion.DatosDireccion;
import med.voll.Api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping()
    public ResponseEntity <DatosRespuestaM> registrarMedico(@RequestBody @Valid DatosMedico datosMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosMedico));
        DatosRespuestaM datosRespuestaM = new DatosRespuestaM(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaM);
    }

    @GetMapping
    public ResponseEntity <Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaM(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaM> retornaMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaM(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }

    /*public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/
}
