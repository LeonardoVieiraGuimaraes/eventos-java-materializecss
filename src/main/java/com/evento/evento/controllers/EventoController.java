package com.evento.evento.controllers;

// import java.util.Map;

import javax.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evento.evento.models.ConvidadoModel;
import com.evento.evento.models.EventoModel;
import com.evento.evento.repositories.ConvidadoRepository;
import com.evento.evento.repositories.EventoRepository;

@Controller
public class EventoController {

    private EventoRepository eventoRepository;
    private ConvidadoRepository convidadoRepository;

    public EventoController(EventoRepository eventoRepository, ConvidadoRepository convidadoRepository) {
        this.eventoRepository = eventoRepository;
        this.convidadoRepository = convidadoRepository;
    }

    @GetMapping(value = "/cadastrar")
    public String form() {
        return "eventos/formEvento";
    }

    @PostMapping(value = "/cadastrar")
    public String form(@Valid EventoModel evento, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os Campos");
            return "redirect:/cadastrar";
        }

        eventoRepository.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento adicinado com sucesso!!");
        return "redirect:/cadastrar";
    }

    @GetMapping("/eventos")
    public ModelAndView listaEventos() {
        ModelAndView mv = new ModelAndView("listaEventos");
        Iterable<EventoModel> eventos = eventoRepository.findAll();
        mv.addObject("eventos", eventos);
        return mv;
    }

    @GetMapping(value = "/eventos/{codigo}")
    public ModelAndView detalhesEventoGet(@PathVariable("codigo") long codigo) {
        EventoModel evento = eventoRepository.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("/eventos/detalhesEvento");
        mv.addObject("evento", evento);
        Iterable<ConvidadoModel> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);
        return mv;
    }

    @PostMapping(value = "/eventos/{codigo}")
    public String detalhesEventoPost(@PathVariable("codigo") Long codigo,
            @Valid ConvidadoModel convidado, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {

            attributes.addFlashAttribute("mensagem", "Verifique os Campos");
            return "redirect:/eventos/{codigo}";
        }
        EventoModel evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicinado com sucesso!!");
        return "redirect:/eventos/{codigo}";

    }

    @GetMapping(value = "/eventos/deletarEvento/{codigo}")
    public String deletarEvento(@PathVariable("codigo") Long codigo) {
        EventoModel evento = eventoRepository.findByCodigo(codigo);

        eventoRepository.delete(evento);
        return "redirect:/eventos";
    }

    @GetMapping(value = "/eventos/deletarConvidado/{cpf}")
    public String deletarConvidado(@PathVariable("cpf") String cpf) {
        ConvidadoModel convidado = convidadoRepository.findByCpf(cpf);
        convidadoRepository.delete(convidado);
        EventoModel evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = "" + codigoLong;
        return "redirect:/eventos/" + codigo;

    }

}
