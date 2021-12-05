package etazeta.engine.core;

import etazeta.engine.core.entity.Model;
import etazeta.engine.core.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer
{
    private final Window window;
    private Shader shaderProgram;

    public Renderer()
    {
        window = Launcher.getWindow();
    }

    public void init() throws Exception
    {
        shaderProgram = new Shader();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/standard.vert"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/standard.frag"));
        shaderProgram.link();
        shaderProgram.createUniform("textureSampler");
    }

    public void render(Model model)
    {
        clear();
        shaderProgram.bind();
        shaderProgram.setUniform("textureSampler", 0);
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void clear()
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void terminate()
    {
        shaderProgram.terminate();
    }
}
