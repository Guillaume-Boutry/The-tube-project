package com.tubeproject.model.requests.select;

import com.tubeproject.model.DatabaseConnection;
import com.tubeproject.model.annotation.Description;
import com.tubeproject.model.interfaces.Selectable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmailExistsRequest implements Selectable {
    private String email;

    public EmailExistsRequest(String email) {
        this.email = email;
    }

    @Description("Get Email, no result if not duplicated")
    @Override
    public PreparedStatement getSelectQuery() throws SQLException {
        String query = "SELECT email FROM users WHERE email=?";
        PreparedStatement stmt = DatabaseConnection.prepareStmt(query);
        stmt.setString(1, this.email);
        return stmt;
    }

    @Override
    public Optional<Boolean> buildFromResult(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return Optional.of(Boolean.TRUE);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return Optional.of(Boolean.FALSE);
        }
        return Optional.of(Boolean.FALSE);
    }
}
