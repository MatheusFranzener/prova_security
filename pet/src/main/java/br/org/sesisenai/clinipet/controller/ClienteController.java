package br.org.sesisenai.clinipet.controller;

import br.org.sesisenai.clinipet.model.dto.ClienteDTO;
import br.org.sesisenai.clinipet.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@AllArgsConstructor
public class ClienteController implements InterfaceController<Long, ClienteDTO> {

    private final ClienteService clienteService;

    @Override
    public ResponseEntity<?> salvar(ClienteDTO clienteDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));

        return ResponseEntity.ok(clienteService.salvar(clienteDTO));
    }

    @Override
    public ResponseEntity<?> atualizar(Long id, ClienteDTO clienteDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));

        return ResponseEntity.ok(clienteService.atualizar(id, clienteDTO));
    }

    @Override
    public ResponseEntity<?> buscarPorId(Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Override
    public ResponseEntity<?> excluirPorId(Long id) {
        clienteService.excluirPorId(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> buscarTodos() {
        return ResponseEntity.ok(clienteService.buscarTodos());
    }
}
