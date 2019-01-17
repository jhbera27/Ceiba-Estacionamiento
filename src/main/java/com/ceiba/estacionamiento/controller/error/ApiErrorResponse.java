package com.ceiba.estacionamiento.controller.error;

/**
 * clase para manejar la informacion de error al consumir un servicio rest
 * @author jhon.bedoya
 *
 */
public class ApiErrorResponse {
	/**
	 * Atributo que representa el status del error
	 */
	private int status;
	/**
	 * Atributo que representa el codigo del error
	 */
    private int code;
    /**
     * Atributo que representa el mensaje de error
     */
    private String message;
    /**
     * constructor de la clase
     * @param status, el status del error
     * @param code, el codigo de error 
     * @param message, el mensaje del error
     */
    public ApiErrorResponse(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    /**
     * método encargado de obtener el status del error
     * @return status, el status del error
     */
    public int getStatus() {
        return status;
    }
    /**
     * método encargado de obtener el codigo del error
     * @return code, el code del error
     */
    public int getCode() {
        return code;
    }
    /**
     * método encargado de obtener el mensaje del error
     * @return message, el mensaje del error
     */
    public String getMessage() {
        return message;
    }
    /**
     * método que concatena la informacion del error
     */
    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "status=" + status +
                ", code=" + code +
                ", message=" + message +
                '}';
    }
}
