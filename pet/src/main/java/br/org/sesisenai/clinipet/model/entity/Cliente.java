package br.org.sesisenai.clinipet.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cliente extends Pessoa {
    @OneToMany(mappedBy = "dono")
    private List<Animal> animais;
}
