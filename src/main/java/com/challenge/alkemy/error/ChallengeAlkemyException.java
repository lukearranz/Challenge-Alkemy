package com.challenge.alkemy.error;

public class ChallengeAlkemyException extends Exception {

    // Aca cree una Exception generica general
    // Lo ideal es que tengas una por clase o por tipo de error.     Ejemplo  Pelicula not found   PeliculaAlredyExist   , GeneroNoExiste GeneroDuplicado  PeliculaCalificacionBadRequest

    public ChallengeAlkemyException(String message) {
        super(message);
    }
}
