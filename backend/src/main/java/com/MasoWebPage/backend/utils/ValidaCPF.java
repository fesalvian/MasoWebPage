package com.MasoWebPage.backend.utils;

public class  ValidaCPF {
    // Validates if the CPF number is valid
    public static boolean isValido(String cpf) {

        cpf = cpf.replaceAll("[^0-9]", "");


        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(.)\\1{10}")) {
            return false;
        }

        int firstDigit = calculaVerificacaoDigital(cpf.substring(0, 9), 10);
        if (firstDigit != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        int secondDigit = calculaVerificacaoDigital(cpf.substring(0, 10), 11);
        if (secondDigit != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }
    private static int calculaVerificacaoDigital(String base, int weight) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * (weight - i);
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
