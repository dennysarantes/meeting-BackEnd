package br.com.meeting.controll;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.meeting.dto.UsuarioDTO;
import br.com.meeting.model.Perfil;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.UsuarioRepository;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("")
	public  List<UsuarioDTO> listaTodosUsuarios(){
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();		
		List<UsuarioDTO> usuariosDTO = ModelToDTO.deUsuarioParaUsuarioDTO(usuarios);
		
		return usuariosDTO;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listaUsuarioPorId(@PathVariable("id") Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuarioDTO,
			UriComponentsBuilder uriBuilder) {
		
		Perfil perfil = new Perfil();
		perfil.setId(2L); //ID de perfil para usuário comum
		
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);
		
		Usuario usuario = usuarioDTO.toUsuario(usuarioDTO);
		usuario.setPerfis(perfis);
		
		try {
			usuarioRepository.save(usuario);
			URI uri = uriBuilder.path("api/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
			//usuarioDTO = atualizaDTO(reuniaoDTO, participantes, itens, reuniao.getId());
			return ResponseEntity.ok(usuarioDTO);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
