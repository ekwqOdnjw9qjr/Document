package ru.lenazavupach.documentzxc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Company")
public class CompanyDto {

    @JsonProperty("id")
    @Schema(description = "ID компании в БД", example = "123")
    private Long id;

    @JsonProperty("address")
    @NotBlank(message = "The company must have an address")
    @Schema(description = "Адрес компании", example = "ул. Ленина, д. 10")
    private String address;

    @JsonProperty("name")
    @NotBlank(message = "The company must have a name")
    @Schema(description = "Название компании", example = "ООО Ромашка")
    private String name;

    @JsonProperty("email")
    @Size(min = 11, message = "The min length of email address should be 11 characters")
    @NotBlank(message = "The company must have an email")
    @Schema(description = "Email компании", example = "company@example.com")
    private String email;

    @JsonProperty("phone_for_calls")
    @Positive(message = "The phone number cannot be negative")
    @Schema(description = "Телефон для звонков", example = "123456789012")
    private Long phoneForCalls;

    @JsonProperty("inn")
    @Positive(message = "The INN number cannot be negative")
    @Schema(description = "ИНН компании", example = "1234567890")
    private Long inn;

    @JsonProperty("documents")
    @Schema(description = "Список документов, связанных11 с компанией")
    private List<Long> documentsIds = new ArrayList<>();


}

