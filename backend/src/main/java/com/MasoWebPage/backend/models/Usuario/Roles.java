package com.MasoWebPage.backend.models.Usuario;

public enum Roles {
    COMUM,
    ADM;
    @Override
    public String toString() {
        return this.name();
    }



}
