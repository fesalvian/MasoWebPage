package com.MasoWebPage.backend.models.Usuario;

public enum Role {
    COMUM,
    ADM;
    @Override
    public String toString() {
        return this.name();
    }



}
