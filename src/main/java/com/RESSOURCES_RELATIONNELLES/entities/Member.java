package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends User {

}
