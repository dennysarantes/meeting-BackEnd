package br.com.meeting.controll;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

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

import br.com.meeting.dto.ItemDTO;
import br.com.meeting.dto.ItemModalDTO;
import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.StatusItem;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.AcaoRepository;
import br.com.meeting.repository.DeliberacaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.ReuniaoRepository;
import br.com.meeting.repository.UsuarioRepository;
import br.com.meeting.service.GuardaTokenService;

@RestController
@RequestMapping("api/item")
public class ItemController {
	
	@Autowired
	private GuardaTokenService guardaTokenService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DeliberacaoRepository deliberacaoRepository;
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ReuniaoRepository reuniaoRepository;

	@GetMapping("")
	public  List<ItemDTO> listaTodasAcoes(){
		
		List<Item> itens = (List<Item>) itemRepository.findAll();		
		List<ItemDTO> itensDTO = ModelToDTO.deItemParaItemDTO(itens);
		
		return itensDTO;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ItemDTO> listaReuniaoPorId(@PathVariable("id") Long id) {
		
		
		
		List<Item> itensEncontrados = new ArrayList<Item>();
		
		try {
			itensEncontrados.add(itemRepository.findById(id).get());
			List<ItemDTO> item = ModelToDTO.deItemParaItemDTO(itensEncontrados);
			return ResponseEntity.ok(item.get(0));
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/reuniao/{idReuniao}")
	public List<ItemDTO> listaItemPorReuniao (@PathVariable("idReuniao") Long idReuniao){
		
		List<Item> itensEncontrados = new ArrayList<Item>();
						
		try {
			itensEncontrados = reuniaoRepository.findItensByReuniaoId(idReuniao);
			List<ItemDTO> itens = ModelToDTO.deItemParaItemDTOTela(itensEncontrados);
			return itens;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ItemDTO> cadastrar(@RequestBody ItemDTO itemDTO,
			UriComponentsBuilder uriBuilder) {
		
		Usuario responsavel = usuarioRepository.findById(itemDTO.getResponsavel()).get();
		Usuario responsavelCadastrado = usuarioRepository.findById(itemDTO.getResponsavelCadastro()).get();
		
		Item item = new Item(itemDTO, responsavel, responsavelCadastrado);
		
		
		try {
			itemRepository.save(item);
			URI uri = uriBuilder.path("api/item/{id}").buildAndExpand(item.getId()).toUri();
			
			return ResponseEntity.created(uri).body(itemDTO);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("modal/{idReuniao}")
	@Transactional
	public ResponseEntity<ItemDTO> cadastrarNovoItemTela(
			@PathVariable("idReuniao") Long idReuniao,
			@RequestBody ItemModalDTO itemModalDTO,
			UriComponentsBuilder uriBuilder) {
		
		
		
		Usuario responsavel = usuarioRepository.findById(Long.parseLong(guardaTokenService.getToken().getSub())).get();
		Usuario responsavelCadastrado = usuarioRepository.findById(Long.parseLong(guardaTokenService.getToken().getSub())).get();
		
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setTitulo(itemModalDTO.getTitulo());
		itemDTO.setDescricao(itemModalDTO.getDescricao());
		itemDTO.setStatus(StatusItem.NOVO);
		itemDTO.setDataCadastro(LocalDate.now());
		
		Item item = new Item(itemDTO, responsavel, responsavelCadastrado);
		
		try {
			itemRepository.save(item);
			Reuniao reuniao = reuniaoRepository.findById(idReuniao).get();
			List<Item> itens = reuniao.getItens();
			itens.add(item);
			reuniao.setItens(itens);
			reuniaoRepository.save(reuniao);
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Item> deletarItem(@PathVariable("id") Long id){
		
		try {
			itemRepository.deleteById(id);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{idReuniao}/{idItem}")
	@Transactional
	public ResponseEntity<List<ItemDTO>> deletarItemPorReuniao(
			@PathVariable("idReuniao") Long idReuniao,
			@PathVariable("idItem") Long idItem,
			UriComponentsBuilder uriBuilder){
		
		
		
		try {
			List<Item> itensEncontrados = new ArrayList<Item>();
			itensEncontrados = reuniaoRepository.findItensByReuniaoId(idReuniao);
			
			int index = 0;
			int indexReuniao = -1;
					
			for (Item item :  itensEncontrados) {
				if (item.getId() == idItem) {
					indexReuniao = index;
				}
				index++;
			}
			if (indexReuniao != -1) {
				itensEncontrados.remove(indexReuniao);
			}

			Reuniao reuniao = reuniaoRepository.getById(idReuniao);
			reuniao.setItens(itensEncontrados);
			
			reuniaoRepository.save(reuniao);
			
			List<ItemDTO> itens = ModelToDTO.deItemParaItemDTOTela(itensEncontrados);
			itemRepository.deleteById(idItem);
						
			return ResponseEntity.status(202).body(itens);
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Item> atualizar(@PathVariable("id") Long id,
			@RequestBody ItemDTO itemDTO) {

		Usuario responsavel = usuarioRepository.findById(itemDTO.getResponsavel()).get();
		Usuario responsavelCadastrado = usuarioRepository.findById(itemDTO.getResponsavelCadastro()).get();
		
		Item item = itemRepository.findById(id).get(); 
		
		item = itemDTO.toItem(item, responsavel, responsavelCadastrado);
		
		try {
	
			itemRepository.save(item);
	
			return ResponseEntity.ok(item);
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PutMapping("/editar/{idItem}")
	@Transactional
	public ResponseEntity<List<ItemDTO>> atualizarComData(
			@PathVariable("idItem") Long idItem,
			@RequestBody ItemModalDTO itemModal) {

		try {
			Item item = itemRepository.getById(idItem);
			item.setDescricao(itemModal.getDescricao());
			item.setTitulo(itemModal.getTitulo());
			//Implementar data de alteração e responsável pela alteração.
			itemRepository.save(item);
			
			if (itemModal.getIdReuniaoAtual() != itemModal.getIdReuniaoNova()){
				List<Item> itensEncontrados = new ArrayList<Item>();
				//Carrega a reunião pelo ID
				Reuniao reuniao = reuniaoRepository.getById(itemModal.getIdReuniaoAtual());
				//Pega a lista de itens da reunião
				itensEncontrados = reuniao.getItens();
				//Verifica em qual posição está o item na respectiva lista de itens
				int index = 0;
				int indexReuniao = -1;
				
				for (Item itemList :  itensEncontrados) {
					if (itemList.getId() == idItem) {
						indexReuniao = index;
					}
					index++;
				}
				//Retira o item da reunião atual
				if (indexReuniao != -1) {
					itensEncontrados.remove(indexReuniao);
				}
				
				//Atualiza a reunião sem o item
				reuniao.setItens(itensEncontrados);
				reuniaoRepository.save(reuniao);
				
				
				//busca a nova reunião
				
				List<Item> itensEncontradosNovaReuniao = new ArrayList<Item>();
				//Carrega a reunião pelo ID
				Reuniao reuniaoNova = reuniaoRepository.getById(itemModal.getIdReuniaoNova());
				//Pega a lista de itens da reunião
				itensEncontradosNovaReuniao = reuniaoNova.getItens();		
				itensEncontradosNovaReuniao.add(item);
				reuniaoNova.setItens(itensEncontradosNovaReuniao);
				//Salva reunião com novo item
				reuniaoRepository.save(reuniaoNova);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
