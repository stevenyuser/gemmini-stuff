#!/bin/bash

./scripts/build-verilator.sh

cd software/gemmini-rocc-tests/

./build.sh

cd ../..

./scripts/run-verilator.sh $1