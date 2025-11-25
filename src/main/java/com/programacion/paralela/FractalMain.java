package com.programacion.paralela;
import com.programacion.paralela.dll.FractalDll;
import com.programacion.paralela.dll.FractalSimd;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
//FractalMain

public class FractalMain {


    private long window; //ventana

    private int textureID; //textura que vamos a presentar

    private IntBuffer intBuffer; //es similar

    private FractalCpu fractalSerial;

    private FractalSimd fractalSimd;

    private FPSCounter fpsCounter = new FPSCounter();

    int modo=1;

    FractalMain() {

        fractalSerial = new FractalCpu();
        fractalSimd = new FractalSimd();
        intBuffer = BufferUtils.createIntBuffer(1600*900);  //inicializamos
    }


    public void run() { //este es el punto de entrada
        System.out.println("Fractal Julia " + Version.getVersion() + "!");

        init(); //inicializa todo
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);


        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();


        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        //configuracion de la ventanas
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //creamos la ventana
        window = glfwCreateWindow(1600, 900, "Julia Set", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        //aqui es el manejo del reclado
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
            if (key == GLFW_KEY_UP && action == GLFW_RELEASE) {
                FractalParam.max_iterations += 10;

            }
            if (key == GLFW_KEY_DOWN && action == GLFW_RELEASE) {
                FractalParam.max_iterations -= 10;
                if (FractalParam.max_iterations < 10) {
                    FractalParam.max_iterations = 10;
                }
            }
            if(key==GLFW_KEY_1 && action==GLFW_RELEASE){
                System.out.println("Modo Java");
                modo=1;


            }
            else if (key==GLFW_KEY_2 && action==GLFW_RELEASE) {
                System.out.println("Modo SIMD - c/c++");
                modo=2;
                fractalSimd = new FractalSimd();
            }



        });
        //esto es para centar la ventana
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                    (vidMode.width()-1600)/2,
                (vidMode.height()-900)/2
        );

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL.createCapabilitiesWGL();

        String version = GL11.glGetString(GL11.GL_VERSION);
        String vendor = GL11.glGetString(GL11.GL_VENDOR);
        String renderer = GL11.glGetString(GL11.GL_RENDERER);

        System.out.println("OpenGL version: " + version);
        System.out.println("OpenGL vendor: " + vendor);
        System.out.println("OpenGL renderer: " + renderer);

        //configuramos la proyeccion
        {
          glMatrixMode(GL_PROJECTION);
          glLoadIdentity();
          glOrtho(-1,1,-1,1,-1,1); //PROYECCION OCTOGONAL
          glMatrixMode(GL_MODELVIEW);
          glEnable(GL_TEXTURE_2D);
          glLoadIdentity();

        }


        //glfwSwapInterval(1); // para agilizar el dibujo

        glfwShowWindow(window);

        setupTexture();
    }
    void setupTexture(){
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(
                GL_TEXTURE_2D,0, GL_RGB,
                1600,900,0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                NULL
        );

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

         while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            paint();

            glfwSwapBuffers(window);

             glfwPollEvents();
        }
    }

    void paint() {

        fpsCounter.update();

        intBuffer.clear();                    // posición a 0, limit = capacity

        if(modo==1) {
            fractalSerial.julia_serial_2();
            intBuffer.put(fractalSerial.pixel_buffer);

        }
        else if (modo==2){
            fractalSimd.juliaSimd();
            intBuffer.put(fractalSimd.pixelBuffer.asIntBuffer());

        }

        intBuffer.rewind();                   // poner posición a 0 para lectura por GL

        //dibujar la textura
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA8,
                1600,900,0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                intBuffer
                );

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(-1,-1);

            glTexCoord2f(0, 1);
            glVertex2f(-1,1);

            glTexCoord2f(1, 1);
            glVertex2f(1,1);

            glTexCoord2f(1, 0);
            glVertex2f(1,-1);
        }
        glEnd();


    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.library.path", "c:\\tools\\libreria;c:\\tools\\mingw64\\bin");

            //PATH=c:/tools/libreria\;c:/tools/mingw64/bin
            new FractalMain().run();
        } catch (Throwable t) {
            System.err.println("Excepción no capturada al ejecutar FractalMain:");
            t.printStackTrace();
            // salir con código 1 para que Gradle/IDE lo marque, pero con stacktrace visible
            System.exit(1);
        }
    }

}