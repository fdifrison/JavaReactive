package com.reactive.S11sinks.assignment;

import com.reactive.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Colors {

    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN

    List<String> cololist;

    {
        cololist = new ArrayList<>();
        cololist.add(GREEN);
        cololist.add(YELLOW);
        cololist.add(BLUE);
        cololist.add(PURPLE);
        cololist.add(CYAN);
    }


    public String getColor() {
        Integer i = Util.faker().random().nextInt(0, cololist.size() - 1);
        String color = cololist.get(i);
        cololist.remove(color);
        return color;
    }

}
