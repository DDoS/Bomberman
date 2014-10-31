// $shader_type: fragment
// $texture_layout: spriteSheet = 0

#version 120

varying vec2 spriteCoords;

uniform sampler2D spriteSheet;

void main() {
    gl_FragColor = texture2D(spriteSheet, spriteCoords);
}
