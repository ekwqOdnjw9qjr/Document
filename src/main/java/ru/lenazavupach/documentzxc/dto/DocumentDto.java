package ru.lenazavupach.documentzxc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Document")
public class DocumentDto {

    @JsonProperty("id")
    @Schema(description = "ID документа в БД", example = "123")
    private Long id;

    @JsonProperty("title")
    @NotBlank(message = "The title must not be blank")
    @Schema(description = "Заголовок документа", example = "Договор аренды")
    private String title;

    @JsonProperty("type")
    @NotBlank(message = "The type must not be blank")
    @Schema(description = "Тип документа", example = "Контракт")
    private String type;

    @JsonProperty("date")
    @NotNull(message = "The date must not be null")
    @Schema(description = "Дата документа", example = "2024-06-25")
    private LocalDate date;

    @JsonProperty("author")
    @NotBlank(message = "The author must not be blank")
    @Schema(description = "Автор документа", example = "Иван Иванов")
    private String author;

    @JsonProperty("number")
    @Positive(message = "The number must be positive")
    @Schema(description = "Номер документа", example = "456")
    private Long number;

    @JsonProperty("companyId")
    @Schema(description = "Компания, связанная с документом")
    private String companyId;
}
