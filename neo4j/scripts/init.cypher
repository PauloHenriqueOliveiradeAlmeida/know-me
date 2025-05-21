LOAD CSV WITH HEADERS FROM 'file:///persons.csv' AS row
CREATE (:Person {id: toInteger(row.id), name: row.name});

LOAD CSV WITH HEADERS FROM 'file:///interests.csv' AS row
CREATE (:Interest{name: row.name});

LOAD CSV WITH HEADERS FROM 'file:///person_to_person.csv' AS row
MATCH (a:Person {id: toInteger(row.:START_ID)}), (b:Person {id: toInteger(row.:END_ID)})
CREATE (a)-[:IS_FRIEND_OF]->(b);

LOAD CSV WITH HEADERS FROM 'file:///person_to_interest.csv' AS row
MATCH (a:Person {id: toInteger(row.:START_ID)}), (b:Interest {name: row.:END_ID})
CREATE (a)-[:HAS_INTEREST]->(b);