package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMINISTRATOR")
public class SuperAdministrator extends User {

}
