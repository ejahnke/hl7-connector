#!/bin/bash

# Synthea Province Data Generation Script
# Runs headless generation for all Canadian provinces

set -e  # Exit on any error

echo "Starting Synthea data generation for Canadian provinces..."

# Alberta
echo "Generating data for Alberta..."
./run_synthea -s 585321 -cs 965832 -p 8215 -c src/main/resources/synthea.properties Alberta

# Manitoba
echo "Generating data for Manitoba..."
./run_synthea -s 201477 -cs 302366 -p 10250 -c src/main/resources/synthea.properties Manitoba

# Saskatchewan
echo "Generating data for Saskatchewan..."
./run_synthea -p 8521 -s 935587 -cs 320013 -c src/main/resources/synthea.properties Saskatchewan

# British Columbia
echo "Generating data for British Columbia..."
./run_synthea -p 47500 -s 201147 -cs 693200 -c src/main/resources/synthea.properties "British Columbia"

# Nova Scotia
echo "Generating data for Nova Scotia..."
./run_synthea -p 17522 -s 165987 -cs 320014 -c src/main/resources/synthea.properties "Nova Scotia"

# New Brunswick
echo "Generating data for New Brunswick..."
./run_synthea -p 5021 -s 210155 -cs 965221 -c src/main/resources/synthea.properties "New Brunswick"

echo "All province data generation completed successfully!"