#!/bin/bash

# cd ../../sims/verilator/
# echo Generating new gemmini_params.h file...
# make verilog CONFIG=CustomGemminiSoCConfig &> build.log

# cd -
# cp software/gemmini-rocc-tests/include/gemmini_params.h software/libgemmini/gemmini_params.h
# make -C software/libgemmini clean
# make -C software/libgemmini install


cd software/gemmini-rocc-tests/

./build.sh

cd ../..
