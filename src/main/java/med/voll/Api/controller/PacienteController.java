package med.voll.Api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.Api.domain.paciente.DatosListadoPaciente;
import med.voll.Api.domain.paciente.DatosPaciente;
import med.voll.Api.domain.paciente.Paciente;
import med.voll.Api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DatosPaciente datos) {
        repository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DatosListadoPaciente> listar(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
        return repository.findAll(paginacion).map(DatosListadoPaciente::new);
    }

    @PutMapping
    public void actualizarPaciente(){

    }
}
