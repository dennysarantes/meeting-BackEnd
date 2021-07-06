package br.com.meeting.controll;

import java.net.URI;
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

import br.com.meeting.dto.AcaoDTO;
import br.com.meeting.model.Acao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.AcaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.UsuarioRepository;

@RestController
@RequestMapping("api/acao")
public class AcaoController {
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	@GetMapping("")
	public  List<AcaoDTO> listaTodasAcoes(){
		
		List<Acao> acoes = (List<Acao>) acaoRepository.findAll();		
		List<AcaoDTO> acoesDTO = ModelToDTO.deAcaoParaAcaoDTO(acoes);
		
		return acoesDTO;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AcaoDTO> listaReuniaoPorId(@PathVariable("id") Long id) {
		
		List<Acao> acoesEncontradas = new ArrayList<Acao>();
		
		try {
			acoesEncontradas.add(acaoRepository.findById(id).get());
			List<AcaoDTO> acao = ModelToDTO.deAcaoParaAcaoDTO(acoesEncontradas);
			return ResponseEntity.ok(acao.get(0));
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
		@PostMapping
		@Transactional
		public ResponseEntity<Acao> cadastrar(@RequestBody AcaoDTO acaoDTO,
				UriComponentsBuilder uriBuilder) {
			
			Usuario responsavel = usuarioRepository.findById(acaoDTO.getResponsavel()).get();
			
			Item item = itemRepository.findById(acaoDTO.getItem()).get();
			
			Acao acao = new Acao(acaoDTO, responsavel, item );
			
			try {
				acaoRepository.save(acao);
				// Esse trecho de código é responsável por criar a URI de retorno da resposta
				// 201
				// Na entrada do método o spring recebeu um uriBuilder
				// O retorno, por convensão, apresenta o objeto criado contendo o ID desse
				// objeto.
				URI uri = uriBuilder.path("api/acao/{id}").buildAndExpand(acao.getId()).toUri();
				
				return ResponseEntity.created(uri).body(acao);
				
			} catch (Exception e) {
				return ResponseEntity.internalServerError().build();
			}
		}
	
		@DeleteMapping("/{id}")
		@Transactional
		public ResponseEntity<Acao> deletarAcao(@PathVariable("id") Long id){
			
			try {
				acaoRepository.deleteById(id);
				return ResponseEntity.noContent().build();
				
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
		}
	
		@PutMapping("/{id}")
		@Transactional
		public ResponseEntity<Acao> cadastrar(@RequestBody AcaoDTO acaoDTO,
				@PathVariable("id") Long id) {
			
			Usuario responsavel = usuarioRepository.findById(acaoDTO.getResponsavel()).get();
			Item item = itemRepository.findById(acaoDTO.getItem()).get();
			
			Acao acao = acaoRepository.findById(id).get(); 
			
			acao = acaoDTO.toAcao(acao, responsavel, item);
			
			try {
				acaoRepository.save(acao);
				return ResponseEntity.ok(acao);
				
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
		}
}
