@echo off
rmdir /s /q target
docker build -t store-api .
docker-compose up -d