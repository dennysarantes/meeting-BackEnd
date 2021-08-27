package br.com.meeting.controll;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.meeting.model.AcaoRascunho;
import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.AcaoRepository;
import br.com.meeting.repository.DeliberacaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.service.GuardaTokenService;

@RestController
@RequestMapping("api/acao")
public class AcaoController {
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DeliberacaoRepository deliberacaoRepository;
	
	@Autowired
	private GuardaTokenService guardaTokenService;
	

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
			
			Deliberacao deliberacao = deliberacaoRepository.findById(acaoDTO.getDeliberacao()).get();
			
			Acao acao = new Acao(acaoDTO, responsavel, deliberacao );
			
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
		
		@PostMapping("/novo/rascunho/{idDeliberacao}")
		@Transactional
		public Integer cadastrarAcaoNovaComRascunho(@PathVariable("idDeliberacao") Long idDeliberacao,
				@RequestBody AcaoDTO acaoDTO) {
			
			Long userId = Long.parseLong(this.guardaTokenService.getToken().getSub());
			Usuario usuario = usuarioRepository.findById(userId).get();
			
			Deliberacao deliberacao = deliberacaoRepository.findById(idDeliberacao).get();
			
			AcaoRascunho acaoRascunho = new AcaoRascunho();
			
			acaoRascunho.setDataModificacao(LocalDateTime.now());
			acaoRascunho.setDescricao(acaoDTO.getDescricao());
			
			
			acaoRascunho.setDataRealizada(convertToLocalDateTimeViaSqlTimestamp(acaoDTO.getDataRealizada()));
			
			Acao acao = acaoDTO.toAcaoNovo(usuario, deliberacao);
			
			acao.setDescricao(null);
			acao.setDataRealizada(convertToDateViaSqlTimestamp(acaoRascunho.getDataRealizada()));
			
			acao.setAcaoRascunho(acaoRascunho);
			
			List<Acao> acaoList = new ArrayList<Acao>();
			acaoList.add(acao);
			
			deliberacao.setAcoes(acaoList);
						
			try {
					
					deliberacaoRepository.save(deliberacao);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			
			return null;
			
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
			Deliberacao deliberacao = deliberacaoRepository.findById(acaoDTO.getDeliberacao()).get();
			
			Acao acao = acaoRepository.findById(id).get(); 
			
			acao = acaoDTO.toAcao(acao, responsavel, deliberacao);
			
			try {
				acaoRepository.save(acao);
				return ResponseEntity.ok(acao);
				
			} catch (Exception e) {
				return ResponseEntity.notFound().build();
			}
		}
		
		private LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
		    return new java.sql.Timestamp(
		      dateToConvert.getTime()).toLocalDateTime();
		}
		
		public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
		    return java.sql.Timestamp.valueOf(dateToConvert);
		}
}
