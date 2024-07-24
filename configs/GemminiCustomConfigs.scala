package gemmini

import org.chipsalliance.cde.config.{Config, Parameters}
import chisel3._
import freechips.rocketchip.diplomacy.LazyModule
import freechips.rocketchip.subsystem.SystemBusKey
import freechips.rocketchip.tile.BuildRoCC


object GemminiCustomConfigs {
  // Default configurations
  val defaultConfig = GemminiConfigs.defaultConfig
  val defaultFpConfig = GemminiFPConfigs.defaultFPConfig

  // Create your own configs here
  val baselineInferenceConfig = defaultConfig.copy(
    has_training_convs = false,
  )

  val highPerfInferenceConfig = defaultConfig.copy(
    meshRows = 32,
    meshColumns = 32,

    has_training_convs = false,

    sp_capacity = CapacityInKilobytes(512),
    acc_capacity = CapacityInKilobytes(128),
  )

  val trainingConfig = defaultFpConfig.copy(
    inputType = Float(expWidth = 8, sigWidth = 24),
    accType = Float(expWidth = 8, sigWidth = 24),

    meshRows = 8,
    meshColumns = 8,

    has_training_convs = true,
    has_max_pool =  false,

    sp_capacity = CapacityInKilobytes(512),
    acc_capacity = CapacityInKilobytes(128),
  )

  val ibertInferenceConfig = defaultConfig.copy(
    has_training_convs = false,
    has_max_pool =  false,
    has_normalizations = true,

    acc_capacity = CapacityInKilobytes(128),
  )

  // experimental configs

  // sequential config (16x16)
  // val defaultConfig = GemminiConfigs.defaultConfig

  // one tile config (no pipeline registers in between) (16x16)
  val combinationalConfig = defaultConfig.copy(
    tileRows = 16,
    tileColumns = 16,
    meshRows = 1,
    meshColumns = 1
  )

  // sequential config (8x8)
  val mediumDefaultConfig = defaultConfig.copy(
    tileRows = 1,
    tileColumns = 1,
    meshRows = 8,
    meshColumns = 8,

    sp_capacity = CapacityInKilobytes(128),
    acc_capacity = CapacityInKilobytes(32),
  )

  // one tile config (8x8)
  val mediumCombinationalConfig = defaultConfig.copy(
    tileRows = 8,
    tileColumns = 8,
    meshRows = 1,
    meshColumns = 1,

    sp_capacity = CapacityInKilobytes(128),
    acc_capacity = CapacityInKilobytes(32),
  )

  // sequential config (4x4)
  val smallDefaultConfig = defaultConfig.copy(
    tileRows = 1,
    tileColumns = 1,
    meshRows = 4,
    meshColumns = 4,

    sp_capacity = CapacityInKilobytes(64),
    acc_capacity = CapacityInKilobytes(16),
  )

  // one tile config (4x4)
  val smallCombinationalConfig = defaultConfig.copy(
    tileRows = 4,
    tileColumns = 4,
    meshRows = 1,
    meshColumns = 1,

    sp_capacity = CapacityInKilobytes(64),
    acc_capacity = CapacityInKilobytes(16),
  )

  // sequential config (2x2)
  val miniDefaultConfig = defaultConfig.copy(
    tileRows = 1,
    tileColumns = 1,
    meshRows = 2,
    meshColumns = 2,

    sp_capacity = CapacityInKilobytes(32),
    acc_capacity = CapacityInKilobytes(8),
  )

  // one tile config (2x2)
  val miniCombinationalConfig = defaultConfig.copy(
    tileRows = 2,
    tileColumns = 2,
    meshRows = 1,
    meshColumns = 1,

    sp_capacity = CapacityInKilobytes(32),
    acc_capacity = CapacityInKilobytes(8),
  )
  
  // experimental fp configs

  val miniFP32Config = defaultFpConfig.copy(
    tileRows = 1,
    tileColumns = 1,
    meshRows = 2,
    meshColumns = 2,

    sp_capacity = CapacityInKilobytes(32),
    acc_capacity = CapacityInKilobytes(8),
  )

  val miniFP16Config = defaultFpConfig.copy(
    tileRows = 1,
    tileColumns = 1,
    meshRows = 2,
    meshColumns = 2,

    inputType = Float(expWidth = 5, sigWidth = 11),
    spatialArrayOutputType = Float(expWidth = 5, sigWidth = 11),
    accType = Float(expWidth = 5, sigWidth = 11),

    sp_capacity = CapacityInKilobytes(32),
    acc_capacity = CapacityInKilobytes(8),
  )

  // Specify which of your custom configs you want to build here
  val customConfig = defaultConfig
}


class GemminiCustomConfig[T <: Data : Arithmetic, U <: Data, V <: Data](
  gemminiConfig: GemminiArrayConfig[T,U,V] = GemminiCustomConfigs.customConfig
) extends Config((site, here, up) => {
  case BuildRoCC => up(BuildRoCC) ++ Seq(
    (p: Parameters) => {
      implicit val q = p
      val gemmini = LazyModule(new Gemmini(gemminiConfig))
      gemmini
    }
  )
})

