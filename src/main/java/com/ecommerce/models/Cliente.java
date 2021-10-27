package com.ecommerce.models;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    @NotNull
    private String nome;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String senha;

}
