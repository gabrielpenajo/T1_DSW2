package br.ufscar.dc.dsw.controller.rest;

import br.ufscar.dc.dsw.Utils.RestUtils;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class PropostaRestController {

    @Autowired
    private IPropostaService propostaService;

    private void parse(Proposta proposta, JSONObject jsonObject) {
        Object id = jsonObject.get("id");
        if(id != null) {
            if (id instanceof Integer) {
                proposta.setId(((Integer) id).longValue());
            } else {
                proposta.setId((Long) id);
            }
        }

        proposta.setPacote((Pacote) jsonObject.get("pacote"));
        proposta.setDataProposta((Date) jsonObject.get("dataProposta"));
        proposta.setValor((BigDecimal) jsonObject.get("valor"));
        proposta.setStatusProposta((Integer) jsonObject.get("statusProposta"));
    }

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

    @PostMapping(path="propostas")
    @ResponseBody
    public ResponseEntity<Proposta> cria(@RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Proposta proposta = new Proposta();
                parse(proposta, jsonObject);
                propostaService.salvar(proposta);
                return ResponseEntity.ok(proposta);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path="propostas/{id}")
    public ResponseEntity<Proposta> atualiza(@PathVariable("id") long id, @RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Proposta proposta = propostaService.buscarPorId(id);
                if(proposta == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(proposta, jsonObject);
                    propostaService.salvar(proposta);
                    return ResponseEntity.ok(proposta);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
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
