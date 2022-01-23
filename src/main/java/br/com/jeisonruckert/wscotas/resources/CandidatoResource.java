package br.com.jeisonruckert.wscotas.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jeisonruckert.wscotas.entities.Candidato;
import br.com.jeisonruckert.wscotas.entities.Cota;
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
	
	@GetMapping
	public ResponseEntity<List<Candidato>> generateList(@RequestParam(value = "cursoId") Integer cursoId,
			@RequestParam(value = "chamadaId") Integer chamadaId) {
		List<Candidato> chamada = service.generateList(cursoId, chamadaId);
		return ResponseEntity.ok().body(chamada);
	}
	
	@GetMapping(value = "/retrieveList")
	public ResponseEntity<List<Candidato>> retrieveList(@RequestParam(value = "cursoId") Integer cursoId,
			@RequestParam(value = "chamadaId") Integer chamadaId) {
		List<Candidato> chamada = service.retrieveList(cursoId, chamadaId);
		return ResponseEntity.ok().body(chamada);
	}
	
	@PostMapping(value = "/callResultFile")
	public ResponseEntity<List<Cota>> callResultFile(@RequestBody List<Candidato> candidatos) {
		service.insertResultFile(candidatos);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/enrolledList")
	public ResponseEntity<List<Candidato>> enrolledList(@RequestParam(value = "cursoId") Integer cursoId) {
		List<Candidato> matriculados = service.enrolledList(cursoId);
		return ResponseEntity.ok().body(matriculados);
	}

}
