#version 120

uniform sampler2D DiffuseSampler;
uniform float Intensity;

varying vec2 texCoord;

void main() {
    vec2 uv = texCoord;
    vec4 color = texture2D(DiffuseSampler, uv);
    // 简单的方向模糊（水平+垂直）
    float strength = Intensity * 0.02;
    vec4 sum = color;
    sum += texture2D(DiffuseSampler, uv + vec2(strength, 0.0));
    sum += texture2D(DiffuseSampler, uv - vec2(strength, 0.0));
    sum += texture2D(DiffuseSampler, uv + vec2(0.0, strength));
    sum += texture2D(DiffuseSampler, uv - vec2(0.0, strength));
    sum *= 0.2; // 共5个样本
    gl_FragColor = sum;
}