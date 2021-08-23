package br.com.meeting.controll;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.meeting.dto.AcaoDTO;
import br.com.meeting.dto.DeliberacaoDTO;
import br.com.meeting.model.Acao;
import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.StatusConfirmacao;
import br.com.meeting.model.StatusDeliberacao;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.DeliberacaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.service.GuardaTokenService;

@RestController
@RequestMapping("api/deliberacao")
public class DeliberacaoController {
	
	@Autowired
	private DeliberacaoRepository deliberacaoRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GuardaTokenService guardaTokenService;

	@GetMapping("")
	public  List<DeliberacaoDTO> listaTodasAcoes(){
		
		List<Deliberacao> deliberacaoes = (List<Deliberacao>) deliberacaoRepository.findAll();		
		List<DeliberacaoDTO> deliberacaoesDTO = ModelToDTO.deDeliberacaoParaDeliberacaoDTO(deliberacaoes);
		
		return deliberacaoesDTO;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeliberacaoDTO> listaReuniaoPorId(@PathVariable("id") Long id) {
		
		List<Deliberacao> deliberacoesEncontradas = new ArrayList<Deliberacao>();
		
		try {
			deliberacoesEncontradas.add(deliberacaoRepository.findById(id).get());
			List<DeliberacaoDTO> deliberacao = ModelToDTO.deDeliberacaoParaDeliberacaoDTO(deliberacoesEncontradas);
			return ResponseEntity.ok(deliberacao.get(0));
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/atrasadas")
	public ResponseEntity<List<DeliberacaoDTO>> listaDeliberacoesAtrasadasUsuario() {
		
		System.out.println("Verificando...");
		
		List<Deliberacao> deliberacoesAtrasadas = new ArrayList<Deliberacao>();
		Long idUsuario = Long.parseLong(guardaTokenService.getToken().getSub());
		
		try {
			List<String> statusDeliberacoes = new ArrayList<String>();
			
			deliberacoesAtrasadas = deliberacaoRepository.findAtrasadasByResponsaveisCustom(idUsuario, Date.valueOf(LocalDate.now()), "ATRASADO");
			
			List<DeliberacaoDTO> deliberacoes = ModelToDTO
											  .deDeliberacaoParaDeliberacaoDTO(deliberacoesAtrasadas);
			return ResponseEntity.ok(deliberacoes);
			
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}
	

	@GetMapping("/pendentes")
	public ResponseEntity<List<DeliberacaoDTO>> listaDeliberacoesPendentesUsuario() {
		
		System.out.println("Verificando...");
		
		List<Deliberacao> deliberacoesPendentes = new ArrayList<Deliberacao>();
		Long idUsuario = Long.parseLong(guardaTokenService.getToken().getSub());
		
		try {
			List<String> statusDeliberacoes = new ArrayList<String>();
			
			deliberacoesPendentes = deliberacaoRepository.findPendentesByResponsavel(idUsuario, "PENDENTE");
			
			for (Deliberacao d : deliberacoesPendentes) {
				System.out.println(d.getStatus());
			}
			
			//deliberacoesAtrasadas = deliberacaoRepository.findAllByResponsaveis(idUsuario);
			List<DeliberacaoDTO> deliberacoes = ModelToDTO
											  .deDeliberacaoParaDeliberacaoDTO(deliberacoesPendentes);
			return ResponseEntity.ok(deliberacoes);
			
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}
	
	@GetMapping("/concluidas")
	public ResponseEntity<List<DeliberacaoDTO>> listaDeliberacoesConcluidasUsuario() {
		
		List<Deliberacao> deliberacoesConcluidas = new ArrayList<Deliberacao>();
		Long idUsuario = Long.parseLong(guardaTokenService.getToken().getSub());
		
		try {
			List<String> statusDeliberacoes = new ArrayList<String>();
			
			deliberacoesConcluidas = deliberacaoRepository.findConcluidasByResponsavel(idUsuario, "CONCLUIDA");
			
			for (Deliberacao d : deliberacoesConcluidas) {
				System.out.println(d.getStatus());
			}
			
			//deliberacoesAtrasadas = deliberacaoRepository.findAllByResponsaveis(idUsuario);
			List<DeliberacaoDTO> deliberacoes = ModelToDTO
											  .deDeliberacaoParaDeliberacaoDTO(deliberacoesConcluidas);
			return ResponseEntity.ok(deliberacoes);
			
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<Deliberacao> cadastrar(@RequestBody DeliberacaoDTO deliberacaoDTO,
			UriComponentsBuilder uriBuilder) {
		
		List<Usuario> responsaveis = usuarioRepository.findAllById(deliberacaoDTO.getResponsaveis());
		
		Item item = itemRepository.findById(deliberacaoDTO.getItem()).get();
		
		Deliberacao deliberacao = new Deliberacao(deliberacaoDTO, responsaveis, item );
		
		try {
			deliberacaoRepository.save(deliberacao);
			URI uri = uriBuilder.path("api/deliberacao/{id}").buildAndExpand(deliberacao.getId()).toUri();
			
			return ResponseEntity.created(uri).body(deliberacao);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Deliberacao> deletarDeliberacao(@PathVariable("id") Long id){
		
		try {
			deliberacaoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Deliberacao> atualizar(@RequestBody DeliberacaoDTO deliberacaoDTO,
			@PathVariable("id") Long id) {
		
		
		List<Usuario> responsaveis = usuarioRepository.findAllById(deliberacaoDTO.getResponsaveis());
		Item item = itemRepository.findById(deliberacaoDTO.getItem()).get();
		Deliberacao deliberacao = deliberacaoRepository.findById(id).get(); 
		
		deliberacao = deliberacaoDTO.toDeliberacao(deliberacao, responsaveis, item);
		
		try {
			deliberacaoRepository.save(deliberacao);
			return ResponseEntity.ok(deliberacao);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
		
	}
}
