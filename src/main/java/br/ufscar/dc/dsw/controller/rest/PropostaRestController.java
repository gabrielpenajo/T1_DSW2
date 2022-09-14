package br.ufscar.dc.dsw.controller.rest;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PropostaRestController {

    @Autowired
    private IPropostaService propostaService;

    @GetMapping(path="propostas")
    public ResponseEntity<List<Proposta>> lista() {
        final List<Proposta> lista = propostaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path="propostas/{id}")
    public ResponseEntity<Proposta> lista(@PathVariable("id") long id) {
        final Proposta proposta = propostaService.buscarPorId(id);
        if (proposta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proposta);
    }

    @DeleteMapping(path = "/propostas/{id}")
    public ResponseEntity<Boolean> remove (@PathVariable("id") long id) {
        Proposta proposta = propostaService.buscarPorId(id);
        if (proposta == null) {
            return ResponseEntity.notFound().build();
        } else  {
            propostaService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}
