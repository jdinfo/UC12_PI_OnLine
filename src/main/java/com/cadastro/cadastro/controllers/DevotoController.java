package com.cadastro.cadastro.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cadastro.cadastro.models.Devoto;
import com.cadastro.cadastro.models.Oracao;
import com.cadastro.cadastro.repository.DevotoRepository;
import com.cadastro.cadastro.repository.OracaoRepositoy;

@Controller
public class DevotoController {

	
	@Autowired
	private DevotoRepository dr;
	
	@Autowired
	private OracaoRepositoy or;

	// Cadastrar Devotos
	@RequestMapping(value = "/cadastrarDevoto", method = RequestMethod.GET)
	public String form() {
		return "devoto/formDevoto";
	}

	@RequestMapping(value = "/cadastrarDevoto", method = RequestMethod.POST)
	public String form(@Valid Devoto devoto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos ...");
			return "redirect:/cadastrarDevoto";
		}
		dr.save(devoto);
		attributes.addFlashAttribute("mensagem", "Devoto cadastrado com sucesso!");
		return "redirect:/cadastrarDevoto";
	}

	// Listar Devotos

	@RequestMapping("/devotos")
	public ModelAndView listaDevotos() {
		ModelAndView mv = new ModelAndView("devoto/listaDevoto");
		Iterable<Devoto> devotos = dr.findAll();
		mv.addObject("devotos", devotos);
		return mv;
	}

	//
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesDevoto(@PathVariable("codigo") long codigo) {
		Devoto devoto = dr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("devoto/detalhesDevoto");
		mv.addObject("devoto", devoto);

		Iterable<Oracao> oracoes = or.findByDevoto(devoto);
		mv.addObject("oracoes", oracoes);

		return mv;
	}

	// Deletar Devotos

	@RequestMapping("/deletarDevoto")
	public String deletarDevoto(long codigo) {
		Devoto devoto = dr.findByCodigo(codigo);
		dr.delete(devoto);
		return "redirect:/devotos";
	}

	//Adicionar Oração
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesDevotoPost(@PathVariable("codigo") long codigo, @Valid Oracao oracao, BindingResult result,
			RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}
		
		// versao duplicada
		if(or.findByVersao(oracao.getVersao()) != null ) {
			attributes.addFlashAttribute("mensagem_erro", "Versão duplicada");
			return "redirect:/{codigo}";
		}
		
		Devoto devoto = dr.findByCodigo(codigo);
		oracao.setDevoto(devoto);
		or.save(oracao);
		attributes.addFlashAttribute("mensagem", "Oração adicionada com sucesso!");
		return "redirect:/{codigo}";
	}

	// Deletar Oração pela versao
	@RequestMapping("/deletarOracao")
	public String deletarOracao(String versao) {
		Oracao oracao = or.findByVersao(versao);
		Devoto devoto = oracao.getDevoto();
		String codigo = "" + devoto.getCodigo();
		
		or.delete(oracao);
		
		return "redirect:/" + codigo;
	}
	
	// Métodos que atualizam os devotos
	// formulário de edição de devotos
	@RequestMapping(value= "/editar-devoto", method = RequestMethod.GET)
	public ModelAndView editarDevoto(long codigo) {
		Devoto devoto = dr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("devoto/update-devoto");
		mv.addObject("devoto", devoto);
		
		return mv;
	}
	
	//Update do devoto
	@RequestMapping(value= "/editar-devoto", method = RequestMethod.POST)
	public String updateDevoto(@Valid Devoto devoto, BindingResult result, RedirectAttributes attributes) {
		dr.save(devoto);
		attributes.addFlashAttribute("Sucesso", "Devoto alterado com sucesso!");
		
		long codigoLong = devoto.getCodigo();
		String codigo = "" + codigoLong;		
		return "redirect:/" + codigo;
	}
	
	
	
	
}
