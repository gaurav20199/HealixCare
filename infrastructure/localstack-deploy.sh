#!/bin/bash
set -e # Stops the script if any command fails

# Delete stack if exists
aws --endpoint-url=http://localhost:4566 cloudformation delete-stack \
  --stack-name patient-management || true

# Wait for deletion (optional)
aws --endpoint-url=http://localhost:4566 cloudformation wait stack-delete-complete \
  --stack-name patient-management || true

# Create stack from CDK output
aws --endpoint-url=http://localhost:4566 cloudformation create-stack \
  --stack-name patient-management \
  --template-body file://./cdk.out/localstack.template.json

# Wait until creation is complete
aws --endpoint-url=http://localhost:4566 cloudformation wait stack-create-complete \
  --stack-name patient-management

# Get load balancer DNS
aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
  --query "LoadBalancers[0].DNSName" --output text
