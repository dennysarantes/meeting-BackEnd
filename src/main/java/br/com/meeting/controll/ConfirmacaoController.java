package br.com.meeting.controll;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.meeting.dto.ConfirmacaoDTO;
import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.model.Confirmacao;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.StatusConfirmacao;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.ConfirmacaoRepository;
import br.com.meeting.repository.ReuniaoRepository;
import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.service.GuardaTokenService;

@RestController
@RequestMapping("api/confirmacao")
public class ConfirmacaoController {
	
	@Autowired
	private GuardaTokenService guardaTokenService;
	
	@Autowired
	private ReuniaoRepository reuniaoRepository;
	
	@Autowired
	private ConfirmacaoRepository confirmacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	

	@PutMapping("/{idReuniao}")
	@Transactional
	public ResponseEntity alteraStatus (
			@PathVariable("idReuniao") Long idReuniao,
			@RequestBody ConfirmacaoDTO confirmacaoDTO,
			UriComponentsBuilder uriBuilder){
		
		
		System.out.println(confirmacaoDTO == null);
		
		Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
		Usuario usuario = usuarioRepository.findById(Long.parseLong(guardaTokenService.getToken().getSub())).get();
		
		try {
			
			if (confirmacaoDTO == null) {
				System.out.println("Pedindo participação...");
				Confirmacao confirmacaoNovo = new Confirmacao();
				confirmacaoNovo.setDataAlteracao(LocalDateTime.now());
				confirmacaoNovo.setParticipante(usuario);
				confirmacaoNovo.setReuniao(reuniao);
				confirmacaoNovo.setStatusConfirmacao(StatusConfirmacao.SOLICITADO);
				confirmacaoRepository.save(confirmacaoNovo);
			}
			
			if (confirmacaoDTO.getStatusConfirmacao().equalsIgnoreCase("SOLICITADO")) {
				confirmacaoRepository.deleteById(confirmacaoDTO.getId());
			}else {
				confirmacaoRepository.save(new Confirmacao(confirmacaoDTO, usuario, reuniao));
			}
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/{idReuniao}")
	public ResponseEntity incluiStatus (
			@PathVariable("idReuniao") Long idReuniao){
		
		System.out.println("chegou a solicitação");	
		Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
		Usuario usuario = usuarioRepository.findById(Long.parseLong(guardaTokenService.getToken().getSub())).get();
		
		try {
			
				System.out.println("Pedindo participação...");
				Confirmacao confirmacaoNovo = new Confirmacao();
				confirmacaoNovo.setDataAlteracao(LocalDateTime.now());
				confirmacaoNovo.setParticipante(usuario);
				confirmacaoNovo.setReuniao(reuniao);
				confirmacaoNovo.setStatusConfirmacao(StatusConfirmacao.SOLICITADO);
				confirmacaoRepository.save(confirmacaoNovo);
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}

		
		
	}
}
