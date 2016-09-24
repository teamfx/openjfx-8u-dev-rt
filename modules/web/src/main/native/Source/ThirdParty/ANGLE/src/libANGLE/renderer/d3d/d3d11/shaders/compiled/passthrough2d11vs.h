#if 0
//
// Generated by Microsoft (R) HLSL Shader Compiler 6.3.9600.16384
//
//
//
// Input signature:
//
// Name                 Index   Mask Register SysValue  Format   Used
// -------------------- ----- ------ -------- -------- ------- ------
// POSITION                 0   xy          0     NONE   float   xy
// TEXCOORD                 0   xy          1     NONE   float   xy
//
//
// Output signature:
//
// Name                 Index   Mask Register SysValue  Format   Used
// -------------------- ----- ------ -------- -------- ------- ------
// SV_POSITION              0   xyzw        0      POS   float   xyzw
// TEXCOORD                 0   xy          1     NONE   float   xy
//
//
// Runtime generated constant mappings:
//
// Target Reg                               Constant Description
// ---------- --------------------------------------------------
// c0                              Vertex Shader position offset
//
//
// Level9 shader bytecode:
//
    vs_2_x
    def c1, 0, 1, 0, 0
    dcl_texcoord v0
    dcl_texcoord1 v1
    add oPos.xy, v0, c0
    mov oPos.zw, c1.xyxy
    mov oT0.xy, v1

// approximately 3 instruction slots used
vs_4_0
dcl_input v0.xy
dcl_input v1.xy
dcl_output_siv o0.xyzw, position
dcl_output o1.xy
mov o0.xy, v0.xyxx
mov o0.zw, l(0,0,0,1.000000)
mov o1.xy, v1.xyxx
ret
// Approximately 4 instruction slots used
#endif

const BYTE g_VS_Passthrough2D[] =
{
     68,  88,  66,  67, 230,  95,
    115, 230,  65, 211,  74,  82,
    143, 170, 109, 175,  63, 210,
     14, 229,   1,   0,   0,   0,
    216,   2,   0,   0,   6,   0,
      0,   0,  56,   0,   0,   0,
    200,   0,   0,   0,  88,   1,
      0,   0, 212,   1,   0,   0,
     44,   2,   0,   0, 128,   2,
      0,   0,  65, 111, 110,  57,
    136,   0,   0,   0, 136,   0,
      0,   0,   0,   2, 254, 255,
     96,   0,   0,   0,  40,   0,
      0,   0,   0,   0,  36,   0,
      0,   0,  36,   0,   0,   0,
     36,   0,   0,   0,  36,   0,
      1,   0,  36,   0,   0,   0,
      0,   0,   1,   2, 254, 255,
     81,   0,   0,   5,   1,   0,
     15, 160,   0,   0,   0,   0,
      0,   0, 128,  63,   0,   0,
      0,   0,   0,   0,   0,   0,
     31,   0,   0,   2,   5,   0,
      0, 128,   0,   0,  15, 144,
     31,   0,   0,   2,   5,   0,
      1, 128,   1,   0,  15, 144,
      2,   0,   0,   3,   0,   0,
      3, 192,   0,   0, 228, 144,
      0,   0, 228, 160,   1,   0,
      0,   2,   0,   0,  12, 192,
      1,   0,  68, 160,   1,   0,
      0,   2,   0,   0,   3, 224,
      1,   0, 228, 144, 255, 255,
      0,   0,  83,  72,  68,  82,
    136,   0,   0,   0,  64,   0,
      1,   0,  34,   0,   0,   0,
     95,   0,   0,   3,  50,  16,
     16,   0,   0,   0,   0,   0,
     95,   0,   0,   3,  50,  16,
     16,   0,   1,   0,   0,   0,
    103,   0,   0,   4, 242,  32,
     16,   0,   0,   0,   0,   0,
      1,   0,   0,   0, 101,   0,
      0,   3,  50,  32,  16,   0,
      1,   0,   0,   0,  54,   0,
      0,   5,  50,  32,  16,   0,
      0,   0,   0,   0,  70,  16,
     16,   0,   0,   0,   0,   0,
     54,   0,   0,   8, 194,  32,
     16,   0,   0,   0,   0,   0,
      2,  64,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
    128,  63,  54,   0,   0,   5,
     50,  32,  16,   0,   1,   0,
      0,   0,  70,  16,  16,   0,
      1,   0,   0,   0,  62,   0,
      0,   1,  83,  84,  65,  84,
    116,   0,   0,   0,   4,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   4,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   1,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   3,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
     82,  68,  69,  70,  80,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,  28,   0,   0,   0,
      0,   4, 254, 255,   0,   1,
      0,   0,  28,   0,   0,   0,
     77, 105,  99, 114, 111, 115,
    111, 102, 116,  32,  40,  82,
     41,  32,  72,  76,  83,  76,
     32,  83, 104,  97, 100, 101,
    114,  32,  67, 111, 109, 112,
    105, 108, 101, 114,  32,  54,
     46,  51,  46,  57,  54,  48,
     48,  46,  49,  54,  51,  56,
     52,   0, 171, 171,  73,  83,
     71,  78,  76,   0,   0,   0,
      2,   0,   0,   0,   8,   0,
      0,   0,  56,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   3,   0,   0,   0,
      0,   0,   0,   0,   3,   3,
      0,   0,  65,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   3,   0,   0,   0,
      1,   0,   0,   0,   3,   3,
      0,   0,  80,  79,  83,  73,
     84,  73,  79,  78,   0,  84,
     69,  88,  67,  79,  79,  82,
     68,   0, 171, 171,  79,  83,
     71,  78,  80,   0,   0,   0,
      2,   0,   0,   0,   8,   0,
      0,   0,  56,   0,   0,   0,
      0,   0,   0,   0,   1,   0,
      0,   0,   3,   0,   0,   0,
      0,   0,   0,   0,  15,   0,
      0,   0,  68,   0,   0,   0,
      0,   0,   0,   0,   0,   0,
      0,   0,   3,   0,   0,   0,
      1,   0,   0,   0,   3,  12,
      0,   0,  83,  86,  95,  80,
     79,  83,  73,  84,  73,  79,
     78,   0,  84,  69,  88,  67,
     79,  79,  82,  68,   0, 171,
    171, 171
};
