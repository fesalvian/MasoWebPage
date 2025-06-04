package com.MasoWebPage.backend.models.Usuario;

public enum Role {

    LEAD,
    ADM;
    @Override
    public String toString() {
        return this.name();
    }



}
