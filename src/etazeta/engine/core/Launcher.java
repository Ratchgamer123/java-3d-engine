package etazeta.engine.core;

import etazeta.engine.core.utils.Constants;
import org.lwjgl.Version;
import testing.TestGame;

public class Launcher
{
    private static Window window;
    private static TestGame game;

    public static void main(String[] args)
    {
        System.out.println(Version.getVersion());
        window = new Window(Constants.TITLE, 800, 600, false);

        game = new TestGame();

        Engine engine = new Engine();

        try
        {
            engine.start();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static Window getWindow()
    {
        return window;
    }

    public static TestGame getGame()
    {
        return game;
    }
}
