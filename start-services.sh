#!/bin/bash

set -e

K8S_DIR="k8s"

echo "Setting Docker Desktop context..."
kubectl config use-context docker-desktop

echo "Starting all Kubernetes services from directory: $K8S_DIR"

if [ ! -d "$K8S_DIR" ]; then
  echo "Directory $K8S_DIR does not exist."
  exit 1
fi

# Apply all Kubernetes configurations
echo "Applying Kubernetes configurations..."
kubectl apply -f k8s/mysql.yaml
kubectl rollout status deployment/user-db
kubectl rollout status deployment/department-db
kubectl apply -f k8s/eureka-service.yaml
kubectl rollout status deployment/eureka-server
kubectl apply -f k8s/department-service.yaml
kubectl rollout status deployment/department-service
kubectl apply -f k8s/user-service.yaml
kubectl rollout status deployment/user-service
kubectl apply -f k8s/auth-service.yaml
kubectl rollout status deployment/auth-service
kubectl apply -f k8s/apigateway-service.yaml
kubectl rollout status deployment/api-gateway


echo "All Kubernetes services started."

# Port-forward services
kubectl port-forward deployment/eureka-server 8761:8761 &
kubectl port-forward deployment/api-gateway 8080:8080 &

echo "Port forwarding started for eureka-service (8761) and api-gateway (8080)."