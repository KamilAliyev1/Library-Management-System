package com.FinalProject.security.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_tokens")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;

    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Token{" +
               "id=" + id +
               ", token='" + token + '\'' +
               ", tokenType=" + tokenType +
               ", expired=" + expired +
               ", revoked=" + revoked +
               '}';
    }


}
