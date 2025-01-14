package com.gestion.empleados.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.exceptions.ResourceNotFoundException;
import com.gestion.empleados.modelo.Empleado;
import com.gestion.empleados.repository.EmpleadoRepository;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@GetMapping("/empleados")
	public List<Empleado> getAllEmpleados(){
		return empleadoRepository.findAll();
	}
	
	@PostMapping("/empleados")
	public Empleado createEmpleado(@RequestBody Empleado empleado){
		return empleadoRepository.save(empleado);
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id){
		Empleado empleado = empleadoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado de id" + id));
		return ResponseEntity.ok(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado detallesEmpleado){
		Empleado empleado = empleadoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado de id" + id));
		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		empleado.setEmail(detallesEmpleado.getEmail());
		Empleado empleadoActualizado = empleadoRepository.save(empleado); 	
		
		return ResponseEntity.ok(empleadoActualizado);
	}
	@DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmpleado(@PathVariable Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

        empleadoRepository.delete(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminar", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
