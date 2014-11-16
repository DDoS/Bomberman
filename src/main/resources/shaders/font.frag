/*
 * This shader was modified from the original one included in Caustic
 */

// $shader_type: fragment
// $texture_layout: diffuse = 0

#version 120

varying vec2 textureUV;

uniform sampler2D diffuse;
uniform vec4 fontColor;

uniform vec4 foregroundColor;
uniform vec4 backgroundColor;

void main() {
    float color = texture2D(diffuse, textureUV).r;

    if (color <= 0.5) {
        discard;
    }

    gl_FragColor = fontColor * foregroundColor;
}
