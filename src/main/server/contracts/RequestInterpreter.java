package main.server.contracts;

import main.protocol.HttpRequest;
import main.protocol.HttpResponse;

public interface RequestInterpreter {
    HttpResponse handle(HttpRequest request, HttpResponse response) throws Exception;
}
