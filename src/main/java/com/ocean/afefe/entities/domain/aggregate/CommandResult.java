package com.ocean.afefe.entities.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandResult<T extends Aggregate> {
    private String entityId;
    private T result;
    private Map<String, Object> notes = new HashMap<>();

    public CommandResult(String entityId, T result){
        super();
        this.entityId = entityId;
        this.result = result;
        this.notes = new HashMap<>();
    }

    public static <T extends Aggregate> CommandResult<T> from(String entityId, T result){
        return new CommandResult<>(entityId, result);
    }
}
