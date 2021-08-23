package br.com.meeting.controll;

import java.net.URI;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import br.com.meeting.dto.ConfirmacaoDTO;
import br.com.meeting.dto.ItemDTO;
import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.dto.UsuarioDTO;
import br.com.meeting.model.Confirmacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.ConfirmacaoRepository;
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
	
	@Autowired
	private ConfirmacaoRepository confirmacaoRepository;

	@GetMapping("")
	public  List<ReuniaoDTO> listaTodasReunioes(){
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAll();		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	@GetMapping("/proximas/{id}")
	public  List<ReuniaoDTO> listaTodasReunioesExcetoUma(@PathVariable("id") Long id){
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findByDataAgendamentoAfterAndIdNotOrderByDataAgendamentoAsc ( LocalDateTime.now(), id);		
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
	
	
	@GetMapping("/user-yet-prox-test")	
	public List<ReuniaoDTO> listaProximasReunioesDoUsuarioTest(){
		
		System.out.println("Id de usuário passado no controller: "+ guardaTokenService.getToken().getSub());
		
		//Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		//System.out.println("ID de usuário retornado na consulta: " + user.getId());
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserIDMaiorQueHojeTest(Long.parseLong(guardaTokenService.getToken().getSub()), LocalDateTime.now()) ;		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	
	@GetMapping("/user-yet-prox")	
	public List<ReuniaoDTO> listaProximasReunioesDoUsuario(){
		
		System.out.println("Id de usuário passado no controller: "+ guardaTokenService.getToken().getSub());
		
		//Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		//System.out.println("ID de usuário retornado na consulta: " + user.getId());
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserIDMaiorQueHoje(Long.parseLong(guardaTokenService.getToken().getSub()), LocalDateTime.now()) ;		
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		return reunioesDTO;
	}
	
	@GetMapping("/proximasnouser")	
	public List<ReuniaoDTO> listaProximasReunioesNaoUsuario(){
		
		//Pega o usuário logado
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		//Obtem a lista das próximas reuniões que esse usuário está incluso 
		//List<Reuniao> reunioesList = reuniaoRepository.findIdsReunioesByUserMaiorQueHoje(user.getId(), LocalDateTime.now());
		
		//Da lista de reuniões, filtra-se apenas os IDs
//		List<Long> soIdsReunioes = new ArrayList<Long>(); 
//		soIdsReunioes.add(0L);
//		for (Reuniao reuniao :  reunioesList) {
//			soIdsReunioes.add(reuniao.getId());
//		}
		
		//Busca as próximas reuniões que o usuário não estará presente
		
		//List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllProximasByNotUserID(user.getId(), LocalDateTime.now(), soIdsReunioes);		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllProximasByNotUserID(user.getId(), LocalDateTime.now());
		//Transforma a lista de reuniões em reuniõesDTO
		List<ReuniaoDTO> reunioesDTO = ModelToDTO.deReuniaoParaReuniaoDTO(reunioes);
		
		
		List<ConfirmacaoDTO> confirmacoesDTOdaReuniaoAtual = new ArrayList<ConfirmacaoDTO>();		
		for (ReuniaoDTO rDTO : reunioesDTO) {
			confirmacoesDTOdaReuniaoAtual = rDTO.getConfirmacoes();
			
			int indexConfirmacao = 0;
			for (ConfirmacaoDTO cDTO : confirmacoesDTOdaReuniaoAtual) {
				if (cDTO.getParticipantes() == user.getId()) {
					List<ConfirmacaoDTO> confUser = new ArrayList<ConfirmacaoDTO>();
					confUser.add(cDTO);
					rDTO.setConfirmacoes(confUser);
					indexConfirmacao++;
				}else {
					rDTO.setConfirmacoes(null);
					indexConfirmacao++;
				}
			}
		}
		
		
		//Obtém apenas os ids das próximas reuniões que o usuário não estará presente 
//		List<Long> listaIdsReunioes = new ArrayList<Long>();
//		ConfirmacaoDTO confirmacaoNull = new ConfirmacaoDTO();
//		List<ConfirmacaoDTO> listB = new ArrayList<ConfirmacaoDTO>();
//		listB.add(confirmacaoNull);
//		
//		for (ReuniaoDTO reuniaoDTO : reunioesDTO) {
//			listaIdsReunioes.add(reuniaoDTO.getId());
//			//reuniaoDTO.setConfirmacoes(listB);
//		}
//		
//		System.out.println("XXXXXXXXXXXXXXXXLISTA DE IDS DAS REUNIOES QUE USER NÃO ESTÁ PRESENTEXXXXXXXXXXXXXXX");
//		
//		for (Long id : listaIdsReunioes) {
//			System.out.println(id);
//		}
		
		//Busca as confirmações corretas das próximas reuniões que o usuário não estará presente
		//List<Confirmacao> confirmacoes = confirmacaoRepository.findAllByReuniaoIdAndUsuarioIDX(listaIdsReunioes, user.getId());
		//List<Confirmacao> confirmacoes = confirmacaoRepository.findAllByReuniaoIdAndUsuarioID(user.getId());
		//Pega apenas os ids das confirmações		
		
		//Map<Integer, Confirmacao> mapaConfirmacoes = new HashMap<Integer, Confirmacao>();
		//int indexConf = 0;
//		for (Confirmacao confirmacao : confirmacoes) {
//			//System.out.println("ID DE CONFIRMAÇÃO: " + confirmacao.getId());
//			//System.out.println("ID DA REUNIÃO: " + confirmacao.getReuniao().getId());
//			Reuniao reuniao = reuniaoRepository.getById(confirmacao.getReuniao().getId());
//			System.out.println("Reuniao encontrada: " + reuniao.getId());
//			
//		    ReuniaoDTO reuniaoDTO = ModelToDTO.deReuniaoUnidParaReuniaoDTO(reuniao);
//		    System.out.println("ID da reunião transformada: " + reuniaoDTO.getId());
//		    
//		    System.out.println("Indice do array: " + reunioesDTO.indexOf(reuniaoDTO));
//		    
//		    for (ReuniaoDTO rDTO : reunioesDTO) {
//		    	if (rDTO.getId() == reuniaoDTO.getId()) {
//		    		rDTO.setConfirmacoes(reuniaoDTO.getConfirmacoes());
//		    	}
//		    }
		    
		    //reunioesDTO.get(reunioesDTO.  indexOf(reuniaoDTO)).setConfirmacoes(reuniaoDTO.getConfirmacoes());
		    //mapaConfirmacoes.put(indexConf++, confirmacao);
		//}
		
//		for (int i = 1; i < mapaConfirmacoes.size(); i++) {
//			if (listaIdsReunioes.contains(mapaConfirmacoes.get(i).getReuniao().getId())) {
//				for (ReuniaoDTO reuniaoDTO : reunioesDTO) {
//					if (reuniaoDTO.getId() == mapaConfirmacoes.get(i).getReuniao().getId()) {
//						List<ConfirmacaoDTO> listC = new ArrayList<ConfirmacaoDTO>();
//						ConfirmacaoDTO confirmacaoToDTO = new ConfirmacaoDTO().confirmacaoToDTO(mapaConfirmacoes.get(i));
//						listC.add(confirmacaoToDTO);
//						reuniaoDTO.setConfirmacoes(listC);
//				}
//				}
//			}
//		}
		
		
		
		return reunioesDTO;
	}
	

	@GetMapping("/user-yet-antigas")	
	public List<ReuniaoDTO> listaAntigasReunioesDoUsuario(){
		
		
		Usuario user = usuarioRepository.findByUsername(guardaTokenService.getToken().getUsername()).get();
		
		List<Reuniao> reunioes = (List<Reuniao>) reuniaoRepository.findAllByUserIDMenorQueHoje(user.getId(), LocalDateTime.now()) ;		
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
	
	
	@GetMapping("/participantes/{idReuniao}")
	public List<UsuarioDTO> listaItemPorReuniao (@PathVariable("idReuniao") Long idReuniao){
		
		List<Usuario> participantesEncontrados = new ArrayList<Usuario>();
		
		try {
			participantesEncontrados = reuniaoRepository.findParticipantesByReuniaoId(idReuniao);
			
			List<UsuarioDTO> participantes = ModelToDTO.deUsuarioParaUsuarioDTO(participantesEncontrados);
						
			return participantes;
			
		} catch (Exception e) {
			return null;
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
	
	@PutMapping("retiraruser/{idReuniao}/{idUsuario}/{idConfirmacao}")
	@Transactional
	public ResponseEntity<Reuniao> retirarUsuarioPorReuniao(
			@PathVariable("idReuniao") Long idReuniao,
			@PathVariable("idUsuario") Long idUsuario,
			@PathVariable("idConfirmacao") Long idConfirmacao) {
		

		List<Usuario> usuarios = new ArrayList<Usuario>(); 
		
		try {
			Reuniao reuniao = reuniaoRepository.findById(idReuniao.longValue()).get();
			usuarios = reuniaoRepository.findParticipantesByReuniaoId(idReuniao);
			
			for (int i = 0; i < usuarios.size(); i++) {
				if(usuarios.get(i).getId() == idUsuario) {
					usuarios.remove(i);
				}
			}
			
			reuniao.setParticipantes(usuarios);
			reuniaoRepository.save(reuniao);
			confirmacaoRepository.deleteById(idConfirmacao);
			
		return ResponseEntity.ok().build();
		
		} catch (Exception e) {
			System.out.println(e);
		return ResponseEntity.internalServerError().build();
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
