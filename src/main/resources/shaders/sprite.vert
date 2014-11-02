// $shader_type: vertex
// $attrib_layout: position = 0
// $attrib_layout: textureCoords = 2

#version 120

attribute vec3 position;
attribute vec2 textureCoords;

varying vec2 spriteCoords;

uniform int spritesPerLine;
uniform int spriteNumber;
uniform vec2 spriteSize;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {
    float spriteX = mod(spriteNumber, spritesPerLine);
    float spriteY = spritesPerLine - 1 - floor(spriteNumber / spritesPerLine);

    spriteCoords = (vec2(spriteX, spriteY) + textureCoords) / spritesPerLine * spriteSize;

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position.xy * spriteSize, 0, 1);
}
