package com.reactive.part1;

import com.reactive.part1.assignment.FileService;
import com.reactive.util.Util;

public class L09Assignment {

    public static void main(String[] args) {

        FileService.read("file01.txt")
                .subscribe(
                        Util.onNext(),
                        Util.onError(),
                        Util.onComplete()
                );

        FileService.write("file03.txt", "A brand new File!").subscribe();

        FileService.delete("file03.txt").subscribe();
    }

}
