package com.know_me.know_me.features.relationship.repositories;

import com.know_me.know_me.features.relationship.domain.entities.Person;
import com.know_me.know_me.features.relationship.domain.valueobjects.Interest;
import com.know_me.know_me.shared.domain.valueobjects.ID;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.neo4j.driver.Values;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepositoryNeo4j implements PersonRepository {

    private final Driver driver;

    public PersonRepositoryNeo4j(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void save(Person person) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE (p:Person {id: $id, name: $name})",
                        Values.parameters(
                                "id", person.id.value().toString(),
                                "name", person.name
                        ));
                for (Interest interest : person.getInterests()) {
                    tx.run("""
                                    MERGE (i:Interest {name: $name})
                                    WITH i
                                    MATCH (p:Person {id: $personId})
                                    MERGE (p)-[:HAS_INTEREST]->(i)
                                    """,
                            Values.parameters(
                                    "name", interest.name(),
                                    "personId", person.id.value().toString()
                            ));
                }
                return null;
            });
        }
    }

    @Override
    public void addFriend(ID personId, ID friendId) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("""
                        MATCH (a:Person {id: $aId}), (b:Person {id: $bId})
                        MERGE (a)-[:IS_FRIEND_WITH]->(b)
                        MERGE (b)-[:IS_FRIEND_WITH]->(a)
                        """, Values.parameters(
                        "aId", personId.value().toString(),
                        "bId", friendId.value().toString()
                ));
                return null;
            });
        }
    }
}
