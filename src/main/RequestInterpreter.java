package main;

import main.protocol.HTTPResponse;
import main.protocol.HttpRequest;

public interface RequestInterpreter {
    HTTPResponse handle(HttpRequest request, HTTPResponse response) throws Exception;
}
