package br.ufscar.dc.dsw.controller.rest;

import br.ufscar.dc.dsw.Utils.RestUtils;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class UsuarioRestController {

    @Autowired
    private IClienteService clienteService;

    private void parse(Cliente cliente, JSONObject jsonObject) {
        Object id = jsonObject.get("id");
        if(id != null) {
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            } else {
                cliente.setId((Long) id);
            }
        }

        cliente.setNome((String) jsonObject.get("nome"));
        cliente.setCPF((String) jsonObject.get("cpf"));
        cliente.setSexo((String) jsonObject.get("sexo"));

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse((String) jsonObject.get("nascimento"));
            cliente.setNascimento(data);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "A data deve ter um formato válido");
        }

        cliente.setSenha((String) jsonObject.get("senha"));
        cliente.setEmail((String) jsonObject.get("email"));
        cliente.setPapel((String) jsonObject.get("papel"));
        cliente.setTelefone((String) jsonObject.get("telefone"));
    }

    @GetMapping(path="clientes")
    public ResponseEntity<List<Cliente>> lista() {
        final List<Cliente> lista = clienteService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path="clientes/{id}")
    public ResponseEntity<Cliente> lista(@PathVariable("id") long id) {
        final Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(path="clientes")
    @ResponseBody
    public ResponseEntity<Cliente> cria(@RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Cliente cliente = new Cliente();
                parse(cliente, jsonObject);
                clienteService.salvar(cliente);
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path="clientes/{id}")
    public ResponseEntity<Cliente> atualiza(@PathVariable("id") long id, @RequestBody JSONObject jsonObject) {
        try{
            if(RestUtils.isJsonValid(jsonObject.toString())) {
                Cliente cliente = clienteService.buscarPorId(id);
                if(cliente == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(cliente, jsonObject);
                    clienteService.salvar(cliente);
                    return ResponseEntity.ok(cliente);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/clientes/{id}")
    public ResponseEntity<Boolean> remove (@PathVariable("id") long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        } else  {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}
