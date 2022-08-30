package com.testday.testapp.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Employee {
    private Long id;
    private LocalDateTime dateAdded;
    private String firstName;
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String email;
    private String password;
}
