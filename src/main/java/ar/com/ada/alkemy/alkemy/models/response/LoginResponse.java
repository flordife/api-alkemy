package ar.com.ada.alkemy.alkemy.models.response;


public class LoginResponse {
    public Integer id;
    public String username;
    public String token;
    public String email;
    public Integer entityId; // Si es un Pasajero, va el Id de Pasajero, si es staff Id Staff

}