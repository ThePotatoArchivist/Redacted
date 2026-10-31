#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:matrix.glsl>
#include <minecraft:globals.glsl>

uniform sampler2D redacted_PrevSampler;

layout(location = 0) in vec4 texProj0;

layout(location = 0) out vec4 fragColor;

void main() {
    fragColor = textureProj(redacted_PrevSampler, texProj0);
}
