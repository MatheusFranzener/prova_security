package br.org.sesisenai.clinipet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface InterfaceController<ID, DTO> {
    @PostMapping
    ResponseEntity<?> salvar(@RequestBody DTO dto);

    @PutMapping("/{id}")
    ResponseEntity<?> atualizar(@PathVariable(value = "id") ID id, @RequestBody DTO dto);

    @GetMapping("/{id}")
    ResponseEntity<?> buscarPorId(@PathVariable(value = "id") ID id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> excluirPorId(@PathVariable(value = "id") ID id);

    @GetMapping
    ResponseEntity<?> buscarTodos();
}
