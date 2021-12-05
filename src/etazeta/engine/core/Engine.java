package etazeta.engine.core;

import etazeta.engine.core.utils.Constants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Engine
{
    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000.0f;

    private static int fps;
    private static float frametime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private Window window;
    private GLFWErrorCallback errorCallback;
    private ILogic logic;

    private void init() throws Exception
    {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Launcher.getWindow();
        logic = Launcher.getGame();
        window.init();
        logic.init();
    }

    public void start() throws Exception
    {
        init();
        if(isRunning)
        {
            return;
        }
        run();
    }

    public void run()
    {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long previousTime = System.nanoTime();
        double unprocessedTime = 0;

        while(isRunning)
        {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - previousTime;
            previousTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while(unprocessedTime > frametime)
            {
                render = true;
                unprocessedTime -= frametime;

                if(window.windowShouldClose())
                {
                    stop();
                }
                if(frameCounter >= NANOSECOND)
                {
                    setFps(frames);
                    window.setTitle(Constants.TITLE + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render)
            {
                update();
                render();
                frames++;
            }
        }
        terminate();
    }

    private void stop()
    {
        if(!isRunning)
        {
            return;
        }
        isRunning = false;
    }

    private void input()
    {
        logic.input();
    }

    private void render()
    {
        logic.render();
        window.update();
    }

    private void update()
    {
        logic.update();
    }

    private void terminate()
    {
        logic.terminate();
        window.terminate();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps()
    {
        return fps;
    }

    public static void setFps(int fps)
    {
        Engine.fps = fps;
    }
}
