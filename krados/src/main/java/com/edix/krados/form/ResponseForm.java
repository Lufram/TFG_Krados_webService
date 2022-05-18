package com.edix.krados.form;

import lombok.Data;

@Data
public class ResponseForm {
    private String response;

    public ResponseForm(String response) {
        this.response = response;
    }

}



