package com.ecommerce.contoller;

import com.ecommerce.models.Cliente;
import com.ecommerce.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value="/projetoecommerce")
@Api(value = "API-Rest E-Commerce")
@CrossOrigin(origins = "*")
public class ECommerceController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(path = "/buscarCliente/{email}&{senha}")
    @ApiOperation(value = "Faz a busca no banco para saber se há um cliente com o email recebido e se a senha recebida está correta.")
    public Cliente buscarCliente(@PathVariable(value="email") String email, @PathVariable(value="senha") String senha) {
        Cliente cliente;

        try {
            cliente = clienteRepository.findByEmail(email);
        } catch(Exception exception) {
            throw new ResponseStatusException(HttpStatus.valueOf(exception.getCause().toString()));
        }

        if(cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado, verifique se o email está correto.");
        } else if(!cliente.getSenha().equals(senha)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta. Digite novamente.");
        } else {
            return cliente;
        }
    }

    @PostMapping(path = "/salvarCliente")
    @ApiOperation(value = "Salva o cliente no banco de dados se ele estiver com os campos de cadastro corretos.")
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        List<Cliente> listaCliente = clienteRepository.findAll();
        for (Cliente buscarCliente : listaCliente) {
            if (buscarCliente.getEmail().equals(cliente.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já tem um cliente cadastrado com esse email.");
            }
        }

        if(cliente.getSenha() == null || cliente.getSenha().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Digite uma senha.");
        } else if(cliente.getEmail() == null || cliente.getEmail().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Digite um email.");
        } else if(cliente.getNome() == null || cliente.getNome().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Digite um nome.");
        } else {
            return clienteRepository.save(cliente);
        }

    }

}
