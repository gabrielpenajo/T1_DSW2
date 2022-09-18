package br.ufscar.dc.dsw.controller.rest;

import br.ufscar.dc.dsw.Utils.RestUtils;
import br.ufscar.dc.dsw.dao.IAgenciaDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IPacoteService;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class PacoteRestController {

    @Autowired
    private IPacoteService pacoteService;

    @Autowired
    private IPropostaService propostaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    IAgenciaDAO agenciaDAO;

    private void parse(Pacote pacote, JSONObject jsonObject) throws ParseException, JsonProcessingException {
        Object id = jsonObject.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                pacote.setId(((Integer) id).longValue());
            } else {
                pacote.setId((Long) id);
            }
        }

        pacote.setCidade((String) jsonObject.get("cidade"));
        pacote.setEstado((String) jsonObject.get("estado"));
        pacote.setPais((String) jsonObject.get("pais"));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse((String) jsonObject.get("dataPartida"));
            pacote.setDataPartida(data);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "A data deve ter um formato válido");
        }
        pacote.setDuracaoDias(((Integer) jsonObject.get("duracaoDias")).longValue());
        pacote.setValor(BigDecimal.valueOf((Double) jsonObject.get("valor")));
        pacote.setDescricao((String) jsonObject.get("descricao"));
        pacote.setPictures((String) jsonObject.get("pictures"));
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

    @GetMapping(path="pacotes/agencias/{id}")
    public ResponseEntity<List<Pacote>> listaPorAgencia(@PathVariable("id") long id) {
        final List<Pacote> lista = pacoteService.buscarPorAgencia(agenciaDAO.findById(id));
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping(path="pacotes/agencias/{id}")
    @ResponseBody
    public ResponseEntity<Pacote> cria(@PathVariable("id") long id, @RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Pacote pacote = new Pacote();
                parse(pacote, jsonObject);
                pacote.setAgencia(agenciaDAO.findById(id));
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

    @GetMapping(path="pacotes/clientes/{id}")
    @ResponseBody
    public ResponseEntity<List<Proposta>> compra(@PathVariable("id") long id) {
        try{
            List<Proposta> propostas = propostaService.buscarTodosPorCliente_Id(id);

            if (propostas.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(propostas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @GetMapping(path="pacotes/destinos")
    public ResponseEntity<List<Pacote>> buscaPorDestino(@RequestParam("nome") String nome) {
        try{
            System.out.println(nome + " " + nome.length());
            List<Pacote> pacotes = pacoteService.buscarPorDestino(nome);

            if (pacotes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pacotes);
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
        } else {
            pacoteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}