package com.programacion.paralela;

import com.programacion.paralela.FractalParam;

public class FractalCpu {

    public int[] pixel_buffer;


    public FractalCpu() {
        pixel_buffer = new int[FractalParam.WIDTH*FractalParam.HEIGHT];
        julia_serial_2(); // Calcular el fractal
    }

    int divergente_2(double x, double y)
    {
        //calculos manuales
        int iter = 1;
        double zr =x;
        double zi =y;

        while ((zr*zr+zi*zi) < 4.0 && iter < FractalParam.max_iterations)
        {
            double dr = zr * zr - zi * zi + FractalParam.c_real; //calculamos la parte real
            double di = 2.0 * zr * zi + FractalParam.c_imag; //calculamos la parte imaginaria

            zr = dr;
            zi = di;

            iter++;
        }

        if (iter < FractalParam.max_iterations) // mandamos un color
        {
            int index = (iter % FractalParam.PALETTE_SIZE); // obtenemos el indice de la paleta
            return FractalParam.COLOR_RAMP[index]; // regresamos el color
        }

        return 0xFF000000; // color negro
    }

    void julia_serial_2()
    {

        double dx = (FractalParam.x_max - FractalParam.x_min) / (FractalParam.WIDTH);
        double dy = (FractalParam.y_max - FractalParam.y_min) / (FractalParam.HEIGHT);

        for (int i = 0; i < FractalParam.WIDTH; i++)
        {
            for (int j = 0; j < FractalParam.HEIGHT; j++)
            {
                double x = FractalParam.x_min + i * dx;
                double y =  FractalParam.y_min + j * dy;
                //no vamos a usar complejos

                var color = divergente_2(x, y);  //auto es igual a var --> inferencia de tipos


                pixel_buffer[j * FractalParam.WIDTH + i] = color; // asignamos el color al pixel


            }
        }
}
}

