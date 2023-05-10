package br.org.sesisenai.clinipet.controller;

import br.org.sesisenai.clinipet.model.dto.AtendenteDTO;
import br.org.sesisenai.clinipet.service.AtendenteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/atendente")
@AllArgsConstructor
public class AtendenteController implements InterfaceController<Long, AtendenteDTO> {

    private final AtendenteService atendenteService;

    @Override
    public ResponseEntity<?> salvar(AtendenteDTO atendenteDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        atendenteDTO.setSenha(encoder.encode(atendenteDTO.getSenha()));

        return ResponseEntity.ok(atendenteService.salvar(atendenteDTO));
    }
    @Override
    public ResponseEntity<?> atualizar(Long id, AtendenteDTO atendenteDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        atendenteDTO.setSenha(encoder.encode(atendenteDTO.getSenha()));

        return ResponseEntity.ok(atendenteService.atualizar(id, atendenteDTO));
    }

    @Override
    public ResponseEntity<?> buscarPorId(Long id) {
        return ResponseEntity.ok(atendenteService.buscarPorId(id));
    }

    @Override
    public ResponseEntity<?> excluirPorId(Long id) {
        atendenteService.excluirPorId(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> buscarTodos() {
        return ResponseEntity.ok(atendenteService.buscarTodos());
    }
}
