package com.senati.tecnifyproyecto.auth;

import com.senati.tecnifyproyecto.mensaje.Mensaje;
import com.senati.tecnifyproyecto.mensaje.MensajeRequest;
import com.senati.tecnifyproyecto.mensaje.MensajeResponse;
import com.senati.tecnifyproyecto.mensaje.MensajeService;
import com.senati.tecnifyproyecto.ubicacion.UbicacionClienteDTO;
import com.senati.tecnifyproyecto.ubicacion.UbicacionDTO;
import com.senati.tecnifyproyecto.ubicacion.UbicacionService;
import com.senati.tecnifyproyecto.user.UpdateTecnicoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/tecnicos")
@RequiredArgsConstructor  //obligatorio todos los contrusctores con argumentos
public class TecnicoController {


    private final MensajeService mensajeService;
    private final UbicacionService ubicacionService;

    //responseentity hace la respuesta http codigo de estado, encabezado y cuerpo de respuesta
    @GetMapping(value="ubicacionesCli")
    public ResponseEntity<List<UbicacionClienteDTO>> obtenerUbicacionesClientes() {
        List<UbicacionClienteDTO> ubicaciones = ubicacionService.obtenerUbicacionesClientes();
        return ResponseEntity.ok(ubicaciones);
    }

    @PatchMapping(value="enviarUbicacion")
    public ResponseEntity<String> actualizarUbicacion(@RequestBody UbicacionDTO ubicacionDTO) {
        // Obtener el correo desde el contexto de seguridad
        String correo = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Llamar al servicio para actualizar la ubicación
        ubicacionService.actualizarUbicacion(correo, ubicacionDTO.getLatitud(), ubicacionDTO.getLongitud());

        // Retornar respuesta exitosa
        return ResponseEntity.ok("Ubicación actualizada exitosamente");
    }


    //enviar Recibir mensaje
    @PostMapping(value="enviarMensaje")
    public ResponseEntity<Mensaje> register(@RequestBody MensajeRequest request){

        mensajeService.saveMensaje(request);

        return ResponseEntity.ok().build();
    }


    @GetMapping(value="recibirMensaje")
    public ResponseEntity<List<MensajeResponse>> obtenerMensajes() {
        List<MensajeResponse> mensajes = mensajeService.obtenerMensajesPorReceptor();

        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si no hay mensajes
        }

        return ResponseEntity.ok(mensajes); // Devolver los mensajes
    }

    @PatchMapping("actualizarInf")
    public ResponseEntity<Void> actualizarTecnico(@RequestBody UpdateTecnicoDTO updateTecnicoDTO) {
        ubicacionService.actualizarTecnico(updateTecnicoDTO);
        return ResponseEntity.noContent().build(); // Respuesta sin cuerpo (204)
    }
}