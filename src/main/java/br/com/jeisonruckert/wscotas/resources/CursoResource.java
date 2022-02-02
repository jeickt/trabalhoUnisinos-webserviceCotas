package br.com.jeisonruckert.wscotas.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.services.CursoService;

@RestController
@RequestMapping(value = "/cursos")
@CrossOrigin(origins = "http://localhost:4200")
public class CursoResource {
	
	private final CursoService service;
	
	public CursoResource(CursoService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Curso>> findAll() {
		List<Curso> cursos = service.findAll();
		return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Curso> findById(@PathVariable Integer id) {
		Curso curso = service.findById(id);
		return ResponseEntity.ok().body(curso);
	}
	
}
