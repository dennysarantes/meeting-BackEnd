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

import br.com.meeting.dto.ItemDTO;
import br.com.meeting.model.Item;
import br.com.meeting.model.Usuario;
import br.com.meeting.repository.AcaoRepository;
import br.com.meeting.repository.DeliberacaoRepository;
import br.com.meeting.repository.ItemRepository;
import br.com.meeting.repository.UsuarioRepository;

@RestController
@RequestMapping("api/item")
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DeliberacaoRepository deliberacaoRepository;
	
	@Autowired
	private AcaoRepository acaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

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
	
	@PostMapping
	@Transactional
	public ResponseEntity<Item> cadastrar(@RequestBody ItemDTO itemDTO,
			UriComponentsBuilder uriBuilder) {
		
		Usuario responsavel = usuarioRepository.findById(itemDTO.getResponsavel()).get();
		Usuario responsavelCadastrado = usuarioRepository.findById(itemDTO.getResponsavelCadastro()).get();
		
		Item item = new Item(itemDTO, responsavel, responsavelCadastrado);
		
		try {
			itemRepository.save(item);
			URI uri = uriBuilder.path("api/item/{id}").buildAndExpand(item.getId()).toUri();
			
			return ResponseEntity.created(uri).body(item);
			
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
}
