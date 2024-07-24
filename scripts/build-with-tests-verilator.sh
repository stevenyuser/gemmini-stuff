#!/bin/bash

BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}BUILDING VERILATOR${NC}\n"

./scripts/build-verilator.sh $1

cd software/gemmini-rocc-tests/

echo -e "${BLUE}BUILDING TESTS${NC}\n"

./build.sh

cd ../..