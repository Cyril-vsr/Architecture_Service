#!/bin/bash

# Répertoires des services
ROOM_SERVICE_DIR="./RoomService"
WINDOW_SERVICE_DIR="./WindowService"
SENSOR_SERVICE_DIR="./SensorService"
GATEWAY_DIR="./Gateway"

# Logs
ROOM_LOG="room_service.log"
WINDOW_LOG="window_service.log"
SENSOR_LOG="sensor_service.log"
GATEWAY_LOG="gateway.log"

# Tableau pour les PIDs
PIDS=()

# Fonction pour démarrer un service
start_service() {
    local service_name=$1
    local service_dir=$2
    local log_file=$3

    echo "Starting $service_name..."
    (cd "$service_dir" && mvn spring-boot:run > "../$log_file" 2>&1) &
    PIDS+=($!) # Enregistrer le PID du processus
}

# Démarrer les services et enregistrer les logs
start_service "RoomService" $ROOM_SERVICE_DIR $ROOM_LOG
start_service "WindowService" $WINDOW_SERVICE_DIR $WINDOW_LOG
start_service "SensorService" $SENSOR_SERVICE_DIR $SENSOR_LOG
start_service "Gateway" $GATEWAY_DIR $GATEWAY_LOG

# Attendre que tous les processus se terminent
echo "Waiting for all services to start..."
EXIT_CODE=0
for pid in "${PIDS[@]}"; do
    wait $pid || EXIT_CODE=1
done

# Vérifier l'état final
if [ $EXIT_CODE -eq 0 ]; then
    echo "All services started successfully. Logs are stored in respective .log files."
else
    echo "One or more services failed to start. Check the .log files for details."
fi
 
