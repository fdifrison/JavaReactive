package com.reactive.S03fluxEmit.assignment;

import com.reactive.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FileService {

    public static void main(String[] args) {

        final Path PATH = Paths.get("reactor-1/src/main/resources/assignments/emit/file01.txt");
        read(PATH)
                .log()
//                .take(5)
                .subscribe(Util.subscriber("FileReader"));

    }

    private static Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    private static BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (reader, sink) -> {
            try {
                String line = reader.readLine();
                if (Objects.isNull(line))
                    sink.complete();
                else
                    sink.next(line);
            } catch (IOException e) {
                sink.error(e);
            }
            return reader;
        };
    }

    private static Consumer<BufferedReader> closeReader() {
        return reader -> {
            try {
                reader.close();
                System.out.println("\nClosing File..");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Flux<String> read(Path path) {
        return Flux.generate(
                openReader(path),
                read(),
                closeReader()
        );
    }

}
