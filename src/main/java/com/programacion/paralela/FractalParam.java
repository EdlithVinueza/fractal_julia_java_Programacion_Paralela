package com.programacion.paralela;

public class FractalParam {

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;

    public static double x_min=-1.5;
    public static double x_max=1.5;


    public static double y_min=-1.0;
    public static double y_max=1.0;

    public static int max_iterations=10;

    public static double c_real= -0.7;
    public static double c_imag= 0.27015;


    public static final int PALETTE_SIZE =16;

    // de BGRA a ARGB
    public static int bswap32(int a) {
        return ((a & 0x000000FF) << 24) |
                ((a & 0x0000FF00) << 8)  |
                ((a & 0x00FF0000) >>> 8) |
                ((a & 0xFF000000) >>> 24);
    }

    public static final int[] COLOR_RAMP = {
            bswap32(0x0FFF8BFF),
            bswap32(0x0FF38CFF),
            bswap32(0x10E88DFF),
            bswap32(0x10DC8EFF),
            bswap32(0x11D190FF),
            bswap32(0x11C591FF),
            bswap32(0x12BA92FF),
            bswap32(0x12AE93FF),
            bswap32(0x13A395FF),
            bswap32(0x139796FF),
            bswap32(0x148C97FF),
            bswap32(0x148098FF),
            bswap32(0x15759AFF),
            bswap32(0x15699BFF),
            bswap32(0x165E9CFF),
            bswap32(0x16529DFF)
    };
}
