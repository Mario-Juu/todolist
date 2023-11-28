package com.mariojunior.todo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnums {
    ADMIN(1, "ROLE_ADMIN"), USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    public static ProfileEnums toEnum(Integer code){
        if(Objects.isNull(code))
            return null;
        for (ProfileEnums profileEnums : ProfileEnums.values()){
            if(code.equals(profileEnums.getCode()))
                return profileEnums;
            }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
