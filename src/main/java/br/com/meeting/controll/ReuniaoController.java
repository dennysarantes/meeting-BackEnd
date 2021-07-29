package br.com.meeting.controll;

import java.net.URI;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.ReuniaoRepository;
import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.service.GuardaTokenService;

@RestController
@RequestMapping("api/reuniao")
public class ReuniaoController {
	
	@Autowired
	private GuardaTokenService guardaTokenService;
	
	@Autowired
	private ReuniaoRepository reuniaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("")
	public  List<ReuniaoDTO> listaTodasReunioes(){
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAll();		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	@GetMapping("/user")	
	public List<ReuniaoDTO> listaReunioesDoUsuario(){
		
		System.out.println("Extraindo valor no controller");
		System.out.println("Email no controller: " + guardaTokenService.getToken().getEmail());
		
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserID(user.getId());		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	
	@GetMapping("/user-yet-prox")	
	public List<ReuniaoDTO> listaProximasReunioesDoUsuario(){
		
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("nome do usuario: " + user.getUsername());
		System.out.println("id do usuario: " + user.getId());
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserIDMaiorQueHoje(user.getId(), LocalDate.now(), Time.valueOf(LocalTime.now())) ;		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	@GetMapping("/proximasnouser")	
	public List<ReuniaoDTO> listaProximasReunioesNaoUsuario(){
		
		System.out.println("Valor do username no token do não usuário: "  + guardaTokenService.getToken().getUsername());
		
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		List<Reuniao> reunioesList = reuniaoRepository.findIdsReunioesByUserMaiorQueHoje(user.getId(), LocalDate.now(), Time.valueOf(LocalTime.now()));
		
		List<Long> soIdsReunioes = new ArrayList<Long>(); 
		
		soIdsReunioes.add(0L);
		
		for (Reuniao reuniao :  reunioesList) {
			soIdsReunioes.add(reuniao.getId());
		}
		
		for (Long id : soIdsReunioes ) {
			System.out.println("XXXxxxIDS REUNIAOXXXXXX");
			System.out.println(id);
		}
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllProximasByNotUserID(user.getId(), LocalDate.now(), soIdsReunioes);		
		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		//List<ReuniaoDTO> reunioesDTO = new ArrayList<ReuniaoDTO>();
		
		return reunioesDTO;
	}
	

	@GetMapping("/user-yet-antigas")	
	public List<ReuniaoDTO> listaAntigasReunioesDoUsuario(){
		
		System.out.println("Extraindo valor no controller");
		System.out.println("Email no controller: " + guardaTokenService.getToken().getEmail());
		
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserIDMenorQueHoje(user.getId(), LocalDate.now() ) ;		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ReuniaoDTO> listaReuniaoPorId(@PathVariable("id") Long id) {
		
		List<Reuniao> reunioesEncontradas = new ArrayList<Reuniao>();
		
		
		
		try {
			reunioesEncontradas.add(reuniaoRepository.findById(id).get());
			List<ReuniaoDTO> reuniao = ModelToDTO.deReuniaoParaReuniaoDTO(reunioesEncontradas);
			return ResponseEntity.ok(reuniao.get(0));
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ReuniaoDTO> cadastrar(@RequestBody ReuniaoDTO reuniaoDTO,
			UriComponentsBuilder uriBuilder) {
					
		List<Usuario> participantes = usuarioRepository.findAllById(reuniaoDTO.getParticipantes());
		List<Item> itens = itemRepository.findAllById(reuniaoDTO.getItens());
		
		
		Reuniao reuniao = new Reuniao(reuniaoDTO, participantes, itens);
		
		try {
			reuniaoRepository.save(reuniao);
			URI uri = uriBuilder.path("api/reuniao/{id}").buildAndExpand(reuniao.getId()).toUri();
			reuniaoDTO = atualizaDTO (reuniaoDTO, participantes, itens, reuniao.getId());
			return ResponseEntity.created(uri).body(reuniaoDTO);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}


	}

	private ReuniaoDTO atualizaDTO(ReuniaoDTO reuniaoDTO, List<Usuario> participantes, List<Item> itens, Long idReuniao) {
		
		List<Long> idsParticipantes = new ArrayList<Long>();
		List<Long> idsItens = new ArrayList<Long>();
		
		
		participantes.forEach(p -> {
			idsParticipantes.add(p.getId());
		});
		
		itens.forEach(i -> {
			idsItens.add(i.getId());
		});
		
		reuniaoDTO.setItens(idsItens);
		reuniaoDTO.setParticipantes(idsParticipantes);
		reuniaoDTO.setId(idReuniao);
		
		return reuniaoDTO;
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Reuniao> deletarReuniao(@PathVariable("id") Long id){
		
		try {
			reuniaoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
					
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Reuniao> atualizar(@RequestBody ReuniaoDTO reuniaoDTO,
			@PathVariable("id") Long id) {
		
					
		List<Usuario> participantes = usuarioRepository.findAllById(reuniaoDTO.getParticipantes());
		
		List<Item> itens = itemRepository.findAllById(reuniaoDTO.getItens());
		
		Reuniao reuniao = reuniaoRepository.findById(id).get();
		reuniao = reuniaoDTO.toReuniao(reuniao, participantes, itens);
		
		try {
		reuniaoRepository.save(reuniao);
		
		return ResponseEntity.ok(reuniao);
		
		} catch (Exception e) {
		return ResponseEntity.internalServerError().build();
		}
		
	}
	

}
