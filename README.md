# gemmini-stuff

## simple_matmul.c instructions
1. Copy `simple_matmul.c` in this repo into the directory: `~/chipyard/generators/gemmini/software/gemmini-rocc-tests/bareMetalC/`
2. In `~/chipyard/generators/gemmini/software/gemmini-rocc-tests/bareMetalC/Makefile`, add `simple_matmul` to the top of `tests` in the Makefile
3. Run the following to rebuild the tests:
```
cd  ~/chipyard/generators/gemmini/software/gemmini-rocc-tests/
./build.sh
```
Afterwards, you can cd back to gemmini:
```
cd ../..
```
4. Run the simulations in spike and/or verilator (make sure you already built the simulators before):
```
./scripts/run-spike.sh simple_matmul
./scripts/run-verilator.sh simple_matmul
```
