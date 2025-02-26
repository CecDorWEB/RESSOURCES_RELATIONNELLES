package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends User {

}
