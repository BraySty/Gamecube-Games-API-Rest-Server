package com.restapi.api.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario", catalog = "gamecube")
public class Usuario {

    
    String nombre;
    @Id
    @Column(name = "Correo", length = 255, unique = true, nullable = false)
    String correo;
    @Column(name = "Password", length = 255)
    String password;

    public Usuario(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario [nombre=" + nombre + ", correo=" + correo + ", contraseña=" + password + "]";
    }

}
