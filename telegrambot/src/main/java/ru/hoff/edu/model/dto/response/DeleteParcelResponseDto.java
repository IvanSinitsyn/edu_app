package ru.hoff.edu.model.dto.response;

public record DeleteParcelResponseDto(String result) implements ResponseDto {

    @Override
    public String execute() {
        return result;
    }
}
