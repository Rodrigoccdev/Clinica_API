package med.voll.Api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.Api.domain.direccion.Direccion;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;
    private boolean activo;

    public Medico(DatosMedico datosMedico){
        this.nombre = datosMedico.nombre();
        this.email = datosMedico.email();
        this.telefono = datosMedico.telefono();
        this.documento = datosMedico.documento();
        this.especialidad = datosMedico.especialidad();
        this.direccion = new Direccion(datosMedico.direccion());
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if(datosActualizarMedico.nombre() != null)
            this.nombre = datosActualizarMedico.nombre();
        if(datosActualizarMedico.documento() != null)
            this.documento = datosActualizarMedico.documento();
        if (datosActualizarMedico.direccion() != null)
            this.direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());

    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
