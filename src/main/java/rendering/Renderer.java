package rendering;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import scenes.IScene;
import system.IServiceLocator;
import system.jogl.GlDebugOutput;
import system.jogl.Semantic;
import system.jogl.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public final class Renderer implements IRenderer {
    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        serviceLocator.provide(new Renderer());
    }

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private int vertexCount = 3;
    private int vertexSize = vertexCount * 5 * Float.BYTES;
    private float[] vertexData = new float[]{
            -1, -1,/**/ 1, 0, 0,
            +0, +2,/**/ 0, 0, 1,
            +1, -1,/**/ 0, 1, 0
    };

    private int elementCount = 3;
    private int elementSize = elementCount * Short.BYTES;
    private short[] elementData = new short[]{
            0, 2, 1
    };

    private static class Buffer {

        public static final int VERTEX = 0;
        public static final int ELEMENT = 1;
        public static final int TRANSFORM = 2;
        public static final int MAX = 3;
    }

    /**
     * Use pools, you don't want to create and let them cleaned by the garbage
     * collector continuously in the display() method.
     */
    private float[] scale = new float[16], zRotazion = new float[16], modelToClip = new float[16];
    private long start, now;
    private IntBuffer bufferName = GLBuffers.newDirectIntBuffer(Buffer.MAX);
    private IntBuffer vertexArrayName = GLBuffers.newDirectIntBuffer(1);
    private int programName;
    private FloatBuffer clearColor = GLBuffers.newDirectFloatBuffer(new float[]{1.0f, 0.5f, 0.0f, 1.0f});
    private FloatBuffer clearDepth = GLBuffers.newDirectFloatBuffer(new float[]{1.0f});
    private ByteBuffer transformPointer;

    public static GLWindow glWindow;
    public static Animator animator;

    private final String SHADERS_ROOT = "src/main/resources/shaders";
    private final String SHADERS_NAME = "hello-triangle";

    private IScene currentScene;

    private Renderer() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScene(IScene currentScene) {
        assert currentScene != null;
        this.currentScene = currentScene;
    }

    @Override
    public void start() {
        Display display = NewtFactory.createDisplay(null);
        Screen screen = NewtFactory.createScreen(display, 0);
        GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glWindow = GLWindow.create(screen, glCapabilities);

        glWindow.setSize(1024, 768);
        glWindow.setPosition(50, 50);
        glWindow.setUndecorated(false);
        glWindow.setAlwaysOnTop(false);
        glWindow.setFullscreen(false);
        glWindow.setPointerVisible(true);
        glWindow.confinePointer(false);
        glWindow.setTitle("Hello Triangle");
        glWindow.setContextCreationFlags(GLContext.CTX_OPTION_DEBUG);
        glWindow.setVisible(true);

        glWindow.addGLEventListener(this);
//        glWindow.addKeyListener(serviceLocator.getInputManager());

        animator = new Animator(glWindow);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl4 = drawable.getGL().getGL4();

        initDebug(gl4);

        initBuffers(gl4);

        initVertexArray(gl4);

        initProgram(gl4);

        // map the transform buffer and keep it mapped
        transformPointer = gl4.glMapNamedBufferRange(
                bufferName.get(Buffer.TRANSFORM), // buffer
                0, // offset
                16 * Float.BYTES, // size
                GL_MAP_WRITE_BIT | GL_MAP_PERSISTENT_BIT | GL_MAP_COHERENT_BIT | GL_MAP_INVALIDATE_BUFFER_BIT); // flags

        gl4.glEnable(GL_DEPTH_TEST);

        start = System.currentTimeMillis();
    }

    private void initDebug(GL4 gl4) {

        glWindow.getContext().addGLDebugListener(new GlDebugOutput());
        // Turn off all the debug
        gl4.glDebugMessageControl(
                GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DONT_CARE, // severity
                0, // count
                null, // id
                false); // enabled
        // Turn on all OpenGL Errors, shader compilation/linking errors, or highly-dangerous undefined behavior
        gl4.glDebugMessageControl(
                GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DEBUG_SEVERITY_HIGH, // severity
                0, // count
                null, // id
                true); // enabled
        // Turn on all major performance warnings, shader compilation/linking warnings or the use of deprecated functions
        gl4.glDebugMessageControl(
                GL_DONT_CARE, // source
                GL_DONT_CARE, // type
                GL_DEBUG_SEVERITY_MEDIUM, // severity
                0, // count
                null, // id
                true); // enabled
    }

    private void initBuffers(GL4 gl4) {

        FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(vertexData);
        ShortBuffer elementBuffer = GLBuffers.newDirectShortBuffer(elementData);

        gl4.glCreateBuffers(Buffer.MAX, bufferName);

        gl4.glNamedBufferStorage(bufferName.get(Buffer.VERTEX), vertexBuffer.capacity() * Float.BYTES, vertexBuffer,
                GL_STATIC_DRAW);

        gl4.glNamedBufferStorage(bufferName.get(Buffer.ELEMENT), elementBuffer.capacity() * Short.BYTES,
                elementBuffer, GL_STATIC_DRAW);

        gl4.glNamedBufferStorage(bufferName.get(Buffer.TRANSFORM), 16 * Float.BYTES, null, GL_MAP_WRITE_BIT);


        BufferUtils.destroyDirectBuffer(vertexBuffer);
        BufferUtils.destroyDirectBuffer(elementBuffer);
    }

    private void initVertexArray(GL4 gl4) {

        gl4.glCreateVertexArrays(1, vertexArrayName);

        gl4.glVertexArrayAttribBinding(vertexArrayName.get(0), Semantic.Attr.POSITION, Semantic.Stream._0);
        gl4.glVertexArrayAttribBinding(vertexArrayName.get(0), Semantic.Attr.COLOR, Semantic.Stream._0);

        gl4.glVertexArrayAttribFormat(vertexArrayName.get(0), Semantic.Attr.POSITION, 2, GL_FLOAT, false, 0);
        gl4.glVertexArrayAttribFormat(vertexArrayName.get(0), Semantic.Attr.COLOR, 3, GL_FLOAT, false, 2 * Float.BYTES);

        gl4.glEnableVertexArrayAttrib(vertexArrayName.get(0), Semantic.Attr.POSITION);
        gl4.glEnableVertexArrayAttrib(vertexArrayName.get(0), Semantic.Attr.COLOR);

        gl4.glVertexArrayElementBuffer(vertexArrayName.get(0), bufferName.get(Buffer.ELEMENT));
        gl4.glVertexArrayVertexBuffer(vertexArrayName.get(0), Semantic.Stream._0, bufferName.get(Buffer.VERTEX), 0,
                (3 + 2) * Float.BYTES);
    }

    private void initProgram(GL4 gl4) {

        ShaderCode vertShader = ShaderCode.create(gl4, GL_VERTEX_SHADER, this.getClass(), SHADERS_ROOT, null,
                SHADERS_NAME, "vert", null, true);
        ShaderCode fragShader = ShaderCode.create(gl4, GL_FRAGMENT_SHADER, this.getClass(), SHADERS_ROOT, null,
                SHADERS_NAME, "frag", null, true);

        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.add(vertShader);
        shaderProgram.add(fragShader);

        shaderProgram.init(gl4);

        programName = shaderProgram.program();

        shaderProgram.link(gl4, System.out);

        vertShader.destroy(gl4);
        fragShader.destroy(gl4);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        System.out.println("dispose");

        GL4 gl4 = drawable.getGL().getGL4();

        gl4.glUnmapNamedBuffer(bufferName.get(Buffer.TRANSFORM));

        gl4.glDeleteProgram(programName);
        gl4.glDeleteVertexArrays(1, vertexArrayName);
        gl4.glDeleteBuffers(Buffer.MAX, bufferName);

        BufferUtils.destroyDirectBuffer(vertexArrayName);
        BufferUtils.destroyDirectBuffer(bufferName);;

        BufferUtils.destroyDirectBuffer(clearColor);
        BufferUtils.destroyDirectBuffer(clearDepth);

        System.exit(0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl4 = drawable.getGL().getGL4();

        gl4.glClearBufferfv(GL_COLOR, 0, clearColor);
        gl4.glClearBufferfv(GL_DEPTH, 0, clearDepth);

        {
            // update matrix based on time
            now = System.currentTimeMillis();
            float diff = (float) (now - start) / 1000;
            /**
             * Here we build the matrix that will multiply our original vertex
             * positions. We scale, halving it, and rotate it.
             */
            scale = FloatUtil.makeScale(scale, true, 0.5f, 0.5f, 0.5f);
            zRotazion = FloatUtil.makeRotationEuler(zRotazion, 0, 0, 0, diff);
            modelToClip = FloatUtil.multMatrix(scale, zRotazion);

            transformPointer.asFloatBuffer().put(modelToClip);
        }
        gl4.glUseProgram(programName);
        gl4.glBindVertexArray(vertexArrayName.get(0));

        gl4.glBindBufferBase(
                GL_UNIFORM_BUFFER, // target
                Semantic.Uniform.TRANSFORM0, // index
                bufferName.get(Buffer.TRANSFORM)); // buffer

        gl4.glDrawElements(
                GL_TRIANGLES, // primitive mode
                elementCount, // element count
                GL_UNSIGNED_SHORT, // element type
                0); // element offset
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL4 gl4 = drawable.getGL().getGL4();
        /**
         * Just the glViewport for this sample, normally here you update your
         * projection matrix.
         */
        gl4.glViewport(x, y, width, height);
    }
}
