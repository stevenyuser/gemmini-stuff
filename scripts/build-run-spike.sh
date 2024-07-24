#!/bin/bash

./scripts/build-spike.sh

cd software/gemmini-rocc-tests/

./build.sh

cd ../..

./scripts/run-spike.sh $1