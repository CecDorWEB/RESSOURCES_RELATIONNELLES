package com.RESSOURCES_RELATIONNELLES.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MODERATOR")
public class Moderator extends User {

}
