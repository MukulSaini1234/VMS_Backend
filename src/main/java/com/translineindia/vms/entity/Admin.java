package com.translineindia.vms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_login")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AdminId.class)
public class Admin {

    @Id
    @Column(name = "admin_id", nullable = false, length = 50)
    private String AdminId;

    @Column(name = "cmp_cd", nullable = false, length = 30)
    private String cmpCd;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
}