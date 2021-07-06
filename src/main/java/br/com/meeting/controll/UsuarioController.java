package br.com.meeting.controll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meeting.dto.UsuarioDTO;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.UsuarioRepository;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("")
	public  List<UsuarioDTO> listaTodasAcoes(){
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();		
		List<UsuarioDTO> usuariosDTO = ModelToDTO.deUsuarioParaUsuarioDTO(usuarios);
		
		return usuariosDTO;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listaReuniaoPorId(@PathVariable("id") Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
