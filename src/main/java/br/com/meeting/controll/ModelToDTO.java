package br.com.meeting.controll;

import java.util.ArrayList;
import java.util.List;

import br.com.meeting.dto.AcaoDTO;
import br.com.meeting.dto.DeliberacaoDTO;
import br.com.meeting.dto.ItemDTO;
import br.com.meeting.dto.ReuniaoDTO;
import br.com.meeting.dto.UsuarioDTO;
import br.com.meeting.model.Acao;
import br.com.meeting.model.Deliberacao;
import br.com.meeting.model.Item;
import br.com.meeting.model.Reuniao;
import br.com.meeting.model.Usuario;

public class ModelToDTO {
	
	public ModelToDTO() {
		
	}
	
	public static List<ReuniaoDTO> deReuniaoParaReuniaoDTO(List<Reuniao> reunioes) {
		
		List<ReuniaoDTO> reunioesDTO = new ArrayList<ReuniaoDTO>();
		
		reunioes.forEach(reuniao -> {
			reunioesDTO.add(new ReuniaoDTO().toDTO(reuniao));
		});
		
		return reunioesDTO;
		
	}
	
	
	public static ReuniaoDTO deReuniaoUnidParaReuniaoDTO(Reuniao reuniao) {
		
		ReuniaoDTO reuniaoDTO = new ReuniaoDTO();
		
		return reuniaoDTO.toDTO(reuniao);
	}
	

	public static List<AcaoDTO> deAcaoParaAcaoDTO(List<Acao> acoes) {
		
		List<AcaoDTO> acoesDTO = new ArrayList<AcaoDTO>();
		
		acoes.forEach(acao -> {
			acoesDTO.add(new AcaoDTO().toDTO(acao));
		});
		
		
		
		
		return acoesDTO;
	}
	
	public static List<ItemDTO> deItemParaItemDTO(List<Item> itens) {
			
			List<ItemDTO> itemDTO = new ArrayList<ItemDTO>();
			
			itens.forEach(item -> {
				itemDTO.add(new ItemDTO().toDTO(item));
			});
			
			return itemDTO;
			
		}
	
	public static List<ItemDTO> deItemParaItemDTOTela(List<Item> itens) {
		
		List<ItemDTO> itemDTO = new ArrayList<ItemDTO>();
		
		itens.forEach(item -> {
			itemDTO.add(new ItemDTO().toDTOparaTela(item));
		});
		
		return itemDTO;
		
	}
	
	
	public static List<DeliberacaoDTO> deDeliberacaoParaDeliberacaoDTO(List<Deliberacao> deliberacaoes) {
		
		List<DeliberacaoDTO> deliberacaoDTO = new ArrayList<DeliberacaoDTO>();
		
		deliberacaoes.forEach(deliberacao -> {
			deliberacaoDTO.add(new DeliberacaoDTO().toDTO(deliberacao));
		});
		
		return deliberacaoDTO;
		
	}
	
	public static List<UsuarioDTO> deUsuarioParaUsuarioDTO(List<Usuario> usuarios) {
		
		List<UsuarioDTO> usuarioDTO = new ArrayList<UsuarioDTO>();
		
		usuarios.forEach(usuario -> {
			usuarioDTO.add(new UsuarioDTO().toDTOCompleto(usuario));
		});
		
		return usuarioDTO;
		
	}

	
}