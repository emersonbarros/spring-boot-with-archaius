package br.com.emersonbarros.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {


	@RequestMapping("/")
	public @ResponseBody String index() throws Exception {
		return "lock.waitTime1=2000";
	}


}
