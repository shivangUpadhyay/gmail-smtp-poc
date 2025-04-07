package com.poc.smtp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestUtil {


    /**
     * Created response entity and return 201 Created Response.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response entity
     */

    public static <T> ResponseEntity<RestResponse<T>> created(T data) {
        RestResponse<T> response = new RestResponse<>("Created", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Returns a 200 OK response with the given data wrapped in a RestResponse.
     *
     * @param <T>  the type of the data
     * @param data the data to return
     * @return the ResponseEntity containing the RestResponse
     */
    public static <T> ResponseEntity<RestResponse<T>> success(T data) {
        RestResponse<T> response = new RestResponse<>("Success", data);
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a failure response with a custom message and HTTP status.
     *
     * @param <T>     the type parameter
     * @param message the error message
     * @param status  the HTTP status code to return
     * @return the ResponseEntity containing the RestResponse
     */
    public static <T> ResponseEntity<RestResponse<T>> failure(String message, HttpStatus status) {
        RestResponse<T> response = new RestResponse<>(message, null);
        return new ResponseEntity<>(response, status);
    }
}
