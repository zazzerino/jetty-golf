package com.kdp.golf;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DatabaseConnection
{
    private static final Path SCHEMA_PATH = Paths.get("src/main/resources/schema.sql");
    private static final String DB_NAME = "golf";
    private static final String USER = "golf";
    private static final String PASSWORD = "golf";
    private final Jdbi jdbi;
    private static final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);

    public DatabaseConnection()
    {
        var dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[] {"localhost"});
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new PostgresPlugin());
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public void runSchema() throws IOException
    {
        log.info("dropping and recreating db tables");
        try (var lines = Files.lines(SCHEMA_PATH)) {
            var sql = lines.collect(Collectors.joining("\n"));
            jdbi.useHandle(handle -> handle.execute(sql));
        }
    }

    public Jdbi jdbi()
    {
        return jdbi;
    }
}
