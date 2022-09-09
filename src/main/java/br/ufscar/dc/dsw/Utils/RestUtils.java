package br.ufscar.dc.dsw.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtils {

    public static Boolean isJsonValid(String jsonString) {
        try {
            return new ObjectMapper().readTree(jsonString) != null;
        } catch (RuntimeException | JsonProcessingException e) {
            return false;
        }
    }

}