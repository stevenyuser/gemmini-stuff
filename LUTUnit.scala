// Works only for 4-bit int multiplication
class LUTUnit[T <: Data](inputType: T, cType: T, dType: T) extends Module {
    val io = IO(new Bundle {
        val in_a = Input(inputType)
        val in_b = Input(inputType)
        val in_c = Input(cType)
        val out_d = Output(dType)
    })
    
    val lut = VecInit(Seq.tabulate(256) { i =>
        val a = (i >> 4) & 0xF
        val b = i & 0xF
        (a * b).U(8.W)
    })
    
    val index = Cat(io.in_a.asUInt, io.in_b.asUInt)
    
    val mul = lut(index)
    
    io.out_d := mul + io.in_c.asUInt
}

test(new LUTUnit(UInt(4.W), UInt(8.W), UInt(8.W))) { c =>
    c.io.in_a.poke(2.U)
    c.io.in_b.poke(3.U)
    c.io.in_c.poke(1.U)
    c.io.out_d.expect(7.U)
}

println("SUCCESS!!") // Scala Code: if we get here, our tests passed!
