package br.ufscar.dc.dsw.controller.rest;

import br.ufscar.dc.dsw.Utils.RestUtils;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.service.spec.IPacoteService;

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
public class PacoteRestController {

    @Autowired
    private IPacoteService pacoteService;

    private void parse(Pacote pacote, JSONObject jsonObject) {
        Object id = jsonObject.get("id");
        if(id != null) {
            if (id instanceof Integer) {
                pacote.setId(((Integer) id).longValue());
            } else {
                pacote.setId((Long) id);
            }
        }

        pacote.setAgencia((Agencia) jsonObject.get("agencia"));
        pacote.setCidade((String) jsonObject.get("cidade"));
        pacote.setEstado((String) jsonObject.get("estado"));
        pacote.setPais((String) jsonObject.get("pais"));
        pacote.setDataPartida((Date) jsonObject.get("dataPartida"));
        pacote.setDuracaoDias((Long) jsonObject.get("duracaoDias"));
        pacote.setValor((BigDecimal) jsonObject.get("valor"));
        pacote.setDescricao((String) jsonObject.get("descricao"));
        pacote.setPictures((String) jsonObject.get("pictures"));
        pacote.setAgencia((Agencia) jsonObject.get("agencia"));
    }

    @GetMapping(path="pacotes")
    public ResponseEntity<List<Pacote>> lista() {
        final List<Pacote> lista = pacoteService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path="pacotes/{id}")
    public ResponseEntity<Pacote> lista(@PathVariable("id") long id) {
        final Pacote pacote = pacoteService.buscarPorId(id);
        if (pacote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacote);
    }

    @PostMapping(path="pacotes")
    @ResponseBody
    public ResponseEntity<Pacote> cria(@RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Pacote pacote = new Pacote();
                parse(pacote, jsonObject);
                pacoteService.salvar(pacote);
                return ResponseEntity.ok(pacote);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path="pacotes/{id}")
    public ResponseEntity<Pacote> atualiza(@PathVariable("id") long id, @RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Pacote pacote = pacoteService.buscarPorId(id);
                if(pacote == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(pacote, jsonObject);
                    pacoteService.salvar(pacote);
                    return ResponseEntity.ok(pacote);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/pacotes/{id}")
    public ResponseEntity<Boolean> remove (@PathVariable("id") long id) {
        Pacote pacote = pacoteService.buscarPorId(id);
        if (pacote == null) {
            return ResponseEntity.notFound().build();
        } else  {
            pacoteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}
