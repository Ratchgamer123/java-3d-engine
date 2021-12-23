#version 400 core

layout (location = 0) in vec3 position;
in vec2 texCoord;

out vec2 fragTexCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    fragTexCoord = texCoord;
}