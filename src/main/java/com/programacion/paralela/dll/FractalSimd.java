package com.programacion.paralela.dll;

import com.programacion.paralela.FractalParam;

import java.nio.ByteBuffer;

public class FractalSimd {

    public ByteBuffer pixelBuffer;

    public FractalSimd() {
        pixelBuffer = ByteBuffer.allocateDirect(FractalParam.WIDTH * FractalParam.HEIGHT * 4);
    }

    public void juliaSimd() {
        FractalDll.INSTANCE.julia_simd(
                FractalParam.x_min,
                FractalParam.y_min,
                FractalParam.x_max,
                FractalParam.y_max,
                FractalParam.WIDTH,
                FractalParam.HEIGHT,
                FractalParam.max_iterations,
                pixelBuffer
        );
    }
}
