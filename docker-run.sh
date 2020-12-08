#!/bin/bash
docker build -t yousell-orders:latest .
docker run -p 8082:8080 yousell-orders:latest