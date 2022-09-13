package br.ufscar.dc.dsw.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import br.ufscar.dc.dsw.Utils.RestUtils;
import br.ufscar.dc.dsw.domain.Agencia;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufscar.dc.dsw.service.spec.IAgenciaService;

@CrossOrigin
@RestController
public class AgenciaRestController {

    @Autowired
    private IAgenciaService agenciaService;

    private void parse(Agencia agencia, JSONObject jsonObject)  {
        Object id = jsonObject.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                agencia.setId(((Integer) id).longValue());
            } else {
                agencia.setId((Long) id);
            }
        }

        agencia.setNome((String) jsonObject.get("nome"));
        agencia.setCNPJ((String) jsonObject.get("cnpj"));
        agencia.setSenha((String) jsonObject.get("senha"));
        agencia.setEmail((String) jsonObject.get("email"));
        agencia.setPapel((String) jsonObject.get("papel"));
    }
    
    @GetMapping(path="agencias")
    public ResponseEntity<List<Agencia>> lista() {
        final List<Agencia> lista = agenciaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path="agencias/{id}")
    public ResponseEntity<Agencia> lista(@PathVariable("id") long id) {
        final Agencia agencia = agenciaService.buscarPorId(id);
        if (agencia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agencia);
    }

    @PostMapping(path="agencias")
    @ResponseBody
    public ResponseEntity<Agencia> cria(@RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Agencia agencia = new Agencia();
                parse(agencia, jsonObject);
                agenciaService.salvar(agencia);
                return ResponseEntity.ok(agencia);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path="agencias/{id}")
    public ResponseEntity<Agencia> atualiza(@PathVariable("id") long id, @RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Agencia agencia = agenciaService.buscarPorId(id);
                if(agencia == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(agencia, jsonObject);
                    agenciaService.salvar(agencia);
                    return ResponseEntity.ok(agencia);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/agencias/{id}")
    public ResponseEntity<Boolean> remove (@PathVariable("id") long id) {
        Agencia agencia = agenciaService.buscarPorId(id);
        if (agencia == null) {
            return ResponseEntity.notFound().build();
        } else  {
            agenciaService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }

}
