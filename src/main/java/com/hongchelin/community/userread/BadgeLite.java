package com.hongchelin.community.userread;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "badge")
public class BadgeLite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iconPath;
}
