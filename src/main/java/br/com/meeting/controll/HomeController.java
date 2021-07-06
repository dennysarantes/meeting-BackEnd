package br.com.meeting.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"","/"})
public class HomeController {

	@GetMapping("")
	public String testeController() {
		System.out.println("entrou no controller corretamente...");
		
		return "index.html";		
	}
	
}
