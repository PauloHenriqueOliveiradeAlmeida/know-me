#!/bin/bash

neo4j start

echo "Starting Neo4j..."
sleep 10

# echo "Authenticating Neo4j..."
# neo4j-admin dbms set-initial-password $NEO4J_PASSWORD
# sleep 10

echo "Running Cypher..."
cypher-shell -u neo4j -p neo4j -f /var/lib/neo4j/scripts/init.cypher

tail -f /dev/null
