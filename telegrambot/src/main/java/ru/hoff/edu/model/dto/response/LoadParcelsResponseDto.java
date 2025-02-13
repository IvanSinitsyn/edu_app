package ru.hoff.edu.model.dto.response;

public record LoadParcelsResponseDto(String result) implements ResponseDto {

    @Override
    public String execute() {
        return result;
    }
}
