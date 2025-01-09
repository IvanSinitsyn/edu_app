package ru.hoff.edu.service;

import ru.hoff.edu.model.Command;

import java.io.IOException;

public interface ParcelService {

    void processing(Command command) throws IOException;
}
