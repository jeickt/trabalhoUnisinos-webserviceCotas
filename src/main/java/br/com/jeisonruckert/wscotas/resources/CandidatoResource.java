package br.com.jeisonruckert.wscotas.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jeisonruckert.wscotas.entities.Candidato;
import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.services.CandidatoService;

@RestController
@RequestMapping(value = "/candidatos")
public class CandidatoResource {
	
	private final CandidatoService service;
	
	public CandidatoResource(CandidatoService service) {
		this.service = service;
	}
	
	@PostMapping(value = "/mainFile")
	public ResponseEntity<Curso> insertFile(@RequestBody byte[] csvFile) {
		Curso curso = service.insertMainFile(csvFile);
		return ResponseEntity.ok().body(curso);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<Candidato>> generateList(@PathVariable Integer id) {
		List<Candidato> chamada = service.generateList(id);
		return ResponseEntity.ok().body(chamada);
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<Void> callResultFile(@RequestBody byte[] csvFile, @PathVariable Integer id) {
		service.insertResultFile(csvFile, id);
		return ResponseEntity.noContent().build();
	}

}
