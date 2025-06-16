#!/bin/sh
echo "Stopping all Kubernetes services from directory: k8s"
kubectl delete -f k8s
echo "Waiting for pods to terminate..."
kubectl wait --for=delete pod --all --timeout=60s
echo "All services stopped."