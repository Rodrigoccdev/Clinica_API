package med.voll.Api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.Api.domain.direccion.DatosDireccion;

public record DatosActualizarMedico (@NotNull Long id, String nombre, String documento, DatosDireccion direccion){
}
