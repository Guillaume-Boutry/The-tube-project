package com.tubeproject.model.interfaces;

import com.tubeproject.model.annotation.Description;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Updatable {
    @Description("update interface")
    PreparedStatement getUpdateStatement() throws SQLException;
}
