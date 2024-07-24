import hardfloat._

// LUT based int4 MAC unit
// cType is the accumulator type; can be int4 or int8
// dType is the output type; can be int4 or int8
class LUTIntMacUnit[T <: Data](inputType: T, cType: T, dType: T) (implicit ev: Arithmetic[T]) extends Module {
  import ev._
  val io = IO(new Bundle {
    val in_a  = Input(inputType)
    val in_b  = Input(inputType)
    val in_c  = Input(cType)
    val out_d = Output(dType)
  })

  val lut = VecInit(Seq.tabulate(256) { i =>
    val a = ((i >> 4) & 0xF).U
    val b = (i & 0xF).U
    (a * b).asTypeOf(dType)
  })

  val index = Cat(io.in_a.asUInt, io.in_b.asUInt)

  io.out_d := lut(index) + io.in_c
}

// LUT based fp4 MAC unit
// cType is the accumulator type; fp4
// dType is the output type; fp4
class LUTFP4MacUnit(implicit ev: Arithmetic[Float]) extends Module {
  import ev._
  val io = IO(new Bundle {
    val in_a  = Input(Float(expWidth = 2, sigWidth = 2))
    val in_b  = Input(Float(expWidth = 2, sigWidth = 2))
    val in_c  = Input(Float(expWidth = 2, sigWidth = 2))
    val out_d = Output(Float(expWidth = 2, sigWidth = 2))
  })

  val lut = VecInit(Seq.tabulate(256) { i =>
    // a and b are fp4
    val a_bits = ((i >> 4) & 0xF).U
    val b_bits = (i & 0xF).U

    val a = Wire(Float(2, 2))
    a.bits := fNFromRecFN(2, 2, a_bits)
    val b = Wire(Float(2, 2))
    b.bits := fNFromRecFN(2, 2, b_bits)

    (a * b).asTypeOf(Float(expWidth = 2, sigWidth = 2))
  })

  val index = Cat(io.in_a.bits, io.in_b.bits)

  io.out_d := lut(index) + io.in_c
}
