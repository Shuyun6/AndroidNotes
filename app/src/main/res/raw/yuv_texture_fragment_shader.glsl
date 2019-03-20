precision mediump float;
uniform sampler2D u_TextureY;
uniform sampler2D u_TextureUV;
varying vec2 v_TextureCoordinates;

void main() {
    vec3 yuv = vec3(
    texture2D(u_TextureY, v_TextureCoordinates).r - 0.0625,
    0,
    0
    );
    vec3 rgb = mat3( 1,           1,         1,
                    0,    -0.39465,   2.03211,
                    1.13983,    -0.58060,         0) * yuv;
    gl_FragColor = vec4(rgb, 1);
}